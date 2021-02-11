import authReducer from '../Reducers/authReducer'
import {
  LOGIN_SUCCESS,
  LOGIN_FAIL,
  LOGOUT,
} from "../Actions/types";

describe('Auth reducer', () => {
  it('should handle initial state', () => {
    expect(authReducer(undefined, {})).toEqual({isLoggedIn: false, token: null, user: null})
  });

  it('should handle login success state', () => {
    expect(authReducer(undefined, {type: LOGIN_SUCCESS, payload: {token: "token", user: "author"}})).toEqual({isLoggedIn: true, token: "token", user: "author"})
  });

  it('should handle login failed state', () => {
    expect(authReducer(undefined, {type: LOGIN_FAIL})).toEqual({isLoggedIn: false, token: null, user: null})
  });

  it('should handle logout state', () => {
    expect(authReducer({isLoggedIn: true, token: "token", user: "author"}, {type: LOGOUT})).toEqual({isLoggedIn: false, token: null, user: null})
  });
});
