package restaurantsimulering;

import java.io.Serializable;
import java.util.List;

public class SimulationState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public List<Order> orderQueue;
    public List<String> customers;
    public List<String> chefs;
    
    public SimulationState(List<Order> orderQueue, List<String> customers, List<String> chefs) {
        this.orderQueue = orderQueue;
        this.customers = customers;
        this.chefs = chefs;
    }
}