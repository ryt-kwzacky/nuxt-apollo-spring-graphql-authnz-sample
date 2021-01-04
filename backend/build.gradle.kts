import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion by extra("1.4.21")
val springBootVersion by extra("2.4.1")
val mysqlConnectorVersion by extra("8.0.22")

val databaseURL by extra(System.getenv("DATASOURCE_DATABASE_URL") ?: "127.0.0.1:3306/circleci_heroku_dev?characterEncoding=UTF8&connectionCollation=utf8mb4_bin&useSSL=false")
val databaseHost by extra(System.getenv("DATASOURCE_DATABASE_HOST") ?: "127.0.0.1")
val databasePort by extra(System.getenv("DATASOURCE_DATABASE_PORT") ?: "3306")
val databaseName by extra(System.getenv("DATASOURCE_DATABASE_NAME") ?: "nuxt_apollo_spring_graphql_authnz_sample_dev")
val databaseUser by extra(System.getenv("DATASOURCE_DATABASE_USERNAME") ?: "root")
val databaseMigrationUser by extra(System.getenv("DATASOURCE_DATABASE_MIGRATION_USERNAME") ?: "root")
val databasePassword by extra(System.getenv("DATASOURCE_DATABASE_PASSWORD") ?: "")
val databaseQuery by extra(System.getenv("DATASOURCE_DATABASE_QUERY") ?: "?characterEncoding=UTF8&connectionCollation=utf8mb4_bin&useSSL=false")

plugins {
	id("org.springframework.boot") version "2.4.1"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"

	/**
	 * To use Flyway commands to run migration.
	 */
	id("org.flywaydb.flyway") version "6.5.7"

	/**
	 * To use generator for the jOOQ Java sources from a given database schema.
	 *
	 * gradle-jooq-plugin - Gihtub
	 * @link https://github.com/etiennestuder/gradle-jooq-plugin
	 *
	 * nu.studer.jooq - Gradle Plugin
	 * @link https://plugins.gradle.org/plugin/nu.studer.jooq
	 */
	id("nu.studer.jooq") version "5.0.2"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_15

repositories {
	mavenCentral()
}

dependencies {
	/**
	 * Spring Boot Starters
	 */
	implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")

	/**
	 * Java Database Connectivity (JDBC) is an application programming interface (API) for the programming language Java,
	 * which defines how a client may access a database.
	 * This is used for migration, etc.
	 */
	implementation("org.springframework.boot:spring-boot-starter-jdbc:$springBootVersion")

	/**
	 * This is JDBC Type 4 driver for MySQL
	 *
	 * mysql-connector-j - Github
	 * @link https://github.com/mysql/mysql-connector-j
	 */
	implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")
	jooqGenerator("mysql:mysql-connector-java:${mysqlConnectorVersion}")

	/**
	 * Libraries to use Kotlin.
	 */
	implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
	
	/**
	 * This is to add support for serialization/deserialization of Kotlin classes and data classes.
	 *
	 * Github
	 * @link https://github.com/FasterXML/jackson-module-kotlin
	 */
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	
	/**
	 * GraphQL
	 */
	implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:8.1.1")
	runtimeOnly("com.graphql-java-kickstart:playground-spring-boot-starter:8.1.1")
}

/**
 * Flyway is a migration tool.
 *
 * Gradle Task: flywayMigrate
 * @link https://flywaydb.org/documentation/gradle/migrate
 */
flyway {
	// The jdbc url to use to connect to the database
	url = "jdbc:mysql://${databaseHost}:${databasePort}/${databaseName}${databaseQuery}"
	// The user to use to connect to the database
	user = databaseMigrationUser
	// The password to use to connect to the database
	password = databasePassword
	/**
	 * Locations to scan recursively for migrations.
	 * We use plain SQL files for migrating DB schema and java based ones for migrating data.
	 */
	locations = arrayOf("filesystem:src/main/resources/db/migration")
	// Allows migrations to be run "out of order".
	// If you already have versions 1 and 3 applied, and now a version 2 is found, it will be applied too instead of being ignored.
	outOfOrder = true
}

jooq {
	version.set("3.13.4")  // default (can be omitted)
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)  // default (can be omitted)

	configurations {
		create("main") {  // name of the jOOQ configuration
			generateSchemaSourceOnCompilation.set(true)  // default (can be omitted)

			jooqConfiguration.apply {
				logging = org.jooq.meta.jaxb.Logging.DEBUG
				jdbc.apply {
					driver = "com.mysql.cj.jdbc.Driver"
					url = "jdbc:mysql://${databaseURL}"
					user = databaseUser
					password = databasePassword
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						name = "org.jooq.meta.mysql.MySQLDatabase"
						inputSchema = databaseName
						includes = ".*"
						excludes = "flyway_schema_history"
						isOutputSchemaToDefault = true
					}
					generate.apply {
						isRecords = false
					}
					target.apply {
						/**
						 * Package name for generated files.
						 */
						packageName = "com.example.backend.db.generatedJooqCode"
						/**
						 * Directory to put generated files on.
						 */
						directory = "src/main/kotlin"
					}
					/**
					 * This strategy is to add word "J" as prefix to each generated classes.
					 * It prevents name conflict from other ones made by developers.
					 */
					strategy.name = "org.jooq.codegen.example.JPrefixGeneratorStrategy"
				}
			}
		}
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "15"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
