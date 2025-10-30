package Glasses;

import interfaces.ILineStep;
import interfaces.IProductPart;

public class GlassesFramesStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производство дужек");
        return new GlassesFrames();
    }
}
