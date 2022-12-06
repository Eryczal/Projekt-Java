package pl.eryk.todo.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import javax.management.relation.Role;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User createUser(String username, String password) {
        if(getUser(username) != null) {
            throw new RuntimeException("User found!");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(null, username, encodedPassword, new ArrayList<>());

        return userRepository.save(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = getUser(username);

        if(u == null) throw new UsernameNotFoundException(username);

        Collection<GrantedAuthority> authorities = mapRolesToAuthorities(u.getRoles());

        var user = org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(u.getPassword())
                .authorities(authorities)
                .build();

        return user;
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<UserRole> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}