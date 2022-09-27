package kz.hbscale.main.controller;

import kz.hbscale.main.dto.TaskDto;
import kz.hbscale.main.security.facade.AuthenticationFacade;
import kz.hbscale.main.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private TaskService taskService;
    private AuthenticationFacade authenticationFacade;

    private Logger log = LogManager.getLogger(AuthController.class);

    public AuthController(TaskService taskService, AuthenticationFacade authenticationFacade) {
        this.taskService = taskService;
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping("/task")
    public TaskDto save(@RequestBody TaskDto dto) {
        System.out.println("getPrincipal:" + authenticationFacade.getAuthentication().getPrincipal());
        return taskService.save(dto);
    }


    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("getPrincipal:" + authenticationFacade.getAuthentication().getPrincipal());
        log.info("authenticationFacade.getAuthentication().getPrincipal()) {}", authenticationFacade.getAuthentication().getPrincipal());
        return "Hello World";

    }
}
