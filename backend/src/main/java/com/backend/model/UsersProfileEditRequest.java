package com.backend.model;

import lombok.Data;
import lombok.Getter;

@Data
public class UsersProfileEditRequest {
    @Getter
    String bio;
}
