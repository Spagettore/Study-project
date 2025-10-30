package Laptop;

import interfaces.IProduct;
import interfaces.IProductPart;

public class Laptop implements IProduct {
    private IProductPart body;
    private IProductPart motherboard;
    private IProductPart monitor;

    @Override
    public void installFirstPart(IProductPart part) {
        this.body = part;
        System.out.println("Установлен корпус");
    }

    @Override
    public void installSecondPart(IProductPart part) {
        this.motherboard = part;
        System.out.println("Установлена материнская плата");
    }

    @Override
    public void installThirdPart(IProductPart part) {
        this.monitor = part;
        System.out.println("Установлен монитор");
    }
}


