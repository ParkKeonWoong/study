package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * JunitTest
 */
public class JunitTest {
    
    static Set<JunitTest> testObjects = new HashSet<JunitTest>();

    @Test  
    public void test1(){
		assertEquals(false, testObjects.contains(this));
        testObjects.add(this);
        System.out.println(this);
    }

    @Test  
    public void test2(){
		assertEquals(false, testObjects.contains(this));
        testObjects.add(this);
        System.out.println(this);
    }

    @Test  
    public void test3(){
		assertEquals(false, testObjects.contains(this));
        testObjects.add(this);
        System.out.println(this);
    }

    @Test  
    public void test4(){

       for (JunitTest junitTest : testObjects) {
        System.out.println(testObjects.toArray());
       
    }
    }
}