package Laptop;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class LaptopBodyStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство корпуса");
        return new LaptopBody();
    }
}
