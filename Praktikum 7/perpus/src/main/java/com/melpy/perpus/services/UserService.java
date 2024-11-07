package com.melpy.perpus.services;

import com.melpy.perpus.dto.UserDto;

public interface UserService {
    public UserDto createUser(UserDto user);

    public UserDto getUserByEmail(String email);
}
