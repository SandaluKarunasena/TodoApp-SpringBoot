package com.assignment.TodoAppSpringBoot.UnitTest;

//import jdk.internal.jshell.tool.ConsoleIOContext;

import com.assignment.TodoAppSpringBoot.controllers.TaskController;
import com.assignment.TodoAppSpringBoot.models.Task;
import com.assignment.TodoAppSpringBoot.repositories.TaskRepository;
import com.assignment.TodoAppSpringBoot.services.Impl.TaskServiceImpl;
import com.assignment.TodoAppSpringBoot.services.Impl.TaskServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class TaskControllerUnitTest {

    @InjectMocks
    TaskController taskController;
    @Getter
    MockMvc mockMvc;
    @Mock
    ModelMapper modelMapper;
    ObjectMapper mapper;

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskServiceImpl taskService;

    Task TASK1 = new Task( 2L,"Testing task one", true);
    Task TASK2 = new Task(3L,"Testing task two", false);
    Task TASK3 = new Task(4L, "Complete To Do App is Done Now", true);

    @Test
    void getAllTasks() throws Exception {
        List<Task> tasks = new ArrayList<>(Arrays.asList(TASK1, TASK2, TASK3));

        when(taskService.getAllTask()).thenReturn(tasks);

        ResponseEntity<List<Task>> result = taskController.getAllTasks();
        assertThat(result.getBody().size()).isEqualTo(3);
        assertThat(result.getBody().get(2).getTask()).isEqualTo(TASK3.getTask());
        assertThat(result.getBody().get(1).getId()).isEqualTo(TASK2.getId());

    }

    @Test
    void getTaskById() {
        //List<Task> tasks = new ArrayList<>(Arrays.asList(TASK1, TASK2, TASK3));

        when(taskService.findTaskById(4L)).thenReturn(Optional.ofNullable(TASK3));

        ResponseEntity<Optional<Task>> result = taskController.getTaskById(4L);

        assertThat(result.getBody().get().equals(TASK3)).isEqualTo(true);

    }
    private List<Task> filterTasks(String str) {
        List<Task> tasks = new ArrayList<>(Arrays.asList(TASK1, TASK2, TASK3));
        List<Task> comlist = new ArrayList<>();
        List<Task> incomlist = new ArrayList<>();

        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task i = it.next();
            if (i.isCompleted()) {
                comlist.add(i);
            }else{
                incomlist.add(i);
            }
        }
        System.out.println(comlist);
        if(str == "Completed") {
            return comlist;
        }
        if(str == "InCompleted"){
            return incomlist;
        }
        return null;
    }

    @Test
    void getAllCompletedTasks() {
        List<Task> tasks = new ArrayList<>(Arrays.asList(TASK1, TASK2, TASK3));

        when(taskService.findAllCompletedTask()).thenReturn(filterTasks("Completed"));

        ResponseEntity<List<Task>> result = taskController.getAllCompletedTasks();
        assertThat(result.getBody().size()).isEqualTo(2);
        assertThat(result.getBody().get(1).getTask()).isEqualTo(TASK3.getTask());
    }

    @Test
    void getAllIncompleteTasks() {
        List<Task> tasks = new ArrayList<>(Arrays.asList(TASK1, TASK2, TASK3));

        when(taskService.findAllInCompleteTask()).thenReturn(filterTasks("InCompleted"));

        ResponseEntity<List<Task>> result = taskController.getAllIncompleteTasks();
        assertThat(result.getBody().size()).isEqualTo(1);
        assertThat(result.getBody().get(0).getTask()).isEqualTo(TASK2.getTask());
    }

    @Test
    void createTask() {

        List<Task> tasks = new ArrayList<>(Arrays.asList(TASK1, TASK2, TASK3));

        when(taskService.createNewTask(TASK1)).thenReturn(TASK1);

        ResponseEntity<Task> result = taskController.createTask(TASK1);
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getBody().getTask()).isEqualTo(TASK1.getTask());
        assertThat(result.getBody().getClass()).isEqualTo(TASK1.getClass());

    }

    @Test
    void updateTask() {


        List<Task> tasks = new ArrayList<>(Arrays.asList(TASK1, TASK2, TASK3));

        when(taskService.updateTask(TASK2)).thenReturn(TASK2);

        ResponseEntity<Task> result = taskController.updateTask(2L, TASK2);
        assertThat(result.getBody().getTask()).isEqualTo(TASK3.getTask());
        assertThat(result.getBody().getId()).isEqualTo(TASK2.getId());
    }

    @Test
    void testDeleteTask() {
        List<Task> tasks = new ArrayList<>(Arrays.asList(TASK1, TASK2, TASK3));

        when(taskService.deleteTask(2L)).thenReturn("Delete Successful");

        ResponseEntity<String> result = taskController.deleteTask(2L);
        //assertThat(result.getBody().size()).isEqualTo(3);
        assertThat(result.getBody()).isEqualTo("Delete Successful");
        assertThat(result.getStatusCode().value()).isEqualTo(200);
    }
}