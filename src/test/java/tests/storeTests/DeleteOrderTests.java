package tests.storeTests;

import clients.StoreClient;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.Order;
import utils.GenerateOrderUtils;

public class DeleteOrderTests {

    StoreClient storeClient = new StoreClient();

    @DataProvider(name = "invalidOrderIds")
    public Object[][] invalidOrderIds() {
        return new Object[][]{
                {555555555},
                {-1},
        };
    }

    @Test
    public void deleteOrderTest() {
        Order order = GenerateOrderUtils.generateRandomOrder();
        int orderId = storeClient.createOrder(order);
        storeClient.deleteOrder(orderId);
    }

    @Test(dataProvider = "invalidOrderIds")
    public void deleteOrderByInvalidIdTest(int orderId) {
        storeClient.deleteOrder(orderId);
    }
}