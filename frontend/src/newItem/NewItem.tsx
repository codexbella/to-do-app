import {ToDoItem} from "../itemModel";
import React, {FormEvent, useState} from "react";
import {useTranslation} from "react-i18next";

interface NewItemProps {
    onChange: (list: Array<ToDoItem>) => void;
}

export default function NewItem(props: NewItemProps) {
    const [newItemTitle, setNewItemTitle] = useState('test');
    const [newItemDescription, setNewItemDescription] = useState('');

    const [titleField, setTitleField] = useState('');
    const [descriptionField, setDescriptionField] = useState('');

    const { t } = useTranslation();

    const addItem = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/additem`, {
            method: 'POST',
            body: JSON.stringify({
                title: newItemTitle,
                description: newItemDescription,
                done: false
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error(`${t('error')}: ${response.status}`)
            })
            .then((list: Array<ToDoItem>) => {
                props.onChange(list)
                setNewItemTitle('test')
                setNewItemDescription('')
                setTitleField('')
                setDescriptionField('')
            })
            .catch(e => console.log('addItem: '+e.message))
    }

    return (
        <div className='new-item-wrapper'>
            <form onSubmit={ev => addItem(ev)}>
                <input className='new-to-do-item-title' type='text' placeholder={t('title-field-placeholder')}
                   value={titleField} onChange={typed => {setNewItemTitle(typed.target.value); setTitleField(typed.target.value)}}/>
                <input className='new-to-do-item-description' type='text' placeholder={t('description-field-placeholder')}
                   value={descriptionField} onChange={typed => {setNewItemDescription(typed.target.value); setDescriptionField(typed.target.value)}}/>
                <button className='additem-button' type='submit'>{t('new-item')}</button>
            </form>
        </div>
    )
}