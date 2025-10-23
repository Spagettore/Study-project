package Glasses;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class GlassesBodyStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство корпуса");
        return new GlassesBody();
    }
}
