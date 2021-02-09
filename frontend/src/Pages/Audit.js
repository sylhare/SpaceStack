import React from "react";
import AuditBabyRequests from "../Components/AuditBabyRequests";

function Audit() {
  return (
    <div className="content">
      <h1>Audit the processed Baby requests</h1>
      <p>
        Processed requests:
      </p>
      <AuditBabyRequests/>
    </div>
  );
}

export default Audit;
