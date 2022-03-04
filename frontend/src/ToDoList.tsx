import React, {useEffect, useState} from "react";
import {ToDoItem} from "./itemModel";
import ToDoGalleryItem from "./GalleryItem/ToDoGalleryItem";
import './ToDoList.css';
import { useTranslation } from 'react-i18next';
import NewItem from "./newItem/NewItem";

export default function ToDoList() {
    const [searchTerm, setSearchTerm] = useState('');
    const [toDoList, setToDoList] = useState([] as Array<ToDoItem>);
    const [errorMessage, setErrorMessage] = useState('');

    const { t } = useTranslation();
    
    useEffect(() => getAll(), [])
    
    const getAll = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/getall`)
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error(`${t('get-all-error')}, ${t('error')}: ${response.status}`)
            })
            .then((list: Array<ToDoItem>) => {setToDoList(list); setErrorMessage('')})
            .catch(e => {console.log(`${t('get-all-error')}: ${e.message}`); setErrorMessage(e.message)})
        setSearchTerm('')
    }

    const getAllNotDone = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/getallnotdone`)
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error(`${t('get-notdone-error')}, ${t('error')}: ${response.status}`)
            })
            .then((list: Array<ToDoItem>) => {setToDoList(list); setErrorMessage('')})
            .catch(e => {console.log(`${t('get-notdone-error')}: ${e.message}`); setErrorMessage(e.message)})
        setSearchTerm('')
    }

    const getMatchingItems = (input: string) => {
        if (input === '') {
            return getAll();
        } else {
            fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/${input}`)
                .then(response => {
                    if (response.status >= 200 && response.status < 300) {
                        return response.json();
                    }
                    throw new Error(`${t('get-matching-error')}, ${t('error')}: ${response.status}`)
                })
                .then((list: Array<ToDoItem>) => {setToDoList(list); setErrorMessage('')})
                .catch(e => {console.log(`${t('get-matching-error')}: ${e.message}`); setErrorMessage(e.message)})
        }
        setSearchTerm('')
    }
    
    return <div><h1 id='title-to-do-list'>{t('title')}</h1>

        <button className='getall-button' onClick={getAll}>{t('show-all')}</button>
        <button className='getallnotdone-button' onClick={getAllNotDone}>{t('show-all-not-done')}</button>

        <NewItem onChange={setToDoList} data-testid='error' onError={setErrorMessage}/>
        {errorMessage ? <div data-testid='error'>{errorMessage}</div> : <div>{t('no-error')}</div>}
        <div>
            <h3>{t('filter-list')}:</h3>
            <input className='search-field' type='text' placeholder={t('search-term')} value={searchTerm} data-testid='search-field'
                   onChange={typed => getMatchingItems(typed.target.value)}/>
        </div>

        <div id="to-do-items-wrapper">
            {toDoList.map(item => <ToDoGalleryItem toDoItem={item} key={item.id} data-testid="to-do-gallery-item" onChange={setToDoList}/>)}
        </div>
    </div>
}