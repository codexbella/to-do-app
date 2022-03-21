import React, {useCallback, useEffect, useState} from "react";
import {ToDoItem} from "./itemModel";
import ToDoGalleryItem from "./ToDoGalleryItem";
import './App.css';
import { useTranslation } from 'react-i18next';
import NewItem from "./NewItem";
import {useNavigate} from "react-router-dom";

export default function ToDoList() {
    const [searchTerm, setSearchTerm] = useState('');
    const [toDoList, setToDoList] = useState([] as Array<ToDoItem>);
    const [error, setError] = useState('');
    const { t } = useTranslation();
    const nav = useNavigate();
    
    
    const getAll = useCallback(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/getall`, {
            method: 'GET',
            headers: {Authorization: `Bearer ${localStorage.getItem('user-token')}`}
        })
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error(`${t('get-all-error')}, ${t('error')}: ${response.status}, ${t('try-logout-login')}.`)
            })
            .then((list: Array<ToDoItem>) => {setToDoList(list); setError('')})
            .catch(e => {console.log(e.message); setError(e.message)})
        setSearchTerm('')
    },[t]);
    
    useEffect(() => {
        if (localStorage.getItem('user-token')) {
            getAll()
        } else {
        nav('/login')
        }
    }, [getAll, nav])
    
    const getAllNotDone = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/getallnotdone`, {
            method: 'GET',
            headers: {Authorization: `Bearer ${localStorage.getItem('user-token')}`}
        })
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error(`${t('get-notdone-error')}, ${t('error')}: ${response.status}`)
            })
            .then((list: Array<ToDoItem>) => {setToDoList(list); setError('')})
            .catch(e => {console.log(e.message); setError(e.message)})
        setSearchTerm('')
    }

    const getMatchingItems = (input: string) => {
        if (input === '') {
            return getAll();
        } else {
            fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/${input}`, {
                method: 'GET',
                headers: {Authorization: `Bearer ${localStorage.getItem('user-token')}`}
            })
                .then(response => {
                    if (response.status >= 200 && response.status < 300) {
                        return response.json();
                    }
                    throw new Error(`${t('get-matching-error')}, ${t('error')}: ${response.status}`)
                })
                .then((list: Array<ToDoItem>) => {setToDoList(list); setError('')})
                .catch(e => {console.log(e.message); setError(e.message)})
        }
        setSearchTerm(input)
    }
    
    return <div>
        <div className='margin-top-bottom'>
        <button className='getall-button' onClick={getAll}>{t('show-all')}</button>
        <button className='getallnotdone-button' onClick={getAllNotDone}>{t('show-all-not-done')}</button>
        </div>
        <NewItem onChange={setToDoList} onError={setError}/>
        <div className='margin-top-bottom'>
            <span className='color-light large'>{t('filter-list')}:</span>
            <input className='search-field' type='text' placeholder={t('search-term')} value={searchTerm} onChange={typed => getMatchingItems(typed.target.value)}/>
        </div>

        <div className="to-do-items-wrapper margin-top-bottom">
            {toDoList.map(item => <ToDoGalleryItem toDoItem={item} key={item.id} onChange={setToDoList}/>)}
        </div>
        {error && <div className='color-light margin-top-bottom'>{`${t('error')}: `+error}.</div>}
    </div>
}