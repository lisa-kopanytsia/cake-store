package com.store.service;

import com.store.domain.User;
import com.store.domain.security.PasswordResetToken;
import com.store.domain.security.UserRole;

import java.time.LocalDate;
import java.util.Set;

public interface UserService {
    PasswordResetToken getPasswordResetToken(final String token);
    void createPasswordResetTokenForUser(final User user, final String token);
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(Long id);
    User createUser(User user, Set<UserRole> userRoles) throws Exception;
    User save(User user);
}
