package Tank;

import interfaces.IProduct;
import interfaces.IProductPart;

public class Tank implements IProduct {
    private IProductPart body;
    private IProductPart head;
    private IProductPart engine;

    @Override
    public void installFirstPart(IProductPart part) {
        this.body = part;
        System.out.println("Установлен корпус");
    }

    @Override
    public void installSecondPart(IProductPart part) {
        this.head = part;
        System.out.println("Установлена башня");
    }

    @Override
    public void installThirdPart(IProductPart part) {
        this.engine = part;
        System.out.println("Установлен двигатель");
    }
}


