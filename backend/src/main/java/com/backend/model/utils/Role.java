package com.backend.model.utils;

enum AllUsers {
    ADMIN, USER;
}

public interface Role {
    void setAllUsers(AllUsers allUsers);
    AllUsers getAllUsers();
}

class UserImpl implements Role {
    private AllUsers allUsers;

    @Override
    public AllUsers getAllUsers() {
        return allUsers;
    }

    @Override
    public void setAllUsers(AllUsers allUsers) {
        this.allUsers = allUsers;
    }
}
