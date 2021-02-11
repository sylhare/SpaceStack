import React, {useEffect, useReducer, useState} from 'react';
import axios from 'axios';
import {listReducer} from "./ManageBabyRequests";
import {authHeader} from "../Services/AuthService";


const AuditBabyRequests = () => {
  const [requests, dispatchListData] = useReducer(listReducer, {list: []});
  const [error, setError] = useState(null);

  useEffect(() => {
    let unmounted = false;
    axios.get('/v1/baby/request/audit',{ headers: authHeader()})
      .then(
        (result) => {
          if(!unmounted) {
            dispatchListData({type: 'LOAD_ITEM', data: result.data.requests});
            setError(false)
          }
        },
        (_) => {
          setError(true)
        }
      );
    return () => { unmounted = true };
  }, []);

  return error ? <i>Unexpected error while retrieving requests</i> : <ProcessedRequests list={requests.list} />;
};

const ProcessedRequests = ({list}) => {
  return list.length === 0 ? <i>There are no processed requests</i> : <Table list={list}/>
};

const Table = ({list}) => {
  return (<table className='processed-requests'>
    <thead>
    <tr>
      <th>👶 Baby Name</th>
      <th>Request Author</th>
      <th>Status</th>
      <th>Reviewer</th>
      <th>Date</th>
    </tr>
    </thead>
    <tbody>
    {list.map((item) => (
      <tr key={item.id}>
        <th><i>{item.name}</i></th>
        <th>{item.author}</th>
        <th><b>{item.status.toUpperCase()}</b></th>
        <th>{item.reviewer}</th>
        <th>{new Date(parseInt(item.timestamp)).toString().slice(0, 24)}</th>
      </tr>
    ))}
    </tbody>
  </table>)
};

export default AuditBabyRequests;
