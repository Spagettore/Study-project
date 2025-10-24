package Glasses;

import interfaces.IAssemblyLine;
import interfaces.ILineStep;
import interfaces.IProduct;
import interfaces.IProductPart;

public class GlassesAssemblyLine implements IAssemblyLine {
    private ILineStep bodyStep;
    private ILineStep lensesStep;
    private ILineStep framesStep;

    public GlassesAssemblyLine(ILineStep glassesBodyStep, ILineStep glassesLensesStep, ILineStep glassesFramesStep) {
        this.bodyStep = glassesBodyStep;
        this.lensesStep = glassesLensesStep;
        this.framesStep = glassesFramesStep;
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        System.out.println("Сборка очков");

        IProductPart body = bodyStep.buildProductPart();
        product.installFirstPart(body);

        IProductPart lenses = lensesStep.buildProductPart();
        product.installSecondPart(lenses);

        IProductPart frames = framesStep.buildProductPart();
        product.installThirdPart(frames);

        System.out.println("Очки собраны");
        System.out.println();
        return product;
    }
}
