package com.metabuild.board.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserDeleteId {
    @NonNull
    private String id;
    @NonNull
    private String password;
}
