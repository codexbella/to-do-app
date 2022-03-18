import {useTranslation} from "react-i18next";
import {NavLink, useNavigate} from "react-router-dom";
import React, {useEffect} from "react";

export default function ToDoDetail() {
   const { t } = useTranslation();
   const nav = useNavigate();
   
   useEffect(() => {if (localStorage.getItem('user-token')) {} else {nav('/login')}}, [nav])
   
   return <div>
      <button><NavLink to='/todoitems' className='no-decoration-text color-dark large'>{t('link-all-todos')}</NavLink></button>
   
      <div className={localStorage.getItem('current-item-status') === 'true' ? 'to-do-item-done' : 'to-do-item-not-done'}>
         <div className="color-light large small-caps">{localStorage.getItem('current-item-title')}</div>
         <div className="color-light large">{localStorage.getItem('current-item-description') === '' ? ' ' : localStorage.getItem('current-item-description')}</div>
      </div>
   </div>
}