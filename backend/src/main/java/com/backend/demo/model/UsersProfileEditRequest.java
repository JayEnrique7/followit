package com.backend.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Data
public class UsersProfileEditRequest {
    @Getter
    String bio;
}
