package hezix.org.shaudifydemo1.service;

import hezix.org.shaudifydemo1.entity.user.CustomUserDetail;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.ReadUserDTO;
import hezix.org.shaudifydemo1.exception.EntityMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserEntityByUsername(username);
        return Optional.ofNullable(user).map(CustomUserDetail::new)
                .orElseThrow(() -> new EntityMappingException("dont cant map %s to customUserDetail".formatted(username)));
    }
}
