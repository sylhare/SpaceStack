import React from "react";
import {BrowserRouter as Router, NavLink, Route} from "react-router-dom";
import './App.css';

import Pioneers from "./Pages/Pioneers";
import Manage from "./Pages/Manage";
import Home from "./Pages/Home";
import Audit from "./Pages/Audit";

function App() {
  return (
    <Router>
      <div>
        <nav>
          <ul className='header'>
            <li><NavLink aria-label="Home" to="/" exact activeClassName="active">Home</NavLink></li>
            <li><NavLink aria-label="Pioneers" to="/pioneers/" exact activeClassName="active" >Pioneers</NavLink></li>
            <li><NavLink aria-label="Manage" to="/manage/" exact activeClassName="active">Habitat and Survival Management</NavLink></li>
            <li><NavLink aria-label="Audit" to="/audit/" exact activeClassName="active">Audit</NavLink></li>
          </ul>
        </nav>

        <Route path="/" exact component={Home}/>
        <Route path="/pioneers/" component={Pioneers}/>
        <Route path="/manage/" component={Manage}/>
        <Route path="/audit/" component={Audit}/>
      </div>
    </Router>
  );
}

export default App;
