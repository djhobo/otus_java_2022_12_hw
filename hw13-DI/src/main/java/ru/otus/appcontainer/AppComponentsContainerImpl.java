package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        List<Method> methods = getMethodsVsAnnotation(configClass);
        Object instance = getInstanceConfigClass(configClass);
        checkConfigForDuplicatesByName(configClass);
        appComponentsByName.putAll(getMapComponents(methods, instance));
        appComponents.addAll(appComponentsByName.values());
    }

    private <C> C getInstanceConfigClass(Class<C> component) {
        try {
            return component.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> getMapComponents(List<Method> methods, Object configInstance) {
        Map<String, Object> components = new HashMap<>();
        for (Method method : methods) {
            String name = method.getAnnotation(AppComponent.class).name();
            Object component = getComponent(method, configInstance, new ArrayList<>(components.values()));
            components.put(name, component);
        }
        return components;
    }

    private Object getComponent(Method method, Object configInstance, List<Object> components) {
        List<Parameter> parameters = Arrays.asList(method.getParameters());
        try {
            if (parameters.isEmpty()) {
                return method.invoke(configInstance);
            } else {
                List<Object> argsMethod = new ArrayList<>();
                for (Parameter parameter : parameters) {
                    Class<?> type = parameter.getType();
                    for (Object component : components) {
                        if (type.isAssignableFrom(component.getClass())) {
                            argsMethod.add(component);
                        }
                    }
                }
                return method.invoke(configInstance, argsMethod.toArray());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Method> getMethodsVsAnnotation(Class<?> configClass) {
        List<Method> methodsVsAnnotation = new ArrayList<>();
        for (Method method : configClass.getMethods()) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                methodsVsAnnotation.add(method);
            }
        }
        methodsVsAnnotation.sort(Comparator.comparingInt(x -> x.getAnnotation(AppComponent.class).order()));
        return methodsVsAnnotation;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private void checkConfigForDuplicatesByName(Class<?> configClass) {
        var methods = getMethodsVsAnnotation(configClass);
        List<String> nameList = new ArrayList<>();
        for (Method method : methods) {
            nameList.add(method.getAnnotation(AppComponent.class).name());
        }
        Set<String> nameSet = new HashSet<>(nameList);
        if (nameSet.size() != nameList.size()) {
            throw new RuntimeException();
        }
    }

    private void checkConfigForDuplicatesByClass() {
        Set<Object> set = new HashSet<>();
        for (Object component : appComponents) {
            set.add(component.getClass());
        }
        if (appComponents.size() != set.size()) {
            throw new RuntimeException();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        checkConfigForDuplicatesByClass();
        for (Object component : appComponents) {
            if (component.getClass().equals(componentClass) || componentClass.isAssignableFrom(component.getClass())) {
                return (C) component;
            }
        }
        throw new RuntimeException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(String componentName) {
        if (appComponentsByName.containsKey(componentName)) {
            return (C) appComponentsByName.get(componentName);
        }
        throw new RuntimeException();
    }
}
