package com.example.demo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.example.demo.template.BufferedReaderCallback;
import com.example.demo.template.LineCallback;

/**
 * Calculator
 */
public class Calculator {

    public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            T res = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null){
                try { br.close();}
                catch(IOException e) {System.out.println(e.getMessage());}
            }
        }
    }

    private Integer fileReadTemplates(String filepath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            
            int ret = callback.doSomethingWithReader(br);
            return ret;
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null){
                try { br.close();}
                catch(IOException e) {System.out.println(e.getMessage());}
            }
        }
        
    }


    public Integer calcSum(String filepath) throws IOException {
        LineCallback<Integer> sumCallback = new LineCallback<Integer>(){

            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filepath, sumCallback, 0);
        
    }

    public Integer calcMulti(String filepath) throws IOException {
        LineCallback<Integer> multiLineCallback = new LineCallback<Integer>(){
        
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return Integer.valueOf(line) * value;
            }
        };

        return lineReadTemplate(filepath, multiLineCallback, 1);
    }

    public String concatenate(String filepath) throws IOException {
        LineCallback<String> multiLineCallback = new LineCallback<String>(){
        
            @Override
            public String doSomethingWithLine(String line, String value) {
                return line + value;
            }
        };

        return lineReadTemplate(filepath, multiLineCallback, "");
    }
}