import React, {useEffect, useState} from "react";
import {ToDoItem, NewItem} from "./itemModel";
import ToDoGalleryItem from "./ToDoGalleryItem";
import './ToDoList.css';

export default function ToDoList() {
    const [searchTerm, setSearchTerm] = useState('');
    const [toDoList, setToDoList] = useState([] as Array<ToDoItem>);

    const [newItem, setNewItem] = useState({title: 'test', description: '', done: false} as NewItem);
    const [currentItem, setCurrentItem] = useState({id: '', title: 'test', description: '', done: false} as ToDoItem);

    const [titleField, setTitleField] = useState('');
    const [descriptionField, setDescriptionField] = useState('');

    const getAll = () => {
        fetch('http://localhost:8080/todoitems/getall')
            .then(response => response.json())
            .then((list: Array<ToDoItem>) => {
                setToDoList(list)
            }).catch(() => console.log('oopsie - getAll'))
    }
    const getAllNotDone = () => {
        fetch('http://localhost:8080/todoitems/getallnotdone')
            .then(response => response.json())
            .then((list: Array<ToDoItem>) => {
                setToDoList(list)
            }).catch(() => console.log('oopsie - getAllNotDone'))
    }

    const addItem = (item: NewItem) => {
        fetch('http://localhost:8080/todoitems/additem', {
            method: 'POST',
            body: JSON.stringify(item),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then((list: Array<ToDoItem>) => {
                setToDoList(list)
            }).catch(() => console.log('oopsie - addItem'))
    }

    useEffect(() => getAll(), [])

    const getMatchingItems = (input: string) => {
        if (input === '') {
            getAll()
        } else {
            fetch('http://localhost:8080/todoitems/'+input)
                .then(response => response.json())
                .then((list: Array<ToDoItem>) => {
                    setToDoList(list)
                }).catch(() => console.log('oopsie - search'))
        }
        setSearchTerm(input)
    }

    const changeItem = (input: string) => {
        fetch('http://localhost:8080/todoitems/'+input, {
            method: 'PUT',
            body: JSON.stringify(currentItem),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then((list: Array<ToDoItem>) => {
                setToDoList(list)
            }).catch(() => console.log('oopsie - changeItem'))
    }

    const changeNewItemTitle = (input: string) => {
        setNewItem({
            title: input,
            description: newItem.description,
            done: newItem.done})
        setTitleField(input)
    }
    const changeNewItemDescription = (input: string) => {
        setNewItem({
            title: newItem.title,
            description: input,
            done: newItem.done})
        setDescriptionField(input)
    }


    return <div><h1 id='title-to-do-list'>To-Do-Liste</h1>

        <button className='getall-button' onClick={getAll}>Alle anzeigen</button>
        <button className='getallnotdone-button' onClick={getAllNotDone}>Offene anzeigen</button>

        <div>
            <input className='new-to-do-item-title' type='text' placeholder='Titel' value={titleField}
                   onChange={typed => changeNewItemTitle(typed.target.value)}
            />

            <input className='new-to-do-item-description' type='text' placeholder='Beschreibung' value={descriptionField}
                   onChange={typed => changeNewItemDescription(typed.target.value)}
            />

            <button className='additem-button' onClick={() => {addItem(newItem); setTitleField(''); setDescriptionField('')}}>
                Neues To-Do anlegen
            </button>
        </div>

        <div>
            <h3>Liste filtern:</h3>
            <input className='search-field' type='text' placeholder='Suchbegriff' value={searchTerm}
                   onChange={typed => getMatchingItems(typed.target.value)}/>
        </div>

        <div id="to-do-items-wrapper">
            {toDoList.map(item => <ToDoGalleryItem toDoItem={item} key={item.id} onChange={setToDoList}/>)}
        </div>
    </div>
}