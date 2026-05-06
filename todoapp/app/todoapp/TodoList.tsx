import React from 'react';

class TodoAddButton extends React.Component {
    render() {
        return (
            <h1>Add item...</h1>
        );
    }
}


class TodoListList extends React.Component {
    // Fetch all todo items
    render() {
        return (
            <div>
            <h1>Item 1</h1>
            <h1>Item 2</h1>
            </div>
        );
    }


    // Show them

}



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
