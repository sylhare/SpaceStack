import React from 'react'
import App from '../App'
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux'
import { loggedInStore, loggedOutStore } from './__mocks__'

const renderApp = (store) => (
  renderer.create(
    <Provider store={store}>
      <App/>
    </Provider>
  )
)

test('Renders App', () => {
  const component = renderApp(loggedOutStore)

  expect(component.toJSON()).toMatchSnapshot()
})

test('Only log in page should be displayed when logged out', () => {
  const component = renderApp(loggedOutStore)

  expect(component.root.findByProps({ 'data-test': 'login-route' })).toBeTruthy()
  expect(component.toJSON()).not.toContain(/pioneers/i)
})

test('Login page does not appear when logged in', () => {
  const component = renderApp(loggedInStore)

  renderer.act(() => {
  })

  expect(component.toJSON()).not.toContain(/login/i)
  expect(component.root.findByProps({ 'data-test': 'Home' })).toBeTruthy()
})

test('Logout should log out', () => {
  const store = loggedInStore
  store.dispatch = jest.fn().mockImplementation(() => Promise.resolve())
  const component = renderApp(store)

  renderer.act(() => {
    component.root.findByProps({ 'data-test': 'logout' }).props.onClick()
  })

  expect(store.dispatch).toHaveBeenCalledTimes(1)
})
