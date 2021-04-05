package com.backend.demo.utils;

import com.backend.demo.dto.Session;
import com.backend.demo.service.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionUtil {
    public static Session getCurrentUser() {
        return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSession();
    }
}
