package Tank;

import interfaces.IAssemblyLine;
import interfaces.ILineStep;
import interfaces.IProduct;
import interfaces.IProductPart;

public class TankAssemblyLine implements IAssemblyLine {
    private ILineStep bodyStep;
    private ILineStep headStep;
    private ILineStep engineStep;

    public TankAssemblyLine(ILineStep tankBodyStep, ILineStep tankHeadStep, ILineStep tankEngineStep) {
        this.bodyStep = tankBodyStep;
        this.headStep = tankHeadStep;
        this.engineStep = tankEngineStep;
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        System.out.println("Сборка танка");

        IProductPart body = bodyStep.buildProductPart();
        product.installFirstPart(body);

        IProductPart head = headStep.buildProductPart();
        product.installSecondPart(head);

        IProductPart engine = engineStep.buildProductPart();
        product.installThirdPart(engine);

        System.out.println("Танк собран");
        System.out.println();
        return product;
    }
}
