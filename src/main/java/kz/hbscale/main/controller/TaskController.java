package kz.hbscale.main.controller;

import kz.hbscale.main.dto.TaskDto;
import kz.hbscale.main.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/task")
    public List<TaskDto> myTasks() {
        return this.taskService.myTasks();
    }

}
