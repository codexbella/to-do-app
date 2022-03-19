import {ToDoItem} from "./itemModel";
import React, {FormEvent, useState} from "react";
import {useTranslation} from "react-i18next";

interface NewItemProps {
   onChange: (list: Array<ToDoItem>) => void;
   onError: (message: string) => void;
}

export default function NewItem(props: NewItemProps) {
   const [newItemTitle, setNewItemTitle] = useState(localStorage.getItem('new-title') ?? 'test');
   const [newItemDescription, setNewItemDescription] = useState(localStorage.getItem('new-description') ?? '');
   
   const [titleField, setTitleField] = useState(localStorage.getItem('new-title') ?? '');
   const [descriptionField, setDescriptionField] = useState(localStorage.getItem('new-description') ?? '');
   
   const {t} = useTranslation();
   
   const addItem = (event: FormEvent<HTMLFormElement>) => {
      event.preventDefault();
      fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/additem`, {
         method: 'POST',
         body: JSON.stringify({
            title: newItemTitle,
            description: newItemDescription,
            done: false
         }),
         headers: {
            Authorization: `Bearer ${localStorage.getItem('user-token')}`,
            'Content-Type': 'application/json'
         }
      })
         .then(response => {
            if (response.status >= 200 && response.status < 300) {
               return response.json();
            }
            throw new Error(`${t('add-error')}, ${t('error')}: ${response.status}`)
         })
         .then((list: Array<ToDoItem>) => {
            props.onChange(list)
            setNewItemTitle('test')
            setNewItemDescription('')
            setTitleField('')
            setDescriptionField('')
            props.onError('')
         })
         .catch(e => {
            console.log(e.message);
            props.onError(e.message)
         })
   }
   
   return (
      <div>
         <form onSubmit={ev => addItem(ev)}>
            <input type='text' placeholder={t('title-field-placeholder')}
                   value={titleField} onChange={typed => {
               setNewItemTitle(typed.target.value);
               setTitleField(typed.target.value);
               localStorage.setItem('new-title', typed.target.value)
            }}/>
            <input type='text' placeholder={t('description-field-placeholder')}
                   value={descriptionField} onChange={typed => {
               setNewItemDescription(typed.target.value);
               setDescriptionField(typed.target.value);
               localStorage.setItem('new-description', typed.target.value)
            }}/>
            <button type='submit'>{t('new-item')}</button>
         </form>
      </div>
   )
}