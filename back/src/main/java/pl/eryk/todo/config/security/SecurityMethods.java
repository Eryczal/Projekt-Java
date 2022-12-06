package pl.eryk.todo.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.eryk.todo.user.User;
import pl.eryk.todo.user.UserService;

@Component
@RequiredArgsConstructor
public class SecurityMethods {

    private final UserService userService;

    private User getUser(Authentication authentication) {
        org.springframework.security.core.userdetails.User principalUser = (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        
        return userService.getUser(principalUser.getUsername());
    }
    
    public boolean canAccessWithId(Authentication authentication, Long id) {
        User user = getUser(authentication);

        return id.equals(user.getId());
    }
    
    public boolean canAccessWithUsername(Authentication authentication, String username) {
        User user = getUser(authentication);

        return username.equals(user.getUsername());
    }
}
