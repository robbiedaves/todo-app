package com.robbiedaves;

import java.util.concurrent.atomic.AtomicLong;

public class Todo {

    private long id;
    private String title;
    private String description;
    private String raisedBy;
    private String completed;
    private static final AtomicLong counter = new AtomicLong(100);


    public Todo(String title, String description, String raisedBy, long id) {
        this.title = title;
        this.description = description;
        this.raisedBy = raisedBy;
        this.completed = "false";
        this.id = id;
    }

    public Todo(String title, String description, String raisedBy) {
        this.title = title;
        this.description = description;
        this.raisedBy = raisedBy;
        this.completed = "false";
        this.id = counter.incrementAndGet();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(String raisedBy) {
        this.raisedBy = raisedBy;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public static AtomicLong getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "Todo{" + "id=" + id + ", title=" + title +
                ", description=" + description + ", raisedBy=" + raisedBy +
                ", completed=" + completed + '}';
    }
}
