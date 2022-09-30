package kz.hbscale.main.service;

import kz.hbscale.main.controller.DictionaryController;
import kz.hbscale.main.dto.UserDto;
import kz.hbscale.main.model.UserEntity;
import kz.hbscale.main.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private Logger logger
            = LogManager.getLogger(UserService.class);

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDto createUser(UserDto u) {

        UserEntity user = new UserEntity();
        user.setPassword(bCryptPasswordEncoder.encode(u.password));
        user.setUsername(u.username);
        user.setTelegram(u.telegram);

        userRepository.save(user);
        u.id = user.id;
        return u;
    }
}
