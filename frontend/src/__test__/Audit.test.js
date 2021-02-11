import React from 'react'
import {rest} from 'msw'
import {setupServer} from 'msw/node'
import {render, waitFor} from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect'
import Audit from '../Pages/Audit';


const server = setupServer(
  rest.get('/v1/baby/request/audit', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        requests: [
          {name: 'first baby request', status: 'approved', id: '1', author: 'jest author', reviewer: 'reviewer', timestamp: '1612844443333'},
          {name: 'second baby request', status: 'denied', id: '2', author: 'jest author', reviewer: 'reviewer', timestamp: '1612844443333'}
        ]
      }))
  }),
);

beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

test('Load and display the processed baby requests', async () => {
  const {getByText, getAllByText} = render(<Audit/>);

  await waitFor(() => getAllByText(/first/i));

  expect(getAllByText(/jest author/i).length).toBe(2);
  expect(getByText('second baby request')).toBeTruthy()
});
