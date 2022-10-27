package kz.hbscale.main.controller;

import kz.hbscale.main.security.facade.AuthenticationFacade;
import kz.hbscale.main.service.ConstructionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private ConstructionService taskService;
    private AuthenticationFacade authenticationFacade;

    private Logger log = LogManager.getLogger(AuthController.class);

    public AuthController(ConstructionService taskService, AuthenticationFacade authenticationFacade) {
        this.taskService = taskService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("getPrincipal:" + authenticationFacade.getAuthentication().getPrincipal());
        log.info("authenticationFacade.getAuthentication().getPrincipal()) {}", authenticationFacade.getAuthentication().getPrincipal());
        return "Hello World";

    }
}
