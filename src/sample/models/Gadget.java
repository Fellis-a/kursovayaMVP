package sample.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties({"description"})
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public class Gadget {
    public double sizeDisplay;
    public String title;
    public Integer id = null;

    public Gadget() {};

    public Gadget(String title, double sizeDisplay) {
        this.setSizeDisplay(sizeDisplay);
        this.setTitle(title);
    }




    @Override
    public String toString() {
        return String.format("%s: %s дюйм", this.getTitle(), this.getSizeDisplay());
    }

    public double getSizeDisplay() {
        return sizeDisplay;
    }

    public void setSizeDisplay(double sizeDisplay) {
        this.sizeDisplay = sizeDisplay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return "";
    }

}
