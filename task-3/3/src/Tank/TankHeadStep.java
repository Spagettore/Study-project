package Tank;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class TankHeadStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство башни");
        return new TankHead();
    }
}
