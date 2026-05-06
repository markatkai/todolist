import React, { useEffect, useState } from 'react';
import axios from "axios";

import type { TodoItem } from "../types/TodoItem"
import { Button } from './Button';

/**
 * 
 */
function TodoAddButton() {
    const [text, setText] = useState("");
    const [isOpen, setIsOpen] = useState(false);


    const togglePopup = () => {
        console.log("BUTTON WAS PRESSED")
        setIsOpen(true)
    }
    const hidePopup = () => {
        console.log("BUTTON2 WAS PRESSED")
        setIsOpen(false)
    }

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
        <div>
            <input onChange={printText} />
            <Button title="ADD" buttonOnClick={createTodo} />
            {!isOpen && <Button title="Add todo-item" buttonOnClick={togglePopup}/>}
            {isOpen && <Button title="Close" buttonOnClick={hidePopup}/>}
        </div>
    );
}


/**
 * 
 * @returns 
 */
function TodoListList() {
    const [notes, setNotes] = useState<TodoItem[]>([]);

    useEffect(() => {
        axios.get<TodoItem[]>("/api/notes").then(res => setNotes(res.data));
    }, []);

    return (
        <div>
            {notes.map((note, i) => (
                <h1 key={i}>{note.text}</h1>
            ))}
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
            <TodoAddButton />
            <TodoListList />
            </div>
        );
    }
}
