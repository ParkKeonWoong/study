package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import com.example.demo.service.Calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * CalcSumTest
 */
@SpringBootTest
@ContextConfiguration(locations = "/junitContext.xml")
public class CalcSumTest {

    @Test
    public void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum(getClass().getResource("numbers.txt").getPath());
        int sum2 = calculator.calcMulti(getClass().getResource("numbers.txt").getPath());
        System.out.println(sum);
        assertEquals(sum, 10);
        System.out.println(sum2);
        assertEquals(sum2, 24);
    }

}