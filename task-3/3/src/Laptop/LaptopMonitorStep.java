package Laptop;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class LaptopMonitorStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство монитора");
        return new LaptopMonitor();
    }
}
