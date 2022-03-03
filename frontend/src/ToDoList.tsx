import React, {useEffect, useState} from "react";
import {ToDoItem} from "./itemModel";
import ToDoGalleryItem from "./GalleryItem/ToDoGalleryItem";
import './ToDoList.css';
import { useTranslation } from 'react-i18next';
import NewItem from "./newItem/NewItem";

export default function ToDoList() {
    const [searchTerm, setSearchTerm] = useState('');
    const [toDoList, setToDoList] = useState([] as Array<ToDoItem>);

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

    useEffect(() => getAll(), [])

    return <div><h1 id='title-to-do-list'>{t('title')}</h1>

        <button className='getall-button' onClick={getAll}>{t('show-all')}</button>
        <button className='getallnotdone-button' onClick={getAllNotDone}>{t('show-all-not-done')}</button>

        <NewItem onChange={setToDoList}/>

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