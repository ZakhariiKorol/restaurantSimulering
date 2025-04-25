package restaurantsimulering;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class StateLoader {
    public static SimulationState loadState(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            SimulationState state = (SimulationState) in.readObject();
            System.out.println("Simulation State loaded from " + filename);
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}