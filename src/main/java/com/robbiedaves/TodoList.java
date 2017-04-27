package com.robbiedaves;

import java.util.ArrayList;
import java.util.List;

public class TodoList {

    private static final List<Todo> todoList = new ArrayList<>();

    private TodoList(){

    }

    static {
        todoList.add(new Todo("Get Passport", "Get passport for holidays", "Robin Davies"));
        todoList.add(new Todo("Buy tshirt", "Get cloths for holiday", "Robin Davies"));
        todoList.add(new Todo("Book Train Tickets", "book tickets for London on the 8th Dec", "Robin Davies"));
        todoList.add(new Todo("Phone home", "Phone parents to check they are ok", "Robin Davies"));
    }

    public static List<Todo> getInstance(){
        return todoList;
    }




}
