package kz.hbscale.main.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
@RequestMapping(value = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

//    @PostMapping("/auth")
//    public boolean auth(@RequestBody UsernameAndPasswordDto dto) {
//        return false;
//    }
//

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World";
    }
}
