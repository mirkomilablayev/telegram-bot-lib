package com.example.telegrambotlib.easybot;

import com.example.telegrambotlib.easybot.annotation.BotController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BotControllerScanner {

    public static List<Class<?>> botControllerScanner(String basePackage) {
        try {
            return getAllClasses(basePackage);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private static List<Class<?>> getAllClasses(String basePackage) throws IOException, ClassNotFoundException {
        String basePath = basePackage.replace('.', '/');
        String classpathEntry = ClassLoader.getSystemResource(basePath).getPath();
        List<Class<?>> classesInBasePackage = scanPackage(classpathEntry, basePackage);
        List<Class<?>> classes = new ArrayList<>(classesInBasePackage);
        return filter(classes);
    }

    private static List<Class<?>> scanPackage(String path, String packageName) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String newPackageName = packageName + "." + file.getName();
                    classes.addAll(scanPackage(file.getAbsolutePath(), newPackageName));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                    classes.add(Class.forName(className));
                }
            }
        }
        return classes;
    }

    private static List<Class<?>> filter(List<Class<?>> allClasses) {
        List<Class<?>> annotatedClasses = new ArrayList<>();
        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(BotController.class)) {
                annotatedClasses.add(clazz);
            }
        }
        return annotatedClasses;
    }


}
