import {ToDoItem} from "./itemModel";

interface ToDoItemProps {
    toDoItem: ToDoItem;
}

export default function ToDoGalleryItem(props: ToDoItemProps) {
    return (
        <div className="to-do-item">
            <h1 className="to-do-item-title">{ props.toDoItem.title }</h1>
            <div className="gallery-item-description">{ props.toDoItem.description }</div>
            <div className="gallery-item-done">{ props.toDoItem.done }</div>
        </div>
    )
}