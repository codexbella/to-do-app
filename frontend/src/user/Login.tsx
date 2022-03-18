import React, {FormEvent, useState} from "react";
import { useTranslation } from 'react-i18next';

export default function Login() {
   const [usernameField, setUsernameField] = useState('');
   const [passwordField, setPasswordField] = useState('');
   const { t } = useTranslation();
   
   const login = (event: FormEvent<HTMLFormElement>) => {
      event.preventDefault()
      fetch(`${process.env.REACT_APP_BASE_URL}/users/login`, {
         method: 'POST',
         body: JSON.stringify({
            username: usernameField,
            password: passwordField
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
         .then(response => {
            localStorage.setItem('user-token', response);
            setUsernameField('');
            setPasswordField('');
         })
   }
   
   return <div>
      <form onSubmit={ev => login(ev)}>
         <input type='text' placeholder={t('username')} value={usernameField} onChange={ev => {setUsernameField(ev.target.value)}}/>
         <input type='text' placeholder={t('password')} value={passwordField} onChange={ev => {setPasswordField(ev.target.value)}}/>
         <button type='submit'>{t('Login')}</button>
      </form>
   </div>
}