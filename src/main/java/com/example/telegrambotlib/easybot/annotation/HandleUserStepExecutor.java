package com.example.telegrambotlib.easybot.annotation;

import com.example.telegrambotlib.easybot.BotControllerScanner;
import com.example.telegrambotlib.entity.User;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleUserStepExecutor {
    private final Map<String, Method> userStepHandlers = new HashMap<>();
    private final List<Class<?>> target;

    public HandleUserStepExecutor(String basePackage) {
        this.target = BotControllerScanner.botControllerScanner(basePackage);
        registerUserStepHandlers();
    }

    public Boolean handleUserStep(User user, Update update) {
        Method handler = userStepHandlers.get(user.getStep());
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

    private void registerUserStepHandlers() {
        for (Class<?> annotatedClass : target) {
            for (Method method : annotatedClass.getMethods()) {
                if (method.isAnnotationPresent(HandleUserStep.class) && method.getParameterCount() == 2) {
                    HandleUserStep annotation = method.getAnnotation(HandleUserStep.class);
                    userStepHandlers.put(annotation.value(), method);
                }
            }
        }

    }
}
