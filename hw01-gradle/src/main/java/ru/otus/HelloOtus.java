package ru.otus;

import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;

/**
 * <b>Represents the entry point to artifact hw01-gradle</b>
 *
 * <br><br><u>To start the application:</u>
 * <br>1. <code>./gradlew build</code>
 * <br>2. <code>java -jar .\hw01-gradle\build\libs\hw01-gradle-0.1.jar</code>
 */
public class HelloOtus {
    public static void main(String[] args) {
        /* Simple Guava map example from https://www.baeldung.com/guava-maps */
        ImmutableSortedMap<String, Integer> salary = new ImmutableSortedMap
                .Builder<String, Integer>(Ordering.natural())
                .put("John", 1000)
                .put("Jane", 1500)
                .put("Adam", 2000)
                .put("Tom", 2000)
                .build();

        System.out.println(salary.firstKey());
        System.out.println("This compiles and works!");
    }
}