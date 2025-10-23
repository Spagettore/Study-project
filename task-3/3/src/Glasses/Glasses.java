package Glasses;

import interfaces.IProduct;
import interfaces.IProductPart;

public class Glasses implements IProduct {
    private IProductPart body;
    private IProductPart lenses;
    private IProductPart frames;

    @Override
    public void installFirstPart(IProductPart part) {
        this.body = part;
        System.out.println("Установлен корпус");
    }

    @Override
    public void installSecondPart(IProductPart part) {
        this.lenses = part;
        System.out.println("Установлены линзы");
    }

    @Override
    public void installThirdPart(IProductPart part) {
        this.frames = part;
        System.out.println("Установлены дужки");
    }
}


