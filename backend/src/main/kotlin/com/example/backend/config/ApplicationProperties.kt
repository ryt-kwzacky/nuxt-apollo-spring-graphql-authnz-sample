package com.example.backend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("application")
data class ApplicationProperties(
    val url: Url = Url(),
    val security: Security = Security(),
    val google: Google = Google(),
    val rollbar: Rollbar = Rollbar(),
    val aws: AWS = AWS(),
    val mail: Mail = Mail(),
    val algolia: Algolia = Algolia(),
    val actionLog: ActionLog = ActionLog()
) {
    data class Url(
        val frontendApplicationBaseUrl: String = "",
        val backendFileApplicationBaseUrl: String = ""
    )

    data class Security(
        val allowedFrontendDomain: String = "",
        val firebaseProjectId: String = "",
        val googleServiceAccountJson: String = ""
    )

    data class Google(
        val refreshToken: String = "",
        val clientId: String = "",
        val clientSecret: String = "",
        val spreadsheetId: SpreadsheetId = SpreadsheetId()
    ) {
        data class SpreadsheetId(
            val mainKpi: String = ""
        )
    }

    data class Rollbar(
        val enabled: Boolean = true,
        val environment: String = "",
        val accessToken: String = ""
    )

    data class AWS(
        val region: String = "",
        val s3: S3 = S3()
    ) {
        data class S3(
            val bucketName: String = ""
        )
    }

    data class Mail(
        val subjectPrefix: String = "",
        val banOutboundMail: Boolean = false,
        val enableCategoryTag: Boolean = false
    )

    data class Algolia(
        val applicationId: String = "",
        val userProfileSearchApiKey: String = ""
    )

    data class ActionLog(
        val aesCryptSecret: String = ""
    )
}

