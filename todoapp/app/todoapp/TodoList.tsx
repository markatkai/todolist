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
    const createTodo = () => {
        console.log("CREATE BUTTON WAS PRESSED " + text)
        if (text) {
            console.log("And it is ok to try to send it")
            // Create a new todo item
            axios.post<TodoItem>("/api/notes", {
                text: text
            }).then((response) => {
                console.log(response);
                setText("")
                window.location.reload() // TODO Lazy way to do this...
            });
        }
    }

    return (
        <div id='todo_new_item'>
            <input onChange={printText} />
            <Button title="ADD" buttonOnClick={createTodo} />
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
        console.log("TASK " + id + " IS DONE!")
        axios.put<TodoItem>("/api/notes/" + id, {
                status: "FINISHED"
            }).then((response) => {
                console.log(response);
                window.location.reload() // TODO Lazy way to do this...
            });
    }
    const deleteTask = (id: number) => {
        console.log("TASK " + id + " WILL BE DELETED!")
        axios.delete<TodoItem>("/api/notes/" + id).then((response) => {
                console.log(response);
                window.location.reload() // TODO Lazy way to do this...
            });
    }

    return (
        <div>
            <div>
            <h1 id="header_tasks_unfinished">TODO:</h1>
            {notes.map((note, i) => (
                <div className="tasklist" id="todo_tasks_unfinished">
                    <Button title="O" buttonOnClick={() => {markAsDone(note.id)}}/>
                    <Button title="X" buttonOnClick={() => {deleteTask(note.id)}}/>
                    {note.text}
                </div>
            ))}
            </div>
            <div className="tasklist" id="todo_tasks_finished">
            <h1 id="header_tasks_finished">Finished tasks:</h1>
            {doneNotes.map((note, i) => (
                <div>
                    <Button title="X" buttonOnClick={() => {deleteTask(note.id)}}/>
                    {note.text}
                </div>
            ))}
            </div>
        </div>
    );
}


/**
 * 
 */
export class TodoList extends React.Component {
    render() {
        return (
            <div className="transaction-graph">
            <h1>My TODO items</h1>
            <NewTodoItem />
            <TodoItemList />
            </div>
        );
    }
}
