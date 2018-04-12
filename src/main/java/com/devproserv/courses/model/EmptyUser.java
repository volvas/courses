package com.devproserv.courses.model;

import javax.servlet.http.HttpServletRequest;

public class EmptyUser extends User {
    @Override
    public boolean exists() {
        return false; // TODO
    }

    @Override
    public void loadFields() {
// TODO
    }

    @Override
    public void prepareJspData(HttpServletRequest request) {
// TODO
    }
}
