package Glasses;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class GlassesLensesStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство линз");
        return new GlassesLenses();
    }
}
