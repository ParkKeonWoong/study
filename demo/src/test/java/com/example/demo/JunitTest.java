package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * JunitTest
 */
@ContextConfiguration(locations = "/junitContext.xml")
public class JunitTest {
    
    @Autowired
    ApplicationContext context;

    static Set<JunitTest> testObjects = new HashSet<JunitTest>();
    static ApplicationContext contextObject = null;

    @Test  
    public void test1(){
		assertEquals(false, testObjects.contains(this));
        testObjects.add(this);
        assertEquals(contextObject == null || contextObject == this.context, true);
        contextObject = this.context;

        System.out.println(this.context);
        System.out.println(contextObject);
    }

    @Test  
    public void test2(){
		assertEquals(false, testObjects.contains(this));
        testObjects.add(this);
        assertEquals(contextObject == null || contextObject == this.context, true);
        contextObject = this.context;

        System.out.println(this.context);
        System.out.println(contextObject);
    }

    @Test  
    public void test3(){
		assertEquals(false, testObjects.contains(this));
        testObjects.add(this);
        assertEquals(contextObject , this.context);
        contextObject = this.context;

        System.out.println(this.context);
        System.out.println(contextObject);
    }

    @Test  
    public void test4(){

       for (JunitTest junitTest : testObjects) {
        System.out.println(testObjects.toArray());
       
    }
    }
}