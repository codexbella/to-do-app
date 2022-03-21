import './App.css';
import React from 'react';
import {Outlet, useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";

function App() {
   const {t} = useTranslation();
   const nav = useNavigate();
   
   const loginOrLogout = () => {
      if (localStorage.getItem('user-token')) {
         localStorage.setItem('user-token', '');
         nav('login')
      } else {
         nav('/login')
      }
   }
   
   return (
      <div>
         <div><h1 className='color-light' id='text'>{t('title')}</h1></div>
         <button onClick={loginOrLogout} className='no-decoration-text color-dark large'>
            {localStorage.getItem('user-token') ? t('logout') : t('login')}
         </button>
         {!localStorage.getItem('user-token') &&
            <button onClick={() => nav('/register')} className='no-decoration-text color-dark large'>
            {t('register')}
         </button>}
         <div><Outlet/></div>
      </div>
   );
}

export default App;
