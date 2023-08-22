package com.assignment.TodoAppSpringBoot.models;



import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // this is the primary key which will be auto generated
    private Long id;
    private String task;
    private boolean completed;

//    public Task(String task, boolean completed) {
//        this.task = task;
//        this.completed = completed;
//    }
//    public Task(Long id, String task, boolean completed) {
//        this.id = id ;
//        this.task = task;
//        this.completed = completed;
//    }
//    public Task() {
//
//    }

//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public String getTask() {
//        return task;
//    }
//    public void setTask(String task) {
//        this.task = task;
//    }
//    public boolean isCompleted() {
//        return completed;
//    }
//    public void setCompleted(boolean completed) {
//        this.completed = completed;
//    }
}
