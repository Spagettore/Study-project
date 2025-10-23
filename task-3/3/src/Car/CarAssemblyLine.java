package Car;

import interfaces.*;

public class CarAssemblyLine implements IAssemblyLine {
    private ILineStep bodyStep;
    private ILineStep chassisStep;
    private ILineStep engineStep;

    public CarAssemblyLine(ILineStep carBodyStep, ILineStep carChassisStep, ILineStep carEngineStep) {
        this.bodyStep = carBodyStep;
        this.chassisStep = carChassisStep;
        this.engineStep = carEngineStep;
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        System.out.println("Сборка машины");

        IProductPart body = bodyStep.buildProductPart();
        product.installFirstPart(body);

        IProductPart chassis = chassisStep.buildProductPart();
        product.installSecondPart(chassis);

        IProductPart engine = engineStep.buildProductPart();
        product.installThirdPart(engine);

        System.out.println("Машина собрана");
        System.out.println();
        return product;
    }
}
