package restaurantsimulering;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class StateSaver {
    public static void saveState(SimulationState state, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(state);
            System.out.println("Simulation State saved to " +  filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}