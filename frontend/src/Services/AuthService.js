import axios from "axios";

class AuthService {
   login(username, password) {
    return axios
      .post("/v1/login", {username, password})
      .then((response) => {
        if (response.data.token) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }
        return response.data;
      });
  };

  logout = () => {
    localStorage.removeItem("user");
  };

}

export function authHeader() {
  const user = JSON.parse(localStorage.getItem("user"));

  if (user && user.token) {
    return { Authorization: "Bearer " + user.token };
  } else {
    return {};
  }
}

export default new AuthService()
