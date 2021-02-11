import React from 'react'
import {rest} from 'msw'
import {setupServer} from 'msw/node'
import renderer from 'react-test-renderer';
import '@testing-library/jest-dom/extend-expect'
import Login from "../Pages/Login";

import configureStore from 'redux-mock-store';
import {Provider} from "react-redux";

const mockStore = configureStore([]);


const server = setupServer(
  rest.get('/v1/login', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({token: 'this is the token'}))
  }),
);

let store;
let component;

beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());
afterAll(() => server.close());
beforeEach(() => {
  store = mockStore({
    authReducer: {
      isLoggedIn: false,
    }
  });

  store.dispatch = jest.fn().mockImplementation(() => Promise.resolve());

  component = renderer.create(
    <Provider store={store}>
      <Login dispatch={store.dispatch}/>
    </Provider>
  );
});

it('should render with given state from Redux store', () => {
  expect(component.toJSON()).toMatchSnapshot();
});

it('should login with username password', () => {
  renderer.act(() => {
    component.root.findByProps({'data-test': 'username'}).props.onChange({target: {value: "username"}});
    component.root.findByProps({'data-test': 'password'}).props.onChange({target: {value: "password"}});
  });

  renderer.act(() => {
    component.root.findByType('form').props.onSubmit({preventDefault: jest.fn()});
  });

  expect(store.dispatch).toHaveBeenCalledTimes(1);
});

it('should not login if username missing', () => {
  renderer.act(() => {
    component.root.findByProps({'data-test': 'password'}).props.onChange({target: {value: "password"}});
  });

  renderer.act(() => {
    component.root.findByType('form').props.onSubmit({preventDefault: jest.fn()});
  });

  expect(store.dispatch).toHaveBeenCalledTimes(0);
});

it('should not login if password missing', () => {
  renderer.act(() => {
    component.root.findByProps({'data-test': 'username'}).props.onChange({target: {value: "username"}});
  });

  renderer.act(() => {
    component.root.findByType('form').props.onSubmit({preventDefault: jest.fn()});
  });

  expect(store.dispatch).toHaveBeenCalledTimes(0);
});
