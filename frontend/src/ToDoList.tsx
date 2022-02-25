import {useEffect, useState} from "react";
import {ToDoItem, ResponseBody} from "./itemModel";
import ToDoGalleryItem from "./ToDoGalleryItem";


export default function ToDoList() {
    const [searchTerm, setSearchTerm] = useState('');
    const [urlEnd, setUrlEnd] = useState('getall');
    const [toDoList, setToDoList] = useState([] as Array<ToDoItem>);

    const fetchData = (url: string = 'http://localhost:8080/todoitems/'+urlEnd) => {
        fetch(url)
            .then(response => response.json())
            .then((response: Array<ToDoItem>) => {
                setToDoList(response)
            }).catch(() => console.log('oopsie'))
    }

    useEffect(() => fetchData('http://localhost:8080/todoitems/'+urlEnd), [urlEnd])

    return <div><h1>To-Do-Liste</h1>
        <button onClick={() => setUrlEnd('getall')} className='getall-button'>Alle anzeigen</button>
        <button onClick={() => setUrlEnd('getallnotdone')} className='getallnotdone-button'>Offene anzeigen</button>
        <button className='additem-button'>Neues To-Do anlegen</button>

        <div>
            <h3>To-Do(s) filtern:</h3>
            <input type='text' placeholder='Tippe deinen Suchbegriff ein.' value={searchTerm}
                   onChange={typed => setSearchTerm(typed.target.value)} className='search-field'/>
        </div>

        <div>
            {toDoList.map(item => <ToDoGalleryItem toDoItem={item}/>)}
        </div>

        <div>searchItem</div>
    </div>
}