package Car;

import interfaces.*;

public class Car implements IProduct {
    private IProductPart body;
    private IProductPart chassis;
    private IProductPart engine;

    @Override
    public void installFirstPart(IProductPart part) {
        this.body = part;
        System.out.println("Установлен кузов");
    }

    @Override
    public void installSecondPart(IProductPart part) {
        this.chassis = part;
        System.out.println("Установлено шасси");
    }

    @Override
    public void installThirdPart(IProductPart part) {
        this.engine = part;
        System.out.println("Установлен двигатель");
    }
}


