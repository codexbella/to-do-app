import React, {FormEvent, useEffect, useState} from "react";
import {useTranslation} from 'react-i18next';
import {useNavigate} from "react-router-dom";

export default function Register() {
   const [usernameField, setUsernameField] = useState('');
   const [passwordField, setPasswordField] = useState('');
   const [passwordFieldAgain, setPasswordFieldAgain] = useState('');
   const {t} = useTranslation();
   const [error, setError] = useState('');
   const nav = useNavigate();
   
   useEffect(() => {
      if (localStorage.getItem('user-token')) {
         nav('/todoitems')
      }
   }, [nav])
   
   const register = (event: FormEvent<HTMLFormElement>) => {
      event.preventDefault()
      setError('');
      if (passwordField === passwordFieldAgain) {
      fetch(`${process.env.REACT_APP_BASE_URL}/users/register`, {
         method: 'POST',
         body: JSON.stringify({
            username: usernameField,
            password: passwordField,
            passwordAgain: passwordFieldAgain
         }),
         headers: {
            'Content-Type': 'application/json'
         }
      })
         .then(response => {
            if (response.status >= 200 && response.status < 300) {
               return response.text();
            }
            throw new Error(`${t('new-user-error')}, ${t('error')}: ${response.status}`)
         })
         .then(() => {nav('/login')})
      } else {
         setError(`${t('password-not-equal-error')}`)
         throw new Error(t('password-not-equal-error'))
      }
   }
   
   return <div>
       <form onSubmit={ev => register(ev)}>
               <input type='text' placeholder={t('username')} value={usernameField}
                      onChange={ev => setUsernameField(ev.target.value)}/>
               <input type='password' placeholder={t('password')} value={passwordField}
                      onChange={ev => setPasswordField(ev.target.value)}/>
          <input type='password' placeholder={t('password-again')} value={passwordFieldAgain}
                 onChange={ev => setPasswordFieldAgain(ev.target.value)}/>
               <button type='submit'>{t('register')}</button>
            </form>
      {error && <div className='color-light margin-top-bottom'>{`${t('error')}: `+error}.</div>}
   </div>
}