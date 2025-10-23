package Car;

import interfaces.*;

public class CarBodyStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство кузова");
        return new CarBody();
    }
}
