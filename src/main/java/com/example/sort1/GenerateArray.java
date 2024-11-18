package com.example.sort1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GenerateArray {

    static final int x = 5;
    public static ArrayList<String> GetArray() {
        return massive(x);
    }

        public static ArrayList<String> massive(int x) {
            ArrayList<String> array1 = new ArrayList<String>();
            int length = x; // длина массива
            Random random = new Random(); // создаем объект класса Random
            for (int i = 0; i < length; i++) {
                // заполняем каждый элемент случайным числом от 0 до 99
                array1.add(String.valueOf( random.nextInt(100)));
            }
            return array1;
        }

}
