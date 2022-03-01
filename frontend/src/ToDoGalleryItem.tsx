import {ToDoItem} from './itemModel';
import './ToDoGalleryItem.css';
import pen from './images/edit-pen.png';

interface ToDoGalleryItemProps {
    toDoItem: ToDoItem;
    onChange: (list: Array<ToDoItem>) => void;
}

export default function ToDoGalleryItem(props: ToDoGalleryItemProps) {

    const deleteToDo = (id: string) => {
        fetch('http://localhost:8080/todoitems/'+id, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error('Fehlercode: '+response.status)
            })
            .then((list: Array<ToDoItem>) => props.onChange(list))
            .catch(e => console.log('deleteitem: '+e.message))
    }

    const changeStatus = (itemToChange: ToDoItem) => {
        const itemId = itemToChange.id;
        const changedItem = {id: itemId, title: itemToChange.title, description: itemToChange.description, done: !itemToChange.done};
        fetch('http://localhost:8080/todoitems/'+itemId, {
            method: 'PUT',
            body: JSON.stringify(changedItem),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
                throw new Error('Fehlercode: '+response.status)
            })
            .then((list: Array<ToDoItem>) => props.onChange(list))
            .catch(e => console.log('changeStatus: '+e.message))
    }

    return (
        <span className={props.toDoItem.done ? 'to-do-item-done': 'to-do-item-not-done'}>
            <div className="to-do-item-title">{ props.toDoItem.title }</div>
            <span className="gallery-item-description">{ props.toDoItem.description }</span>
            <img className="item-edit-pen" src={pen} alt='Bearbeiten'/>
            <span id="checkmark" onClick={() => changeStatus(props.toDoItem)}>&#10004;</span>
            <span id="x-for-delete" onClick={() => deleteToDo(props.toDoItem.id)} >&#10005;</span>
        </span>
    )
}