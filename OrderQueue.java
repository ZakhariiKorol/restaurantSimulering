package restaurantsimulering;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//Handles synch of orders
public class OrderQueue {
    private final BlockingQueue<Order> orders = new LinkedBlockingQueue<>(10);
    
    public void placeOrder(Order order) throws InterruptedException {
        orders.put(order);
    }
    
    public Order takeOrder() throws InterruptedException {
        return orders.take();
    }
}