import {render, screen, waitFor} from '@testing-library/react';
import ToDoList from "./ToDoList";
import {MemoryRouter} from "react-router-dom";
import testData from "./testData.json";
//require('react-dom');

/*test('that searching works', async () => {
  // Add this in your component file
/!*  window.React2 = require('react');
  console.log(window.React1 === window.React2);*!/
  
  jest.spyOn(global, 'fetch').mockImplementation(() => {
    return Promise.resolve({
      status: 200,
      json: () => Promise.resolve(testData)
    } as Response);
  });
  
  render(<ToDoList />, {wrapper: MemoryRouter});
  
  const searchField = screen.getByTestId('search-field') as HTMLInputElement;
  fireEvent.change(searchField, {target: {value: 'pflanz'}});
  
  await waitFor(() => {
    expect(screen.getAllByTestId('to-do-gallery-item').length).toBe(2);
  })
})*/

test('that error handling works', async () => {
  jest.spyOn(global, 'fetch').mockImplementation(() => {
    return Promise.resolve({
      status: 404,
      json: () => Promise.resolve(testData)
    } as Response);
  });
  
  render(<ToDoList />, {wrapper: MemoryRouter});
  
  await waitFor(() => {
    expect(screen.getByTestId('error').textContent).toBe('');
  })
})