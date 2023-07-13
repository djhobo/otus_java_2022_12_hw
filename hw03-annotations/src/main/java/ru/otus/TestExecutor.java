package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class TestExecutor {

    //Запуск исполнения тестовых процедур
    public static void execute(Class<?> testedClass) throws ClassNotFoundException {
        TestExecutor executor = new TestExecutor();
        Class<?> clazz = Class.forName(testedClass.getName());

        List<Method> beforeMethods = executor.getAnnotatedMethod(clazz.getDeclaredMethods(), Before.class, clazz);
        List<Method> testMethods = executor.getAnnotatedMethod(clazz.getDeclaredMethods(), Test.class, clazz);
        List<Method> afterMethods = executor.getAnnotatedMethod(clazz.getDeclaredMethods(), After.class, clazz);

        int passedCount = 0;
        for (Method m : testMethods) {
            Object objectClass = executor.getObjectClass(clazz);
            executor.executeAnnotatedMethods(beforeMethods, objectClass);
            try {
                m.invoke(objectClass);
                System.out.print(m.getName() + " PASSED! ");
                passedCount++;
            } catch (Exception e) {
                System.out.print(m.getName() + " FAILED! ");
            }
            executor.executeAnnotatedMethods(afterMethods, objectClass);
        }
        executor.executionStatistic(testMethods.size(), passedCount);
    }

    //Создает объект переданного класса.
    private <T> T getObjectClass(Class<T> typeClass) {
        try {
            return typeClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Формирование списка методов с определенной аннотацией.
    @SuppressWarnings("unchecked")
    private List<Method> getAnnotatedMethod(Method[] methods, Class<?> annotation, Class<?> clazz) {
        List<Method> methodsVsAnnotation = new ArrayList<>();
        for (Method m : methods) {
            try {
                if (clazz.getMethod(m.getName()).isAnnotationPresent((Class<? extends Annotation>) annotation)) {
                    methodsVsAnnotation.add(m);
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return methodsVsAnnotation;
    }

    //Запуск списка методов с определенной аннотацией. Возвращает количество успешно пройденных тестов.
    private void executeAnnotatedMethods(List<Method> method, Object testedClass) {
        System.out.println();
        for (Method m : method) {
            try {
                m.invoke(testedClass);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void executionStatistic(int numberOfMethods, int testPassedCount) {
        int testFailedCount = numberOfMethods - testPassedCount;
        System.out.println("\nTest execution statistics");
        System.out.println("*******************************************");
        System.out.println("Tests were performed: " + numberOfMethods + "!");
        System.out.println("Of them successfully: " + testPassedCount + "!");
        System.out.println("Of them unsuccessful: " + testFailedCount + "!");
        System.out.println("*******************************************\n");
    }
}
