package Car;

import interfaces.*;

public class CarChassisStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство шасси");
        return new CarChassis();
    }
}
