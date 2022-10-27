package kz.hbscale.main.controller;

import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.dto.UserTaskDto;
import kz.hbscale.main.service.UserTaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserTaskController {

    private UserTaskService userTaskService;

    public UserTaskController(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }

    @GetMapping("/task/results")
    public List<ResultDto> getResults() {
        return this.userTaskService.getResults();
    }

    @GetMapping("/task")
    public List<UserTaskDto> myTasks() {
        return this.userTaskService.myTasks();
    }

    @PostMapping("/task")
    public UserTaskDto save(@RequestBody UserTaskDto dto) {
        return this.userTaskService.save(dto);
    }


}
