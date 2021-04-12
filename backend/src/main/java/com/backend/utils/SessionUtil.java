package com.backend.utils;

import com.backend.config.service.UserPrincipal;
import com.backend.dto.Session;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionUtil {
    public static Session getCurrentUser() {
        return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSession();
    }
}
