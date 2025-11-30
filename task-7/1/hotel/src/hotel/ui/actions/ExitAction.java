package hotel.ui.actions;

import hotel.Hotel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ExitAction implements IAction {
    @Override
    public void execute() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("hotel.dat"))) {
            oos.writeObject(Hotel.getInstance());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Hotel.getInstance().getConfig().save();
        System.exit(0);
    }
}
