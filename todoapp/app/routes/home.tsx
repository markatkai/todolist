import type { Route } from "./+types/home";
import { TodoList } from "../todoapp/TodoList";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "TODO App" },
    { name: "description", content: "Application for tracking TODO items" },
  ];
}

export default function Home() {
  return <TodoList />;
}
