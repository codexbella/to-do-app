import React, {useEffect, useState} from "react";
import {ToDoItem} from "./itemModel";
import ToDoGalleryItem from "./ToDoGalleryItem";

export default function ToDoList() {
    const [searchTerm, setSearchTerm] = useState('');
    const [urlEnd, setUrlEnd] = useState('getall');
    const [toDoList, setToDoList] = useState([] as Array<ToDoItem>);
    const [currentItem, setCurrentItem] = useState({title: 'test', description: '', done: false} as ToDoItem);
    const [askingMethod, setAskingMethod] = useState('GET');

    const askBackend = (url: string = urlEnd) => {
        if (askingMethod === 'GET') {
            fetch('http://localhost:8080/todoitems/'+url)
                .then(response => response.json())
                .then((list: Array<ToDoItem>) => {
                    setToDoList(list)
                }).catch(() => console.log('oopsie - get'))
        } else if (askingMethod === 'POST') {
            fetch('http://localhost:8080/todoitems/'+url, {
                method: 'POST',
                body: JSON.stringify(currentItem),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then((list: Array<ToDoItem>) => {
                    setToDoList(list)
                }).catch(() => console.log('oopsie - post'))
        }
    }

    useEffect((url = urlEnd) => askBackend(url), [])

    const handleSearchInput = (input: string) => {
        setAskingMethod('GET')
        if (input === '') {
            setUrlEnd('getall')
            askBackend('getall')
        } else {
            setUrlEnd(input)
            askBackend(input)
        }
        setSearchTerm(input)
    }
    const changeCurrentItemTitle = (input: string) => {
        setCurrentItem({
            title: input,
            description: currentItem.description,
            done: currentItem.done})
    }
    const changeCurrentItemDescription = (input: string) => {
        setCurrentItem({
            title: currentItem.title,
            description: input,
            done: currentItem.done})
    }

    return <div><h1 id='title-to-do-list'>To-Do-Liste</h1>

        <button onClick={() => {
            setAskingMethod('GET')
            setSearchTerm('')
            askBackend('getall')
        }
        } className='getall-button'>Alle anzeigen</button>

        <button onClick={() => {
            setAskingMethod('GET')
            setSearchTerm('')
            askBackend('getallnotdone')
        }
        } className='getallnotdone-button'>Offene anzeigen</button>

        <div>
            <input type='text' placeholder='Titel' onChange={typed => changeCurrentItemTitle(typed.target.value)} className='new-to-do-item-title'/>
            <input type='text' placeholder='Beschreibung' onChange={typed => changeCurrentItemDescription(typed.target.value)} className='new-to-do-item-description'/>
            <button onClick={() => {
                setAskingMethod('POST')
                setSearchTerm('')
                askBackend('additem')}} className='additem-button'>Neues To-Do anlegen</button>
        </div>

        <div>
            <h3>Liste filtern:</h3>
            <input type='text' placeholder='Suchbegriff' value={searchTerm}
                   onChange={typed => handleSearchInput(typed.target.value)} className='search-field'/>
        </div>

        <div>
            {toDoList.map(item => <ToDoGalleryItem toDoItem={item} key={JSON.stringify(item.title)}/>)}
        </div>
    </div>
}