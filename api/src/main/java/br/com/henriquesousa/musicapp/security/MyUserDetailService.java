package br.com.henriquesousa.musicapp.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.repository.UserRepository;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userRes = userRepository.findByUserName(username);

        if (userRes.isEmpty()) {
            throw new UsernameNotFoundException("No user found with this username " + username);
        }

        User user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_USER")
                )
        );
    }
}
