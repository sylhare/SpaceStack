import React, {useEffect, useState} from 'react';
import axios from 'axios';


export default function PopulationCount() {
  const [error, setError] = useState(null);
  const [items, setItems] = useState('Not available');

  useEffect(() => {
    let unmounted = false;
    axios.get('/v1/population')
      .then(
        (result) => {
          if (!unmounted) setItems(result.data);
        },
        (error) => {
          if (!unmounted) setError(error);
        }
      );

    return () => { unmounted = true };
  }, []);

  if (error) {
    return <div className='PopulationCount'>Error : {error.message}</div>;
  } else {
    return <div className='PopulationCount'><p>The population counter:</p><h3>{items.amount}</h3></div>;
  }
}
