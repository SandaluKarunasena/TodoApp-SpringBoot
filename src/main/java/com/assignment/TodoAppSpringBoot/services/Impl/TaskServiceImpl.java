package com.assignment.TodoAppSpringBoot.services.Impl;

import com.assignment.TodoAppSpringBoot.models.Task;
import com.assignment.TodoAppSpringBoot.repositories.TaskRepository;
import com.assignment.TodoAppSpringBoot.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Task createNewTask(Task task) {
        return modelMapper.map(taskRepository.save(modelMapper.map(task, Task.class)), Task.class);
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findAllCompletedTask() {
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> findAllInCompleteTask() {
        return taskRepository.findByCompletedFalse();
    }

    public String deleteTask(Long id) {

        if (taskRepository.findById(id).isEmpty()){
            return "ID not Found";
        }
        taskRepository.deleteById(id);
        return "Delete Successful";
    }

    public Task updateTask(Task task) {
        return modelMapper.map(taskRepository.save(modelMapper.map(task, Task.class)), Task.class);
    }
}
