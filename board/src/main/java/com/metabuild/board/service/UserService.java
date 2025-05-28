package com.metabuild.board.service;

import com.metabuild.board.dto.UserDTO;

public interface UserService {

    void registerUser(UserDTO user);

    UserDTO login(String id, String password);

    boolean isDuplicatedId(String id);

    UserDTO getUserInfo(String id);

    void updatePassword(String id, String beforePassword, String afterPassword);

    void deleteId(String id, String password);
}
