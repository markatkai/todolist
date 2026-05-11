import React, { useEffect, useState } from "react";
import axios from "axios";

import type { TodoItem } from "../types/TodoItem";
import { Button } from "./Button";

/**
 * Field and button for adding new todo-items to the list.
 */
function NewTodoItem({ onAdded }: { onAdded: (item: TodoItem) => void }) {
  const [text, setText] = useState("");
  const [error, setError] = useState("");

  const printText = (event: React.ChangeEvent<HTMLInputElement>) => {
    setText(event.currentTarget.value);
  };

  const sendPostRequest = (textToSend: string) => {
    if (textToSend) {
      axios
        .post<TodoItem>("/api/notes", {
          text: textToSend,
        })
        .then((response) => {
          setText("");
          setError("");
          onAdded(response.data);
        })
        .catch((err) => {
          console.log(`Error on create task: ${err}`);
          setError("Failed to create a task!");
        });
    }
  };

  /**
   * Catch Enter pressing in the input.
   */
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      sendPostRequest(text);
    }
  };

  return (
    <div id="todo_new_item">
      <input
        value={text}
        onChange={printText}
        onKeyDown={handleKeyDown}
        maxLength={120}
      />
      <Button title="Add" buttonOnClick={() => sendPostRequest(text)} />
      {error && <div className="error">{error}</div>}
    </div>
  );
}

/*
 * Type of the TaskItemList expected properties
 */
type GenericTodoItemListProps = {
  name: string;
  notes: TodoItem[];
  toRowText: (item: TodoItem) => React.ReactNode;
  error: string;
  setError: (error: string) => void;
  clearErrors: () => void;
  onDelete: (id: number) => void;
  onMarkFinished?: (item: TodoItem) => void;
};

/**
 * List of currently existing todo-items.
 */
function TaskItemList({
  name,
  notes,
  toRowText,
  error,
  setError,
  clearErrors,
  onDelete,
  onMarkFinished,
}: GenericTodoItemListProps) {
  const markAsDone = (id: number) => {
    if (onMarkFinished) {
      axios
        .put<TodoItem>("/api/notes/" + id, {
          status: "FINISHED",
        })
        .then((response) => {
          onMarkFinished(response.data);
          clearErrors();
        })
        .catch((err) => {
          console.log(`Error on mark task finished: ${err}`);
          setError("Failed to mark task finished!");
        });
    }
  };
  const deleteTask = (id: number) => {
    axios
      .delete<TodoItem>("/api/notes/" + id)
      .then(() => {
        onDelete(id);
        clearErrors();
      })
      .catch((err) => {
        console.log(`Error on delete task: ${err}`);
        setError("Failed to delete a task!");
      });
  };

  return (
    <div className="tasklist" id="todo_tasks_unfinished">
      <h1 id="header_tasks_unfinished">{name}:</h1>
      {error && <div className="error">{error}</div>}
      {notes.map((note) => (
        <div className="todo_list_row" key={note.id}>
          <div className="todo_task_buttons">
            {onMarkFinished && (
              <Button
                className="button_mark_done"
                buttonOnClick={() => {
                  markAsDone(note.id);
                }}
              />
            )}
            <Button
              className="button_remove"
              buttonOnClick={() => {
                deleteTask(note.id);
              }}
            />
          </div>
          <div className="todo_task">{toRowText(note)}</div>
        </div>
      ))}
    </div>
  );
}

/*
 * Simple dependencyless way to format date string to another date string.
 */
function formatDateTime(dateTime?: string | null): string {
  if (dateTime) {
    const date = new Date(dateTime);
    return (
      pad(date.getDate()) +
      "." +
      pad(date.getMonth() + 1) +
      "." + // Month starts from 0
      pad(date.getFullYear()) +
      " " +
      pad(date.getHours()) +
      ":" +
      pad(date.getMinutes()) +
      ":" +
      pad(date.getSeconds())
    );
  }
  return "Unknown";
}

/*
 * Pad number to min 2 characters long.
 */
function pad(num: number): string {
  return num.toString().padStart(2, "0");
}

/**
 * Base for the view
 */
export function TodoList() {
  const [notes, setNotes] = useState<TodoItem[]>([]);
  const [finishedNotes, setFinishedNotes] = useState<TodoItem[]>([]);
  const [notesError, setNotesError] = useState<string>("");
  const [finishedNotesError, setFinishedNotesError] = useState<string>("");

  const fetchFinished = () => {
    // Finished notes order: most recently finished first
    axios
      .get<TodoItem[]>(
        "/api/notes?status=FINISHED&orderBy=finishingTime&order=DESC",
      )
      .then((res) => setFinishedNotes(res.data))
      .catch((err) => {
        console.error(`Failed to load finished notes: ${err}`);
        setFinishedNotesError("Failed to update finished notes!");
      });
  };

  useEffect(() => {
    // Unfinished notes order: oldest first
    axios
      .get<TodoItem[]>(
        "/api/notes?status=UNFINISHED&orderBy=createTime&order=ASC",
      )
      .then((res) => setNotes(res.data))
      .catch((err) => {
        console.error(`Failed to load unfinished notes: ${err}`);
        setNotesError("Failed to update notes!");
      });
    fetchFinished();
  }, []);

  const handleAdded = (item: TodoItem) => setNotes((prev) => [...prev, item]);

  const handleMarkFinished = (item: TodoItem) => {
    setNotes((prev) => prev.filter((n) => n.id !== item.id));
    // Fetch unfinished notes again...
    fetchFinished();
  };

  const handleUnfinishedDelete = (id: number) => {
    setNotes((prev) => prev.filter((n) => n.id !== id));
  };
  const handleFinishedDelete = (id: number) => {
    setFinishedNotes((prev) => prev.filter((n) => n.id !== id));
  };

  const clearErrors = () => {
    setNotesError("");
    setFinishedNotesError("");
  };

  return (
    <div id="root">
      <h1>TODO list</h1>
      <NewTodoItem onAdded={handleAdded} />
      <TaskItemList
        name="TODO"
        notes={notes}
        toRowText={(note) => note.text}
        error={notesError}
        setError={setNotesError}
        clearErrors={clearErrors}
        onDelete={handleUnfinishedDelete}
        onMarkFinished={handleMarkFinished}
      />
      <TaskItemList
        name="Finished tasks"
        notes={finishedNotes}
        toRowText={(note) => (
          <>
            {note.finishingTime && (
              <>
                Finished at: {formatDateTime(note.finishingTime)}
                <br />
              </>
            )}
            {note.text}
          </>
        )}
        error={finishedNotesError}
        setError={setFinishedNotesError}
        clearErrors={clearErrors}
        onDelete={handleFinishedDelete}
      />
    </div>
  );
}
