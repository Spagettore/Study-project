package Pen;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class PenRodStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство стержня");
        return new PenRod();
    }
}
