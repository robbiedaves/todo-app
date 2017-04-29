package com.robbiedaves;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TodoService {

    List<Todo> todoList = TodoList.getInstance();

    public List<Todo> getAllTodos() {
       return todoList;
    }
    
    public long addTodo(Todo todo) {
        todoList.add(todo);
        return todo.getId();
    }
    
    public boolean updateTodo(Todo todo) {
        int matchIdx = 0;
        Optional<Todo> match = todoList.stream()
                .filter(t -> t.getId() == todo.getId())
                .findFirst();
        if (match.isPresent()) {
            matchIdx = todoList.indexOf(match.get());
            todoList.set(matchIdx, todo);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean deleteTodo(long id) {
        Predicate<Todo> todo = e -> e.getId() == id;
        if (todoList.removeIf(todo)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Todo> searchTodos(String text) {
        Comparator<Todo> groupByComparator = Comparator.comparing(Todo::getTitle)
                .thenComparing(Todo::getDescription);
        List<Todo> result = todoList
                .stream()
                .filter(e -> e.getTitle().toUpperCase().contains(text.toUpperCase()) ||
                             e.getDescription().toUpperCase().contains(text.toUpperCase()))
                .sorted(groupByComparator)
                .collect(Collectors.toList());
        return result;
    }

    public Todo getTodo(long id) throws Exception {
        Optional<Todo> match
                = todoList.stream()
                 .filter(e -> e.getId() == id)
                .findFirst();
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new Exception("The Todo id" + id + " not found");
        }
    }

}
