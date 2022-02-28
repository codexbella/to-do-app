import {ToDoItem} from './itemModel';
import './ToDoGalleryItem.css';
import pen from './images/edit-pen.png';

interface ToDoGalleryItemProps {
    toDoItem: ToDoItem;
    onChange: (list: Array<ToDoItem>) => void;
}

export default function ToDoGalleryItem(props: ToDoGalleryItemProps) {
    const deleteToDo = (itemToDelete: ToDoItem) => {
        fetch('http://localhost:8080/todoitems/delete', {
            method: 'POST',
            body: JSON.stringify(itemToDelete),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then((list: Array<ToDoItem>) => props.onChange(list))
            .catch(() => console.log('oopsie - deleteItem'))
    }
    const changeStatus = (itemToChange: ToDoItem) => {
        if (itemToChange.done) {
            fetch('http://localhost:8080/todoitems/setasnotdone', {
                method: 'POST',
                body: JSON.stringify(itemToChange),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then((list: Array<ToDoItem>) => props.onChange(list))
                .catch(() => console.log('oopsie - changeItem'))
        } else {
            fetch('http://localhost:8080/todoitems/setasdone', {
                method: 'POST',
                body: JSON.stringify(itemToChange),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then((list: Array<ToDoItem>) => props.onChange(list))
                .catch(() => console.log('oopsie - changeItem'))
        }
    }

    return (
        <span className={props.toDoItem.done ? 'to-do-item-done': 'to-do-item-not-done'}>
            <div className="to-do-item-title">{ props.toDoItem.title }</div>
            <span className="gallery-item-description">{ props.toDoItem.description }</span>
            <img className="item-edit-pen" src={pen} alt='Bearbeiten'/>
            <span id="checkmark" onClick={() => changeStatus(props.toDoItem)}>&#10004;</span>
            <span id="x-for-delete" onClick={() => deleteToDo(props.toDoItem)} >&#10005;</span>
        </span>
    )
}