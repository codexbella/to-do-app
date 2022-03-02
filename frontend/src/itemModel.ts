export interface ToDoItem {
    id: string;
    title: string;
    description: string;
    done: boolean;
}

export interface NewItem {
    title: string;
    description: string;
    done: boolean;
}