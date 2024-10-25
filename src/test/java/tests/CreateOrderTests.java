package tests;

import clients.StoreClient;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.Order;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderTests {
    StoreClient storeClient = new StoreClient();
    List<Integer> orderIds = new ArrayList<>();

    @DataProvider(name = "validData")
    public Object[][] createPositiveOrdersTest() {
        return new Object[][]{
                {new Order(1, 2, 3, "2024-10-23T10:00:00Z", "placed", true)},
                {new Order(2, 3, 5, "2024-11-01T14:30:00Z", "approved", false)},
                {new Order(3, 4, 10, "2024-12-15T09:45:00Z", "delivered", true)},
                {new Order(4, 1, 1, "2024-10-01T00:00:00Z", "placed", false)}
        };
    }

    @DataProvider(name = "missingData")
    public Object[][] missingData() {
        return new Object[][]{
                {new Order(1, null, 3, "2024-10-23T10:00:00Z", "placed", true)},
                {new Order(2, 3, null, "2024-11-01T14:30:00Z", "approved", false)},
                {new Order(3, 4, 10, "2024-12-15T09:45:00Z", null, true)},
                {new Order(null, null, null, null, null, null)}
        };
    }

    @DataProvider(name = "invalidOrderData")
    public Object[][] invalidData() {
        return new Object[][]{
                {new Order(1, 2, 3, "invalid-date", "placed", true)},
                {new Order(2, 3, -5, "2024-11-01T14:30:00Z", "approved", false)},
                {new Order(3, 4, 10, "2024-12-15T09:45:00Z", "invalid-status", true)},
                {new Order(4, -999, 5, "2024-10-01T00:00:00Z", "placed", true)}
        };
    }

    @Test(dataProvider = "validData")
    public void createValidOrderTest(Order order) {
        int orderId = storeClient.createOrder(order);
        orderIds.add(orderId);
    }

    @Test(dataProvider = "missingData")
    public void testCreateOrderWithMissingFieldsTest(Order order) {
        storeClient.createOrderWithMissingFields(order);
    }


    @Test(dataProvider = "invalidOrderData")
    public void testCreateOrderWithInvalidData(Order order) {
        storeClient.createOrderWithInvalidData(order);
    }


    @AfterClass
    public void deleteCreatedOrders() {
        for (Integer orderId : orderIds) {
            storeClient.deleteOrder(orderId);
        }
    }
}