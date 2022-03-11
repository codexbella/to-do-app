import React, {useCallback, useEffect, useState} from "react";
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
    
    const getAll = useCallback(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/getall`)
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error(`${t('get-all-error')}, ${t('error')}: ${response.status}`)
            })
            .then((list: Array<ToDoItem>) => {setToDoList(list); setErrorMessage('')})
            .catch(e => {console.log(e.message); setErrorMessage(e.message)})
        setSearchTerm('')
    },[t]);
    
    useEffect(() => getAll(), [getAll])
    
    const getAllNotDone = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/getallnotdone`)
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error(`${t('get-notdone-error')}, ${t('error')}: ${response.status}`)
            })
            .then((list: Array<ToDoItem>) => {setToDoList(list); setErrorMessage('')})
            .catch(e => {console.log(e.message); setErrorMessage(e.message)})
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
                .catch(e => {console.log(e.message); setErrorMessage(e.message)})
        }
        setSearchTerm(input)
    }
    
    return <div>
        <div className='margin-top-bottom'>
        <button className='getall-button' onClick={getAll}>{t('show-all')}</button>
        <button className='getallnotdone-button' onClick={getAllNotDone}>{t('show-all-not-done')}</button>
        </div>
        <NewItem onChange={setToDoList} onError={setErrorMessage}/>
        <div className='margin-top-bottom'>
            <span className='color-light large'>{t('filter-list')}:</span>
            <input className='search-field' type='text' placeholder={t('search-term')} value={searchTerm} onChange={typed => getMatchingItems(typed.target.value)}/>
        </div>

        <div className="to-do-items-wrapper margin-top-bottom">
            {toDoList.map(item => <ToDoGalleryItem toDoItem={item} key={item.id} onChange={setToDoList}/>)}
        </div>
        <div className='color-light margin-top-bottom'>{errorMessage ? <div data-testid='error'>{errorMessage}</div> : <div>{t('no-error')}</div>}</div>
    </div>
}