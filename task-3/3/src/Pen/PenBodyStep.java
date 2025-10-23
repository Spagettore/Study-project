package Pen;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class PenBodyStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство корпуса");
        return new PenBody();
    }
}
