package com.example.telegrambotlib.easybot.configuration;

import com.example.telegrambotlib.easybot.annotation.HandleMessageExecutor;
import com.example.telegrambotlib.easybot.annotation.HandleUndefinedExecutor;
import com.example.telegrambotlib.easybot.annotation.HandleUserStepExecutor;
import com.example.telegrambotlib.entity.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SmartBotConfiguration {
    private final HandleMessageExecutor handleMessageExecutor;
    private final HandleUserStepExecutor handleUserStepExecutor;
    private final HandleUndefinedExecutor handleUndefinedExecutor;
    // Add other handlers as needed

    public SmartBotConfiguration(Object botInstance) {
        handleMessageExecutor = new HandleMessageExecutor(botInstance);
        handleUserStepExecutor = new HandleUserStepExecutor(botInstance);
        handleUndefinedExecutor = new HandleUndefinedExecutor(botInstance);
    }

    public void handleUpdate(User user, Update update) {
        boolean handle = handleMessageExecutor.handle(update, user);
        if (!handle) {
            Boolean aBoolean = handleUserStepExecutor.handleUserStep(user, update);
            if (!aBoolean) {
                handleUndefinedExecutor.handle(update, user);
            }
        }
    }
}
