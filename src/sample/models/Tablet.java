package sample.models;

public class Tablet extends Gadget{
    public boolean camera;
    private double dpi;

    public Tablet() {};

    public Tablet(String title, double sizeDisplay, boolean camera, double dpi){
        super(title, sizeDisplay);
        this.camera = camera;
        this.setDpi(dpi);

    }

    @Override
    public String getDescription() {
        String isCamera = this.camera ? "есть" : "нет";
        return String.format("Камера: %s , экран, %s", isCamera, getDpi());

    }

    public double getDpi() {
        return dpi;
    }

    public void setDpi(double dpi) {
        this.dpi = dpi;
    }
}
