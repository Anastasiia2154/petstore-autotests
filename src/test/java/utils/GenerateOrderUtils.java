package utils;

import pojo.Order;

import java.util.Random;

public class GenerateOrderUtils {
    private static final Random random = new Random();

    public static Order generateRandomOrder() {
        int id = random.nextInt(1000);
        int petId = random.nextInt(100);
        int quantity = random.nextInt(10) + 1;
        String date = "2024-10-" + (random.nextInt(31) + 1) + "T10:00:00Z";
        String status = random.nextBoolean() ? "placed" : "approved";
        boolean complete = random.nextBoolean();
        return new Order(id, petId, quantity, date, status, complete);
    }
}