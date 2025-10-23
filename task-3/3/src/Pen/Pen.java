package Pen;

import interfaces.IProduct;
import interfaces.IProductPart;

public class Pen implements IProduct {
    private IProductPart body;
    private IProductPart spring;
    private IProductPart rod;

    @Override
    public void installFirstPart(IProductPart part) {
        this.body = part;
        System.out.println("Установлен корпус");
    }

    @Override
    public void installSecondPart(IProductPart part) {
        this.spring = part;
        System.out.println("Установлена пружина");
    }

    @Override
    public void installThirdPart(IProductPart part) {
        this.rod = part;
        System.out.println("Установлен стержень");
    }
}


