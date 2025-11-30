import hotel.Hotel;
import hotel.ui.MenuController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main {
    public static void main(String[] args) {
        if (new File("hotel.dat").exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("hotel.dat"))) {
                Hotel hotel = (Hotel) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        MenuController.getInstance().run();
    }
}
