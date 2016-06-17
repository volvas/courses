package com.github.vovas11.courses.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    public String execute(HttpServletRequest request);
}
