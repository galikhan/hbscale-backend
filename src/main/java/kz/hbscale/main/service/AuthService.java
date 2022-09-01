package kz.hbscale.main.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class AuthService {

    @GetMapping("/data")
    public List<String> getData() {
        return List.of("element1 ", "element0 ", "element2 ");
    }

    @PostMapping("/auth")
    public List<String> authenticate() {
        System.out.println("post data");
        return List.of("element1 ", "element0 ", "element2 ");
    }

}
