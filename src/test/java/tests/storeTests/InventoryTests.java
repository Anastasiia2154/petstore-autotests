package tests.storeTests;

import clients.StoreClient;
import org.testng.annotations.Test;

public class InventoryTests {

    StoreClient storeClient = new StoreClient();

    @Test
    public void getInventoryPositiveTest() {
      storeClient.getInventory();
    }

    @Test
    public void getInventoryNegativeTest() {
        storeClient.getInventoryInvalidRequest();
    }
}