import { greet } from './greeter'

test('#### greeterテスト', () => {
  expect(greet('Hello')).toBe('Hello')
})

test('#### test2', () => {
  expect(greet('Hello')).toBe('Hello')
})

test('#### test2', () => {
  expect(greet('Hello')).not.toBe('yes')
})
