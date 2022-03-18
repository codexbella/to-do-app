import {ToDoItem} from '../itemModel';
import './ToDoGalleryItem.css';
import pen from '../images/edit-pen.png';
import {FormEvent, useState} from "react";
import {useTranslation} from 'react-i18next';
import {NavLink} from "react-router-dom";

interface ToDoGalleryItemProps {
   toDoItem: ToDoItem;
   onChange: (list: Array<ToDoItem>) => void;
}

export default function ToDoGalleryItem(props: ToDoGalleryItemProps) {
   const [editMode, setEditMode] = useState(false);
   const [currentItem, setCurrentItem] = useState(props.toDoItem);
   const {t} = useTranslation();
   
   const deleteToDo = (id: string) => {
      fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/${id}`, {
         method: 'DELETE',
         headers: {Authorization: `Bearer ${localStorage.getItem('user-token')}`}
      })
         .then(response => {
            if (response.status >= 200 && response.status < 300) {
               return response.json();
            }
            throw new Error(`${t('delete-error')}, ${t('error')}: ${response.status}`)
         })
         .then((list: Array<ToDoItem>) => props.onChange(list))
         .catch(e => console.log(`${t('delete-error')}: ` + e.message))
   }
   
   const changeStatus = (itemToChange: ToDoItem) => {
      const itemId = itemToChange.id;
      const changedItem = {
         id: itemId,
         title: itemToChange.title,
         description: itemToChange.description,
         done: !itemToChange.done
      };
      fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/${itemId}`, {
         method: 'PUT',
         body: JSON.stringify(changedItem),
         headers: {
            Authorization: `Bearer ${localStorage.getItem('user-token')}`,
            'Content-Type': 'application/json'
         }
      })
         .then(response => {
            if (response.status >= 200 && response.status < 300) {
               return response.json();
            }
            throw new Error(`${t('change-status-error')}, ${t('error')}: ${response.status}`)
         })
         .then((list: Array<ToDoItem>) => props.onChange(list))
         .catch(e => console.log(`${t('change-status-error')}: ` + e.message))
   }
   
   const changeItem = (event: FormEvent<HTMLFormElement>, item: ToDoItem) => {
      event.preventDefault();
      fetch(`${process.env.REACT_APP_BASE_URL}/todoitems/${props.toDoItem.id}`, {
         method: 'PUT',
         body: JSON.stringify(item),
         headers: {
            Authorization: `Bearer ${localStorage.getItem('user-token')}`,
            'Content-Type': 'application/json'
         }
      })
         .then(response => {
            if (response.status >= 200 && response.status < 300) {
               return response.json();
            }
            throw new Error(`${t('change-item-error')}, ${t('error')}: ${response.status}`)
         })
         .then((list: Array<ToDoItem>) => props.onChange(list))
         .catch(e => console.log(`${t('change-item-error')}: ` + e.message))
      setEditMode(false);
   }
   
   return (
      <span className={props.toDoItem.done ? 'to-do-item-done' : 'to-do-item-not-done'}>
            {
               editMode ?
                  <div>
                     <form onSubmit={ev => changeItem(ev, currentItem)}>
                        <input type='text' placeholder={t('title-field-placeholder')} value={currentItem.title}
                               onChange={typed => setCurrentItem({
                                  id: currentItem.id,
                                  title: typed.target.value,
                                  description: currentItem.description,
                                  done: currentItem.done
                               })}
                        />
                        <input type='text' placeholder={t('description-field-placeholder')} value={currentItem.description}
                               onChange={typed => setCurrentItem({
                                  id: currentItem.id,
                                  title: currentItem.title,
                                  description: typed.target.value,
                                  done: currentItem.done
                               })}
                        />
                        <button type='submit'>OK</button>
                     </form>
                  </div>
                  :
                  <NavLink to={`/todoitems/${props.toDoItem.id}`} className='detail-link no-decoration-text' onClick={() => {
                     localStorage.setItem('current-item-title', props.toDoItem.title);
                     localStorage.setItem('current-item-description', props.toDoItem.description);
                     localStorage.setItem('current-item-status', String(props.toDoItem.done))
                  }}>
                     <div>
                        <div className='color-light large small-caps'>{props.toDoItem.title}</div>
                     </div>
                  </NavLink>
            }
         <img className="image-edit-pen pointer" src={pen} alt={t('edit')} onClick={() => setEditMode(true)}/>
            <span className={`${props.toDoItem.done ? 'color-done' : 'color-not-done'} large pointer`}
                  onClick={() => changeStatus(props.toDoItem)}>&#10004;</span>
            <span className='bold large red pointer'
                  onClick={() => deleteToDo(props.toDoItem.id)}>&#10005;</span>
        </span>
   )
}