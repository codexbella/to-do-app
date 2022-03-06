import {useTranslation} from "react-i18next";
import {NavLink} from "react-router-dom";
import React from "react";

export default function ToDoDetail() {
   const { t } = useTranslation();
   
   return <div>
      <button><NavLink to='/todoitems' className='no-decoration-text color-dark large'>{t('link-all-todos')}</NavLink></button>
   
      <div className={localStorage.getItem('current-item-status') === 'true' ? 'to-do-item-done' : 'to-do-item-not-done'}>
         <div className="color-light large small-caps">{localStorage.getItem('current-item-title')}</div>
         <div className="color-light large">{localStorage.getItem('current-item-description') === '' ? ' ' : localStorage.getItem('current-item-description')}</div>
      </div>
   </div>
}