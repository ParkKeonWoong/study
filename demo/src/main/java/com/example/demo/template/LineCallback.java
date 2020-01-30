package com.example.demo.template;

/**
 * LineCallback
 */
public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}