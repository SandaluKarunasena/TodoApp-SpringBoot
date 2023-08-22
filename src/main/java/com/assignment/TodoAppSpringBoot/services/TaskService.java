package com.assignment.TodoAppSpringBoot.services;

import com.assignment.TodoAppSpringBoot.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createNewTask(Task task);

    List<Task> getAllTask();

    Optional<Task> findTaskById(Long id);

    List<Task> findAllCompletedTask();

    List<Task> findAllInCompleteTask();

    String deleteTask(Long id);

    Task updateTask(Task task);
}
