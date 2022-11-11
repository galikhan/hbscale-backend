package kz.hbscale.main.controller;

import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.dto.UserTaskAndConstruction;
import kz.hbscale.main.dto.UserTaskDto;
import kz.hbscale.main.service.UserTaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private Logger logger = LogManager.getLogger(UserTaskService.class);
    public UserTaskController(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }

    @GetMapping("/task/results")
    public List<ResultDto> getResults() {
        return this.userTaskService.getCountTaskInPeriod(null, null);
    }

    @GetMapping("/task/count/last7days")
    public List<ResultDto> getLast7DaysCount() {
        return this.userTaskService.getLast7DaysCount();
    }

    @GetMapping("/task/count/monthly")
    public List<ResultDto> getMonthlyCount() {
        return this.userTaskService.getMonthlyCount();
    }

    @GetMapping("/task/last7days")
    public UserTaskAndConstruction getAllLast7DaysTasks() {
        return this.userTaskService.getAllLast7DaysTasks();
    }
    @GetMapping("/task/monthly")
    public UserTaskAndConstruction getAllMonthlyTasks() {
        return this.userTaskService.getAllMonthlyTasks();
    }

    @GetMapping("/task")
    public UserTaskAndConstruction myTasks() {
        logger.info("item {}", this.userTaskService.myTasks());
        return this.userTaskService.myTasks();
    }

    @PostMapping("/task")
    public UserTaskDto save(@RequestBody UserTaskDto dto) {
        return this.userTaskService.save(dto);
    }


}
