import React, {Suspense} from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import App from './App';
import reportWebVitals from './reportWebVitals';
import './i18n';
import ToDoList from "./ToDoList";
import ToDoDetail from "./ToDoDetail";

ReactDOM.render(
   <React.StrictMode>
      <Suspense fallback="Loading...">
         <BrowserRouter>
            <Routes>
               <Route path='/' element={<App/>}>
                  <Route path='todoitems' element={<ToDoList/>}/>
                  <Route path='todoitems/:id' element={<ToDoDetail/>}/>
                  <Route path='*' element={<ToDoList/>}/>
               </Route>
            </Routes>
         </BrowserRouter>
      </Suspense>
   </React.StrictMode>,
   document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
