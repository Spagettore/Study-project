package Pen;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class PenSpringStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство пружины");
        return new PenSpring();
    }
}
