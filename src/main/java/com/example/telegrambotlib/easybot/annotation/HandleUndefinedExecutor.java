package com.example.telegrambotlib.easybot.annotation;

import com.example.telegrambotlib.easybot.BotControllerScanner;
import com.example.telegrambotlib.entity.User;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleUndefinedExecutor {
    private final Map<String, Method> messageHandlers = new HashMap<>();
    private final List<Class<?>> target;


    public HandleUndefinedExecutor(String basePackage) {
        this.target = BotControllerScanner.botControllerScanner(basePackage);
        registerMessageHandlers();
    }

    public void handleUndefined(Update update, User user) {
        Method handler = messageHandlers.get("key");
        if (handler != null) {
            try {
                handler.invoke(target, update, user);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerMessageHandlers() {
        for (Class<?> annotatedClass : target) {
            for (Method method : annotatedClass.getMethods()) {
                if (method.isAnnotationPresent(HandleUndefined.class) && method.getParameterCount() == 2) {
                    messageHandlers.put("key", method);
                }
            }
        }
    }
}


