import interfaces.*;
import Car.*;
import Tank.*;
import Laptop.*;
import Pen.*;
import Glasses.*;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //переводим кодировку на windows-1251 для отображения русских символов
        System.setOut(new PrintStream(System.out, true, "Cp1251"));
        //машина
        ILineStep carBodyStep = new CarBodyStep();
        ILineStep carChassisStep = new CarChassisStep();
        ILineStep carEngineStep = new CarEngineStep();

        IAssemblyLine carAssemblyLine = new CarAssemblyLine(carBodyStep, carChassisStep, carEngineStep);
        IProduct car = new Car();

        IProduct finishedCar = carAssemblyLine.assembleProduct(car);

        //танк
        ILineStep tankBodyStep = new TankBodyStep();
        ILineStep tankHeadStep = new TankHeadStep();
        ILineStep tankEngineStep = new TankEngineStep();

        IAssemblyLine tankAssemblyLine = new TankAssemblyLine(tankBodyStep, tankHeadStep, tankEngineStep);
        IProduct tank = new Tank();

        IProduct finishedTank = tankAssemblyLine.assembleProduct(tank);

        //ноутбук
        ILineStep laptopBodyStep = new LaptopBodyStep();
        ILineStep laptopMotherboardStep = new LaptopMotherboardStep();
        ILineStep laptopMonitorStep = new LaptopMonitorStep();

        IAssemblyLine laptopAssemblyLine = new LaptopAssemblyLine(laptopBodyStep, laptopMotherboardStep, laptopMonitorStep);
        IProduct laptop = new Laptop();

        IProduct finishedLaptop = laptopAssemblyLine.assembleProduct(laptop);

        //ручка
        ILineStep penBodyStep = new PenBodyStep();
        ILineStep penSpringStep = new PenSpringStep();
        ILineStep penRodStep = new PenRodStep();

        IAssemblyLine penAssemblyLine = new PenAssemblyLine(penBodyStep, penSpringStep, penRodStep);
        IProduct pen = new Pen();

        IProduct finishedPen = penAssemblyLine.assembleProduct(pen);

        //очки
        ILineStep glassesBodyStep = new GlassesBodyStep();
        ILineStep glassesLensesStep = new GlassesLensesStep();
        ILineStep glassesFramesStep = new GlassesFramesStep();

        IAssemblyLine glassesAssemblyLine = new GlassesAssemblyLine(glassesBodyStep, glassesLensesStep, glassesFramesStep);
        IProduct glasses = new Glasses();

        IProduct finishedGlasses = glassesAssemblyLine.assembleProduct(glasses);
    }
}
