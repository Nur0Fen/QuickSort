package sort1;

import com.example.sort1.*;
import com.example.sort1.Array;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class DB_Tests {


    @Test
    void testInsert100Arrays() throws SQLException {
        boolean success = false;
        Instant start = Instant.now();
        Duration averageDuration = Duration.ofMillis(0);

        Connection conn = DatabaseConnection.connect();
        Statement stmt = conn.createStatement() ;

            // Получение количества записей до вставки
            ResultSet rsBefore = stmt.executeQuery("SELECT COUNT(*) FROM arrays");
            rsBefore.next();
            int countBefore = rsBefore.getInt(1);

            Random random = new Random();


        for (int i = 0; i < 100; i++) {
            Instant averageStart = Instant.now();
            ArrayList<String> array = GenerateArray.massive(random.nextInt(20));
            String arrayString = String.join(", ", array);
            DatabaseOperations.insertData(arrayString);
            DatabaseConnection.closeConnection();
            Instant end = Instant.now();
            averageDuration = Duration.between(averageStart, end).plus(averageDuration) ;
        }
        success = true; // Если вставка прошла без ошибок

            // Получение количества записей после вставки
            ResultSet rsAfter = stmt.executeQuery("SELECT COUNT(*) FROM arrays");
            DatabaseConnection.closeConnection();
            rsAfter.next();
            int countAfter = rsAfter.getInt(1);

            // Проверка, что добавилось ровно 100 записей
            assertEquals(100, countAfter - countBefore);
            Instant end = Instant.now();

            Duration duration = Duration.between(start, end);

            System.out.println("Результат теста создание 100 массивов: " + (success ? "Успешно" : "Ошибка"));
            System.out.println("Время выполнения: " + duration.toMillis() + " мс");
            System.out.println("Среднее время выполнения: " + averageDuration.toMillis()/100 + " мс");

            assertTrue(success); // Проверка, что тест прошел успешно
        }

    @Test
    void testInsert1000Arrays() throws SQLException {
        boolean success = false;
        Instant start = Instant.now();
        Duration averageDuration = Duration.ofMillis(0);

        Connection conn = DatabaseConnection.connect();
        Statement stmt = conn.createStatement() ;

        // Получение количества записей до вставки
        ResultSet rsBefore = stmt.executeQuery("SELECT COUNT(*) FROM arrays");
        rsBefore.next();
        int countBefore = rsBefore.getInt(1);

        Random random = new Random();


        for (int i = 0; i < 1000; i++) {
            Instant averageStart = Instant.now();
            ArrayList<String> array = GenerateArray.massive(random.nextInt(20));
            String arrayString = String.join(", ", array);
            DatabaseOperations.insertData(arrayString);
            DatabaseConnection.closeConnection();
            Instant end = Instant.now();
            averageDuration = Duration.between(averageStart, end).plus(averageDuration) ;
        }
        success = true; // Если вставка прошла без ошибок

        // Получение количества записей после вставки
        ResultSet rsAfter = stmt.executeQuery("SELECT COUNT(*) FROM arrays");
        DatabaseConnection.closeConnection();
        rsAfter.next();
        int countAfter = rsAfter.getInt(1);

        // Проверка, что добавилось ровно 1000 записей
        assertEquals(1000, countAfter - countBefore);
        Instant end = Instant.now();

        Duration duration = Duration.between(start, end);

        System.out.println("Результат теста создание 1000 массивов: " + (success ? "Успешно" : "Ошибка"));
        System.out.println("Время выполнения: " + duration.toMillis() + " мс");
        System.out.println("Среднее время выполнения: " + averageDuration.toMillis()/100 + " мс");

        assertTrue(success); // Проверка, что тест прошел успешно
    }

    @Test
    void testInsert10000Arrays() throws SQLException {
        boolean success = false;
        Instant start = Instant.now();
        Duration averageDuration = Duration.ofMillis(0);

        Connection conn = DatabaseConnection.connect();
        Statement stmt = conn.createStatement() ;

        // Получение количества записей до вставки
        ResultSet rsBefore = stmt.executeQuery("SELECT COUNT(*) FROM arrays");
        rsBefore.next();
        int countBefore = rsBefore.getInt(1);

        Random random = new Random();


        for (int i = 0; i < 10000; i++) {
            Instant averageStart = Instant.now();
            ArrayList<String> array = GenerateArray.massive(random.nextInt(20));
            String arrayString = String.join(", ", array);
            DatabaseOperations.insertData(arrayString);
            DatabaseConnection.closeConnection();
            Instant end = Instant.now();
            averageDuration = Duration.between(averageStart, end).plus(averageDuration) ;
        }
        success = true; // Если вставка прошла без ошибок

        // Получение количества записей после вставки
        ResultSet rsAfter = stmt.executeQuery("SELECT COUNT(*) FROM arrays");
        DatabaseConnection.closeConnection();
        rsAfter.next();
        int countAfter = rsAfter.getInt(1);

        // Проверка, что добавилось ровно 10000 записей
        assertEquals(10000, countAfter - countBefore);
        Instant end = Instant.now();

        Duration duration = Duration.between(start, end);

        System.out.println("Результат теста создание 10000 массивов: " + (success ? "Успешно" : "Ошибка"));
        System.out.println("Время выполнения: " + duration.toMillis() + " мс");
        System.out.println("Среднее время выполнения: " + averageDuration.toMillis()/100 + " мс");

        assertTrue(success); // Проверка, что тест прошел успешно
    }


    @Test
    void testSort100Arrays() throws SQLException {
        boolean success = false;
        Instant start = Instant.now();
        Duration averageDuration = Duration.ofMillis(0);


        Random random = new Random();

        // Наполняем БД 100 массивами
        for (int i = 0; i < 100; i++) {
            Instant averageStart = Instant.now();
            ArrayList<String> array = GenerateArray.massive(random.nextInt(20));
            String arrayString = String.join(", ", array);
            DatabaseOperations.insertData(arrayString);
            DatabaseConnection.closeConnection();
            Instant end = Instant.now();
            averageDuration = Duration.between(averageStart, end).plus(averageDuration) ;
        }

        //Получаем список всех массивов в БД
        List<Array> arrays = DatabaseOperations.getAllArrays();
        DatabaseConnection.closeConnection();

        // Берем случайный из 100 массивов и сортируем его
        for (int i = 0; i < 100; i++) {
            Instant averageStart = Instant.now();

            Array firstArray = arrays.get(random.nextInt(100));
            int id = firstArray.getId();
            String[] parts = firstArray.getArray().replaceAll("[\\[\\]]", "").split(",");

            ArrayList<String> arrayList = new ArrayList<>();
            for (String part : parts) {
                arrayList.add(part.trim());
            }
            QuickSortArray.quickSortArr(arrayList, 0, parts.length- 1);
            Instant end = Instant.now();
            averageDuration = Duration.between(averageStart, end).plus(averageDuration) ;

        }
        success = true; // Если все прошло без ошибок
        Instant end = Instant.now();

        Duration duration = Duration.between(start, end);

        System.out.println("Результат теста сортировка 100 массивов: " + (success ? "Успешно" : "Ошибка"));
        System.out.println("Время выполнения: " + duration.toMillis() + " мс");
        System.out.println("Среднее время сортировки 1 массива: " + averageDuration.toMillis()/100 + " мс");
        assertTrue(success); // Проверка, что тест прошел успешно
    }

    @Test
    void testSort1000Arrays() throws SQLException {
        boolean success = false;
        Instant start = Instant.now();
        Duration averageDuration = Duration.ofMillis(0);


        Random random = new Random();

        // Наполняем БД 1000 массивами
        for (int i = 0; i < 1000; i++) {
            Instant averageStart = Instant.now();
            ArrayList<String> array = GenerateArray.massive(random.nextInt(20));
            String arrayString = String.join(", ", array);
            DatabaseOperations.insertData(arrayString);
            DatabaseConnection.closeConnection();
            Instant end = Instant.now();
            averageDuration = Duration.between(averageStart, end).plus(averageDuration) ;
        }

        //Получаем список всех массивов в БД
        List<Array> arrays = DatabaseOperations.getAllArrays();
        DatabaseConnection.closeConnection();

        // Берем случайный из 1000 массивов и сортируем его
        for (int i = 0; i < 100; i++) {
            Instant averageStart = Instant.now();

            Array firstArray = arrays.get(random.nextInt(1000));
            int id = firstArray.getId();
            String[] parts = firstArray.getArray().replaceAll("[\\[\\]]", "").split(",");

            ArrayList<String> arrayList = new ArrayList<>();
            for (String part : parts) {
                arrayList.add(part.trim());
            }
            QuickSortArray.quickSortArr(arrayList, 0, parts.length- 1);
            Instant end = Instant.now();
            averageDuration = Duration.between(averageStart, end).plus(averageDuration) ;

        }
        success = true; // Если вставка прошла без ошибок
        Instant end = Instant.now();

        Duration duration = Duration.between(start, end);

        System.out.println("Результат теста сортировка 1000 массивов: " + (success ? "Успешно" : "Ошибка"));
        System.out.println("Время выполнения: " + duration.toMillis() + " мс");
        System.out.println("Среднее время сортировки 1 массива: " + averageDuration.toMillis()/100 + " мс");
        assertTrue(success); // Проверка, что тест прошел успешно
    }

    @Test
    void testSort10000Arrays() throws SQLException {
        boolean success = false;
        Instant start = Instant.now();
        Duration averageDuration = Duration.ofMillis(0);


        Random random = new Random();

        // Наполняем БД 10000 массивами
        for (int i = 0; i < 10000; i++) {
            Instant averageStart = Instant.now();
            ArrayList<String> array = GenerateArray.massive(random.nextInt(20));
            String arrayString = String.join(", ", array);
            DatabaseOperations.insertData(arrayString);
            DatabaseConnection.closeConnection();
            Instant end = Instant.now();
            averageDuration = Duration.between(averageStart, end).plus(averageDuration) ;
        }

        //Получаем список всех массивов в БД
        List<Array> arrays = DatabaseOperations.getAllArrays();
        DatabaseConnection.closeConnection();

        // Берем случайный из 100 массивов и сортируем его
        for (int i = 0; i < 100; i++) {
            Instant averageStart = Instant.now();

            Array firstArray = arrays.get(random.nextInt(10000));
            int id = firstArray.getId();
            String[] parts = firstArray.getArray().replaceAll("[\\[\\]]", "").split(",");

            ArrayList<String> arrayList = new ArrayList<>();
            for (String part : parts) {
                arrayList.add(part.trim());
            }
            QuickSortArray.quickSortArr(arrayList, 0, parts.length- 1);
            Instant end = Instant.now();
            averageDuration = Duration.between(averageStart, end).plus(averageDuration) ;

        }
        success = true; // Если вставка прошла без ошибок
        Instant end = Instant.now();

        Duration duration = Duration.between(start, end);

        System.out.println("Результат теста сортировка 100 массивов из 10000: " + (success ? "Успешно" : "Ошибка"));
        System.out.println("Время выполнения: " + duration.toMillis() + " мс");
        System.out.println("Среднее время сортировки 1 массива: " + averageDuration.toMillis()/100 + " мс");
        assertTrue(success); // Проверка, что тест прошел успешно
    }

    @AfterEach
    public void cleanUpDatabase() {
        Instant start = Instant.now();
        DatabaseOperations.truncArrayDb();
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("\n"+"Очистка базы данных после теста");
        System.out.println("Время выполнения очистки БД " + duration.toMillis() + " мс");
    }

}