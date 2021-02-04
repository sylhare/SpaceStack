import React from "react";
import ManageBabyRequests from "../Components/ManageBabyRequests";

function Manage() {
  return (
    <div className="content">
      <h1>Review the Baby requests</h1>
      <p>This is the Habitat and Survival Management Page, be wary of the future of the colony.
      We can't overgrow the population, but also as pioneers of the new colony we cannot allow random names
      for the future of our generations.</p>
      <p>New requests:</p>
      <ManageBabyRequests/>
    </div>
  );
}

export default Manage;
