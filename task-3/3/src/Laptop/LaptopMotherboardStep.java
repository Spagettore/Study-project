package Laptop;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class LaptopMotherboardStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство материнской платы");
        return new LaptopMotherboard();
    }
}
