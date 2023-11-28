package com.example.telegrambotlib.easybot;

import com.example.telegrambotlib.easybot.annotation.HandleMessageExecutor;
import com.example.telegrambotlib.easybot.annotation.HandleUndefinedExecutor;
import com.example.telegrambotlib.easybot.annotation.HandleUserStepExecutor;
import com.example.telegrambotlib.entity.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SmartBotConfiguration {
    private final HandleMessageExecutor handleMessageExecutor;
    private final HandleUserStepExecutor handleUserStepExecutor;
    private final HandleUndefinedExecutor handleUndefinedExecutor;

    public SmartBotConfiguration(String basePackage) {
        handleMessageExecutor = new HandleMessageExecutor(basePackage);
        handleUserStepExecutor = new HandleUserStepExecutor(basePackage);
        handleUndefinedExecutor = new HandleUndefinedExecutor(basePackage);
    }

    public void handleUpdate(User user, Update update) {
        boolean handle = handleMessageExecutor.handle(update, user);
        if (!handle) {
            Boolean aBoolean = handleUserStepExecutor.handleUserStep(user, update);
            if (!aBoolean) {
                handleUndefinedExecutor.handleUndefined(update, user);
            }
        }
    }
}
