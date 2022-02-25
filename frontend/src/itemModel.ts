export interface ToDoItem {
    title: string;
    description: string;
    done: boolean;
}

export interface ResponseBody {
    data: Array<ToDoItem>
}