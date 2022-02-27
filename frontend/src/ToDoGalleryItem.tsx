import {ToDoItem} from "./itemModel";

interface ToDoItemProps {
    toDoItem: ToDoItem;
}

const determineStatus = (input: boolean) => {
    if (!input) {
        return "offen"
    } else {
        return "erledigt"
    }
}

export default function ToDoGalleryItem(props: ToDoItemProps) {
    return (
        <div className="to-do-item">
            <h3 className="to-do-item-title">{ props.toDoItem.title }</h3>
            <p className="gallery-item-description">{ props.toDoItem.description }</p>
            <p className="gallery-item-done">{ determineStatus(props.toDoItem.done) }</p>
        </div>
    )
}