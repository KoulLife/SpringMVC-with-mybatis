package com.metabuild.board.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class UserUpdatePasswordRequest {
    @NonNull
    private String beforePassword;
    @NonNull
    private String afterPassword;
}
