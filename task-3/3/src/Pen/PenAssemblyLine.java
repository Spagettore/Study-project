package Pen;

import interfaces.IAssemblyLine;
import interfaces.ILineStep;
import interfaces.IProduct;
import interfaces.IProductPart;

public class PenAssemblyLine implements IAssemblyLine {
    private ILineStep bodyStep;
    private ILineStep springStep;
    private ILineStep rodStep;

    public PenAssemblyLine(ILineStep penBodyStep, ILineStep penSpringStep, ILineStep penRodStep) {
        this.bodyStep = penBodyStep;
        this.springStep = penSpringStep;
        this.rodStep = penRodStep;
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        System.out.println("Сборка ручки");

        IProductPart body = bodyStep.buildProductPart();
        product.installFirstPart(body);

        IProductPart spring = springStep.buildProductPart();
        product.installSecondPart(spring);

        IProductPart rod = rodStep.buildProductPart();
        product.installThirdPart(rod);

        System.out.println("Ручка собрана");
        System.out.println();
        return product;
    }
}
