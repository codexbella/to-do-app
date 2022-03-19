import {render, screen, waitFor} from '@testing-library/react';
import ToDoList from "./ToDoList";
import testData from "./testData.json";

test('that error handling works', async () => {
/*  jest.spyOn(global, 'fetch').mockImplementation(() => {
    // (URL: string) => {
    // expect(URL).toEqual("http://localhost:8080//todoitems/getall"")
    // expect(URL).toEqual("http://localhost:8080//todoitems/getallnotdone"")
    return Promise.resolve({
      status: 404,
      json: () => Promise.resolve(testData)
    } as Response);
  });
  
  render(<ToDoList />);
  
  await waitFor(() => {
    expect(screen.getByTestId('error').textContent).toBe('get-all-error, error: 404');
  })*/
})