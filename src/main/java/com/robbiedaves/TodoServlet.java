package com.robbiedaves;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(
        name = "TodoServlet",
        urlPatterns = {"/todos"}
)
public class TodoServlet extends HttpServlet {

    TodoService todoService = new TodoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("searchAction");
        if (action != null) {
            switch (action) {
                case "searchById":
                    searchTodosByID(req, resp);
                    break;
                case "searchByText":
                    searchTodosByText(req, resp);
                    break;
            }
        } else {
            List<Todo> result = todoService.getAllTodos();
            forwardListTodos(req, resp, result);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("En el doPost");
        String action = req.getParameter("action");
        switch (action) {
            case "add":
                addTodoAction(req, resp);
                break;
            case "edit":
                editTodoAction(req, resp);
                break;
            case "remove":
                removeTodoAction(req, resp);
                break;
        }
    }

    private void searchTodosByID(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
        long idTodo = Integer.valueOf(req.getParameter("idTodo"));
        Todo todo = null;
        try {
            todo = todoService.getTodo(idTodo);
        } catch (Exception ex) {
            Logger.getLogger(TodoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.setAttribute("todo", todo);
        req.setAttribute("action", "edit");
        String nextJSP = "/jsp/new-todo.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }

    private void searchTodosByText(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String todoText = req.getParameter("todoText");
        List<Todo> result = todoService.searchTodos(todoText);
        forwardListTodos(req, resp, result);
    }

    private void forwardListTodos(HttpServletRequest req, HttpServletResponse resp, List todoList)
        throws ServletException, IOException {
        String nextJSP = "/jsp/list-todos.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("todoList", todoList);
        dispatcher.forward(req, resp);
    }
    
    private void addTodoAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String raisedBy = req.getParameter("raisedBy");
        String completed = req.getParameter("completed");
        Todo todo = new Todo(title, description, raisedBy);
        long idTodo = todoService.addTodo(todo);
        List<Todo> todoList = todoService.getAllTodos();
        req.setAttribute("idTodo", idTodo);
        String message = "The new Todo has been successfully created";
        req.setAttribute("message", message);
        forwardListTodos(req, resp, todoList);
    }
    
    private void editTodoAction(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String raisedBy = req.getParameter("raisedBy");
        String completed = req.getParameter("completed");
        long idTodo = Integer.valueOf(req.getParameter("idTodo"));
        Todo todo = new Todo(title, description, raisedBy, idTodo);
        todo.setId(idTodo);
        boolean success = todoService.updateTodo(todo);
        String message = null;
        if (success) {
            message = "The Todo has been successfully updated";
        }
        List<Todo> todoList = todoService.getAllTodos();
        req.setAttribute("idTodo", idTodo);
        req.setAttribute("message", message);
        forwardListTodos(req, resp, todoList);
    }
    
    private void removeTodoAction(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        long idTodo = Integer.valueOf(req.getParameter("idTodo"));
		System.out.println("ROBXX Delete:   " + idTodo);
        boolean confirm = todoService.deleteTodo(idTodo);
        if (confirm) {
            String message = "The Todo has successfully been removed";
            req.setAttribute("message", message);
        } 
        List<Todo> todoList = todoService.getAllTodos();
        forwardListTodos(req, resp, todoList);
    }

}