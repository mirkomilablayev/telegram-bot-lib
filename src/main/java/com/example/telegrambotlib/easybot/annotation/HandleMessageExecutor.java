package com.example.telegrambotlib.easybot.annotation;

import com.example.telegrambotlib.easybot.BotControllerScanner;
import com.example.telegrambotlib.entity.User;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleMessageExecutor {
    private final Map<String, Method> messageHandlers = new HashMap<>();
    private final List<Class<?>> target;

    public HandleMessageExecutor(String basePackage) {
        this.target = BotControllerScanner.botControllerScanner(basePackage);
        registerMessageHandlers();
    }

    public boolean handle(Update update, User user) {
        String messageText = null;
        if (update.hasMessage()) {
            messageText = update.getMessage().getText();
        }
        Method handler = messageHandlers.get(messageText);
        if (handler != null) {
            try {
                handler.invoke(target, update, user);
                return true;
            } catch (IllegalAccessException | InvocationTargetException e) {
                return false;
            }
        }
        return false;
    }

    private void registerMessageHandlers() {
        for (Class<?> annotatedClass : target) {
            for (Method method : annotatedClass.getMethods()) {
                if (method.isAnnotationPresent(HandleMessage.class) && method.getParameterCount() == 2) {
                    HandleMessage annotation = method.getAnnotation(HandleMessage.class);
                    messageHandlers.put(annotation.value(), method);
                }
            }
        }

    }
}
