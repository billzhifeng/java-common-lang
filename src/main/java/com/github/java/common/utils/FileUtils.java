package com.github.java.common.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.github.java.common.base.NameConstant;

public class FileUtils {

    public static String readFileByLines(String fileName) {
        FileInputStream file = null;
        BufferedReader reader = null;
        InputStreamReader inputFileReader = null;
        StringBuilder content = new StringBuilder();
        String tempString = null;
        try {
            file = new FileInputStream(fileName);
            inputFileReader = new InputStreamReader(file, NameConstant.UTF8);
            reader = new BufferedReader(inputFileReader);
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                content.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {

                }
            }
        }
        return content.toString();
    }

}
