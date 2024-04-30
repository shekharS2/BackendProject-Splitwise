package dev.shekhar.SplitWise.service;

import dev.shekhar.SplitWise.entity.User;

public interface UserService {
    User signUp(String name, String email, String password);
    User logIn(String email, String password);
}
