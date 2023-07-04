package ru.otus.test;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SomeTest {

    @Before
    public void beforeA() {
        List<Integer> list = new ArrayList<>();
    }

    @Test
    public void testA() {
        int[] ints = {1, 2, 3};
        int i = ints[1];
    }

    @After
    public void afterA() {
        List<Integer> list = null;
    }


    @Before
    public void beforeB() {
        List<Integer> list = new ArrayList<>();
    }

    @Test
    public void testB() {
        int[] ints = {1, 2, 3};
        int i = ints[5];
    }

    @After
    public void afterB() {
        List<Integer> list = null;
    }


    @Before
    public void beforeC() {
        List<Integer> list = new ArrayList<>();
    }

    @Test
    public void testC() {
        int[] ints = {1, 2, 3};
        int i = ints[0];
    }

    @After
    public void afterC() {
        List<Integer> list = null;
    }

    @Before
    public void beforeD() {
        List<Integer> list = new ArrayList<>();
    }

    @Test
    public void testD() {
        int[] ints = {1, 2, 3};
        int i = ints[7];
    }

    @After
    public void afterD() {
        List<Integer> list = null;
    }
}
