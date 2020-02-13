package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.service.Hello;
import com.example.demo.service.HelloTarget;
import com.example.demo.service.HelloUppercase;

import org.junit.jupiter.api.Test;

/**
 * HelloTest
 */
public class HelloTest {

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget(); //타깃은 인터페이스를 이용하는 습관.
        assertEquals(hello.sayHello("Toby"),"Hello Toby");
        assertEquals(hello.sayHi("Toby"),"Hi Toby");
        assertEquals(hello.sayThankYou("Toby"),"Thank You Toby");
    }

    @Test
    public void HelloUppercaseProxyTest() {
        Hello proxiedHello = new HelloUppercase(new HelloTarget());
        assertEquals(proxiedHello.sayHello("Toby"),"HELLO TOBY");
        assertEquals(proxiedHello.sayHi("Toby"),"HI TOBY");
        assertEquals(proxiedHello.sayThankYou("Toby"),"THANK YOU TOBY");
    }
}