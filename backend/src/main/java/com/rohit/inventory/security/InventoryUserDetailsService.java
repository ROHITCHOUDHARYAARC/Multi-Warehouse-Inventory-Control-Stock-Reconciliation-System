package com.rohit.inventory.security;

import com.rohit.inventory.entity.User; import com.rohit.inventory.entity.UserStatus; import com.rohit.inventory.repository.UserRepository;
import java.util.HashSet; import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.userdetails.UserDetails; import org.springframework.security.core.userdetails.UserDetailsService; import org.springframework.security.core.userdetails.UsernameNotFoundException; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryUserDetailsService implements UserDetailsService {
    private final UserRepository users;
    public InventoryUserDetailsService(UserRepository users) { this.users = users; }
    @Override @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { return toUserDetails(users.findByEmail(email.toLowerCase()).orElseThrow(() -> new UsernameNotFoundException("User not found"))); }
    public UserDetails toUserDetails(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> { authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode())); role.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getCode()))); });
        boolean active = user.getStatus() == UserStatus.ACTIVE;
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPasswordHash()).authorities(authorities).disabled(!active).accountLocked(user.getStatus() == UserStatus.LOCKED).build();
    }
}
