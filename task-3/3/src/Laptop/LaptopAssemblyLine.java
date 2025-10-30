package Laptop;

import interfaces.IAssemblyLine;
import interfaces.ILineStep;
import interfaces.IProduct;
import interfaces.IProductPart;

public class LaptopAssemblyLine implements IAssemblyLine {
    private ILineStep bodyStep;
    private ILineStep motherboardStep;
    private ILineStep monitorStep;

    public LaptopAssemblyLine(ILineStep laptopBodyStep, ILineStep laptopMotherboardStep, ILineStep laptopMonitorStep) {
        this.bodyStep = laptopBodyStep;
        this.motherboardStep = laptopMotherboardStep;
        this.monitorStep = laptopMonitorStep;
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        System.out.println("Сборка ноутбука");

        IProductPart body = bodyStep.buildProductPart();
        product.installFirstPart(body);

        IProductPart motherboard = motherboardStep.buildProductPart();
        product.installSecondPart(motherboard);

        IProductPart monitor = monitorStep.buildProductPart();
        product.installThirdPart(monitor);

        System.out.println("Ноутбук собран");
        System.out.println();
        return product;
    }
}
