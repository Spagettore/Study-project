package Car;

import interfaces.*;

public class CarEngineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство двигателя");
        return new CarEngine();
    }
}
