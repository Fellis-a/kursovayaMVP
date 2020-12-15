package sample.models;

import java.util.ArrayList;

public class Smartphone extends Gadget{

   // public boolean typeSim;// а это собственно тип
    public double megapixels;
    public String battery;
    public boolean oneSim;
    public boolean twoSim;


    public Smartphone() {};

    public Smartphone(String title, double sizeDisplay, double megapixels, String battery, boolean oneSim, boolean twoSim ) {
        super(title, sizeDisplay);
        this.megapixels = megapixels;
        this.battery = battery;
        this.oneSim = oneSim;
        this.twoSim = twoSim;
    }


    @Override
    public String getDescription() {
        ArrayList<String> items = new ArrayList<>();
        if (this.oneSim)
            items.add("одна sim");
        if (this.twoSim)
            items.add("две sim");

        return String.format(" Кол-во sim: %s, камера: %s мп, батарея %s мАч", items, megapixels, battery);
    }



}
