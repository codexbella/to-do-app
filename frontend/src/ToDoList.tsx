import React, {useEffect, useState} from "react";
import {ToDoItem} from "./itemModel";
import ToDoGalleryItem from "./ToDoGalleryItem";
import './ToDoList.css';
import { useTranslation } from 'react-i18next';

export default function ToDoList() {
    const [searchTerm, setSearchTerm] = useState('');
    const [toDoList, setToDoList] = useState([] as Array<ToDoItem>);
    const [newItemTitle, setNewItemTitle] = useState('test');
    const [newItemDescription, setNewItemDescription] = useState('');

    const [titleField, setTitleField] = useState('');
    const [descriptionField, setDescriptionField] = useState('');

    const { t } = useTranslation();

    const getAll = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/getall`)
            .then(response => response.json())
            .then((list: Array<ToDoItem>) => setToDoList(list))
            .catch(() => console.log('oopsie - getAll'))
        setSearchTerm('')
    }

    const getAllNotDone = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/getallnotdone`)
            .then(response => response.json())
            .then((list: Array<ToDoItem>) => setToDoList(list))
            .catch(() => console.log('oopsie - getAllNotDone'))
        setSearchTerm('')
    }

    const getMatchingItems = (input: string) => {
        if (input === '') {
            return getAll();
        } else {
            fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/${input}`)
                .then(response => response.json())
                .then((list: Array<ToDoItem>) => setToDoList(list))
                .catch(() => console.log('oopsie - search'))
        }
        setSearchTerm('')
    }

    const addItem = () => {
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
                setToDoList(list)
                setNewItemTitle('test')
                setNewItemDescription('')
                setTitleField('')
                setDescriptionField('')
            })
            .catch(e => console.log('addItem: '+e.message))
    }

    useEffect(() => getAll(), [])

    return <div><h1 id='title-to-do-list'>{t('title')}</h1>

        <button className='getall-button' onClick={getAll}>{t('show-all')}</button>
        <button className='getallnotdone-button' onClick={getAllNotDone}>{t('show-all-not-done')}</button>

        <div>
            <input className='new-to-do-item-title' type='text' placeholder={t('title-field-placeholder')}
                   value={titleField} onChange={typed => {setNewItemTitle(typed.target.value); setTitleField(typed.target.value)}}/>
            <input className='new-to-do-item-description' type='text' placeholder={t('description-field-placeholder')}
                   value={descriptionField} onChange={typed => {setNewItemDescription(typed.target.value); setTitleField(typed.target.value)}}/>
            <button className='additem-button' onClick={() => addItem()}>{t('new-item')}</button>
        </div>

        <div>
            <h3>{t('filter-list')}:</h3>
            <input className='search-field' type='text' placeholder={t('search-term')} value={searchTerm}
                   onChange={typed => getMatchingItems(typed.target.value)}/>
        </div>

        <div id="to-do-items-wrapper">
            {toDoList.map(item => <ToDoGalleryItem toDoItem={item} key={item.id} onChange={setToDoList}/>)}
        </div>
    </div>
}