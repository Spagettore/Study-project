package Tank;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class TankEngineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство двигателя");
        return new TankEngine();
    }
}
