package com.exercise.accountingNotebook.service;

import com.exercise.accountingNotebook.model.User;

import java.util.Optional;

public interface UserService {
    /**
     * Given a userId,
     * will be retrieved an User.
     *
     * @param userId
     * @return User
     */
    Optional<User> getUser(Long userId);
    /**
     * Given a user,
     * will be save a new User.
     *
     * @param user
     * @return Transaction
     */
    User saveUser(User user);
    /**
     * Given a userId & user,
     * will be retrieved a modifying User.
     *
     * @param userId, user
     * @return User
     */
    User updateUser(Long userId, User user);
}
