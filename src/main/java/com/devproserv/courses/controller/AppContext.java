package com.devproserv.courses.controller;

import static com.devproserv.courses.config.MainConfig.COMMAND_SIGNUP;
import static com.devproserv.courses.config.MainConfig.COMMAND_LOGIN;
import static com.devproserv.courses.config.MainConfig.COMMAND_LOGOUT;
import static com.devproserv.courses.config.MainConfig.COMMAND_SUBSCRIBE;
import static com.devproserv.courses.config.MainConfig.COMMAND_UNSUBSCRIBE;

import java.util.HashMap;
import java.util.Map;

import com.devproserv.courses.command.Command;
import com.devproserv.courses.command.LoginCommand;
import com.devproserv.courses.command.LogoutCommand;
import com.devproserv.courses.command.SignUpCommand;
import com.devproserv.courses.command.SubscribeCommand;
import com.devproserv.courses.command.UnsubscribeCommand;

public class AppContext {
    
    /* represents one instance of the Application Context (Singleton) */
    private static AppContext appContext = new AppContext();
    
    private Map<String, Command> commandMap = new HashMap<>();
    
    
    private AppContext() {
        commandMap.put(COMMAND_SIGNUP, new SignUpCommand());
        commandMap.put(COMMAND_LOGIN, new LoginCommand());
        commandMap.put(COMMAND_LOGOUT, new LogoutCommand());
        commandMap.put(COMMAND_SUBSCRIBE, new SubscribeCommand());
        commandMap.put(COMMAND_UNSUBSCRIBE, new UnsubscribeCommand());
    }
    
    public static AppContext getAppContext() {
        return appContext;
    }

    public void initBeans() {
        
        
        
    }

    public Command getCommand(String key) {
        return commandMap.get(key);
    }

}
