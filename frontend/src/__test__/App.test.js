import React from 'react'
import App from '../App';
import configureStore from 'redux-mock-store';
import renderer from 'react-test-renderer';
import {Provider} from 'react-redux';

const mockStore = configureStore([]);

test('Renders App', () => {
  let store = mockStore({
    authReducer: {
      isLoggedIn: false,
    }
  });

  let component = renderer.create(
    <Provider store={store}>
      <App/>
    </Provider>
  );

  expect(component.toJSON()).toMatchSnapshot();
});

test('Only log in page should be displayed when logged out', () => {
  let store = mockStore({
    authReducer: {
      isLoggedIn: false,
    }
  });

  let component = renderer.create(
    <Provider store={store}>
      <App/>
    </Provider>
  );

  expect(component.root.findByProps({'data-test':'login-route'})).toBeTruthy();
  expect(component.toJSON()).not.toContain('pioneers')
});

test('Login page does not appear when logged in', () => {
  let store = mockStore({
    authReducer: {
      isLoggedIn: true,
    }
  });

  let component = renderer.create(
    <Provider store={store}>
      <App/>
    </Provider>
  );

  renderer.act(() => {});

  expect(component.toJSON()).not.toContain(/Login/i);
  expect(component.toJSON()).toMatchSnapshot();
});
