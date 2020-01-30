package com.example.demo.template;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * BufferedReaderCallback
 */
public interface BufferedReaderCallback {
    Integer doSomethingWithReader(BufferedReader br) throws IOException;
}