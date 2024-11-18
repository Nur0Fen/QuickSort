package com.example.sort1;
import java.util.ArrayList;


public class QuickSortArray {

    public static void quickSortArr(ArrayList<String> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortArr(arr, low, pi - 1);  // Before pi
            quickSortArr(arr, pi + 1, high); // After pi
        }
    }

    // Метод для разделения массива
    private static int partition(ArrayList<String> arr, int low, int high) {
        String pivot = arr.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            // Если текущий элемент меньше или равен опорному
            if (Integer.parseInt(arr.get(j)) <= Integer.parseInt(pivot)) {
                i++;
                // Меняем местами элементы
                String temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }
        // Меняем местами опорный элемент и элемент, стоящий после разделения
        String temp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, temp);
        return i + 1;
    }
}

