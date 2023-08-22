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
    private Long id;
    private String task;
    private boolean completed;

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
