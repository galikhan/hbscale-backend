package kz.hbscale.main.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class Configs /*implements AuthenticationManager*/{

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Bean
//    protected PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        final UserDetails userDetail = customUserDetailsService.loadUserByUsername(authentication.getName());
//        if (!passwordEncoder().matches(authentication.getCredentials().toString(), userDetail.getPassword())) {
//            throw new BadCredentialsException("Wrong password");
//        }
//        return new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities());
//    }

}
