package com.robbiedaves;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TodoService {

    List<Todo> todoList = TodoList.getInstance();

    public List<Todo> getAllTodos() {
       return todoList;
    }

    public List<Todo> searchTodos(String text) {
        Comparator<Todo> groupByComparator = Comparator.comparing(Todo::getTitle)
                .thenComparing(Todo::getDescription);
        List<Todo> result = todoList
                .stream()
                .filter(e -> e.getTitle().compareToIgnoreCase(text) == 0|| e.getDescription().compareToIgnoreCase(text) == 0)
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
