package kz.hbscale.main.security;

import kz.hbscale.main.model.UserEntity;
import kz.hbscale.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

//    public UserDetailsServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    //    @Bean
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername : " + username);
        UserEntity entity = userRepository.findByUsername(username);
        System.out.println("entity {}" + entity);
        System.out.println("entity {}" + entity.getUsername());
        User u = new User(entity.getUsername(), entity.getPassword(), new ArrayList<>());
//        User u = new User("", "", new ArrayList<>());
        return u;
    }
}
