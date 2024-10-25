package tests.storeTests;

import clients.StoreClient;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.Order;
import utils.GenerateOrderUtils;

import java.util.ArrayList;
import java.util.List;

public class GetOrderTests {

    StoreClient storeClient = new StoreClient();
    List<Integer> orderIds = new ArrayList<>();

    @DataProvider(name = "invalidOrderIds")
    public Object[][] invalidOrderIds() {
        return new Object[][]{
                {55555},
                {"invalid-id"}
        };
    }

    @Test
    public void getOrderByIdTest() {
        Order order = GenerateOrderUtils.generateRandomOrder();
        int orderId = storeClient.createOrder(order);
        storeClient.getOrderWithValidation(orderId);
        orderIds.add(orderId);
    }

    @Test(dataProvider = "invalidOrderIds")
    public void getOrderByInvalidIdTest(Object orderId) {
        storeClient.getOrderWithValidation(orderId);
    }

    @AfterClass
    public void deleteCreatedOrders() {
        for (Integer orderId : orderIds) {
            storeClient.deleteOrder(orderId);
        }
    }
}