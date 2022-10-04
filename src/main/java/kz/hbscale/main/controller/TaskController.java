package kz.hbscale.main.controller;

import kz.hbscale.main.dto.TaskDto;
import kz.hbscale.main.security.facade.AuthenticationFacade;
import kz.hbscale.main.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class TaskController {

    private TaskService taskService;
    private AuthenticationFacade authenticationFacade;


    public TaskController(TaskService taskService, AuthenticationFacade authenticationFacade) {
        this.taskService = taskService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/task")
    public List<TaskDto> myTasks() {
        return this.taskService.myTasks();
    }


    @PostMapping("/task")
    public TaskDto save(@RequestBody TaskDto dto) {
        System.out.println("getPrincipal:" + authenticationFacade.getAuthentication().getPrincipal());
        return taskService.save(dto);
    }


}
