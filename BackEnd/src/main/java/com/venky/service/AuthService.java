package com.venky.service;

import com.venky.dto.LoginDto;
import com.venky.entity.Librarian;

public interface AuthService {
    Librarian login(LoginDto loginDto);
}
