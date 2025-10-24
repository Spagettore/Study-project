package Tank;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class TankBodyStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство корпуса");
        return new TankBody();
    }
}
