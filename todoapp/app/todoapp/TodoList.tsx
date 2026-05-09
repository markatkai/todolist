import React, { useEffect, useState } from 'react';
import axios from "axios";

import type { TodoItem } from "../types/TodoItem"
import { Button } from './Button';

/**
 * Field and button for adding new todo-items to the list.
 */
function NewTodoItem() {
    const [text, setText] = useState("");

    const printText = (event: React.ChangeEvent<HTMLInputElement>) => {
        console.log(event.currentTarget.value);
        setText(event.currentTarget.value)
    }

    const sendPostRequest = (text: string) => {
        axios.post<TodoItem>("/api/notes", {
            text: text
        }).then((response) => {
            console.log(response);
            setText("")
            window.location.reload() // TODO Lazy way to do this...
        });
    }

    const createTodo = () => {
        if (text) {
            console.log("And it is ok to try to send it")
            // Create a new todo item
            sendPostRequest(text)
        }
    }

    const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            if (text) {
                sendPostRequest(text)
            }
        }
    }

    return (
        <div id='todo_new_item'>
            <input onChange={printText} onKeyDown={handleKeyDown} />
            <Button title="Add" buttonOnClick={createTodo} />
        </div>
    );
}


/**
 * List of currently existing todo-items
 * @returns 
 */
function TodoItemList() {
    const [notes, setNotes] = useState<TodoItem[]>([]);
    const [doneNotes, setDoneNotes] = useState<TodoItem[]>([]);

    useEffect(() => {
        axios.get<TodoItem[]>("/api/notes?status=UNFINISHED")
            .then(res => setNotes(res.data));
        axios.get<TodoItem[]>("/api/notes?status=FINISHED")
            .then(res => setDoneNotes(res.data));
    }, []);

    const markAsDone = (id: number) => {
        axios.put<TodoItem>("/api/notes/" + id, {
            status: "FINISHED"
        }).then((response) => {
            console.log(response);
            window.location.reload() // TODO Lazy way to do this...
        });
    }
    const deleteTask = (id: number) => {
        axios.delete<TodoItem>("/api/notes/" + id).then((response) => {
            console.log(response);
            window.location.reload() // TODO Lazy way to do this...
        });
    }

    return (
        <div>
            <div className="tasklist" id="todo_tasks_unfinished">
            <h1 id="header_tasks_unfinished">TODO:</h1>
            {notes.map((note, i) => (
                <div>
                    <Button className='button_mark_done' buttonOnClick={() => {markAsDone(note.id)}}/>
                    <Button className='button_remove' buttonOnClick={() => {deleteTask(note.id)}}/>
                    {note.text}
                </div>
            ))}
            </div>
            <div className="tasklist" id="todo_tasks_finished">
            <h1 id="header_tasks_finished">Finished tasks:</h1>
            {doneNotes.map((note, i) => (
                <div>
                    <Button className='button_remove' buttonOnClick={() => {deleteTask(note.id)}}/>
                    <span className="task_finished_date">{formatDateTime(note.finishingTime)}</span>
                    <span className="task_finished_task">{note.text}</span>
                </div>
            ))}
            </div>
        </div>
    );
}

/*
 * Ugly dependencyless way to format date string to another date string.
 */
function formatDateTime(dateTime?: string | null): string {
    if (dateTime) {
        const date = new Date(dateTime)
        return pad(date.getDate()) + "." 
                + pad(date.getMonth()) + "." 
                + pad(date.getFullYear()) + " " 
                + pad(date.getHours()) + ":" 
                + pad(date.getMinutes()) + ":" 
                + pad(date.getSeconds())
    }
    return "Unknown"
}

/*
 * Pad number to min 2 characters long.
 */
function pad(num: number): string {
    return num.toString().padStart(2, "0")
}

/**
 * 
 */
export class TodoList extends React.Component {
    render() {
        return (
            <div id="root">
            <h1>My TODO list</h1>
            <NewTodoItem />
            <TodoItemList />
            </div>
        );
    }
}
