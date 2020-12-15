package sample.models;

public class Laptop extends Gadget {
    public boolean keyBacklight;
    public int sizeDisc;
    public enum TypeCore {one, two, fore, eight} // какие типы
    public TypeCore typeCore;// а это собственно тип


    public Laptop() {};

    public Laptop (String title, double sizeDisplay, int sizeDisc, TypeCore typeCore, boolean keyBacklight ){
        super(title, sizeDisplay);
        this.sizeDisc = sizeDisc;
        this.typeCore = typeCore;
        this.keyBacklight = keyBacklight;

    }

    @Override
    public String getDescription() {
        String typeString = "";
        switch (this.typeCore)
        {
            case one:
                typeString = "одноядерный";
                break;
            case two:
                typeString = "двухядерный";
                break;
            case fore:
                typeString = "четырехядерный";
                break;
            case eight:
                typeString = "восьмиядерный";
                break;
        }
        String isLight = this.keyBacklight ? "есть" : "нет";

        return String.format("Процессор: %s, подсветка: %s, объем ж.диска: %s гб",  typeString, isLight,sizeDisc);
    }


}
