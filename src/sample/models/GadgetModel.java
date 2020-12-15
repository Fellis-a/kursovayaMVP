package sample.models;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GadgetModel {

    Class<? extends Gadget> gadgetFilter = Gadget.class;

    ArrayList<Gadget> drinkList = new ArrayList<>();
    private int counter = 1;

    public interface DataChangedListener {
        void dataChanged(ArrayList<Gadget> gadget);
    }

    private ArrayList<DataChangedListener> dataChangedListeners = new ArrayList<>();
    public void addDataChangedListener(DataChangedListener listener) {

        this.dataChangedListeners.add(listener);
    }
    public void setGadgetFilter(Class<? extends Gadget> gadgetFilter) {
        this.gadgetFilter = gadgetFilter;
        this.emitDataChanged();
    }


    public void add(Gadget gadget, boolean emit) {
        gadget.id = counter;
        counter += 1;

        this.drinkList.add(gadget);

        if (emit) {
            this.emitDataChanged();
        }
    }

    public void add(Gadget gadget) {
        add(gadget, true);
    }

    private void emitDataChanged() {
        for (DataChangedListener listener : dataChangedListeners) {
            ArrayList<Gadget> filteredList = new ArrayList<>(
                    drinkList.stream()
                            .filter(food -> gadgetFilter.isInstance(food))
                            .collect(Collectors.toList())
            );
            listener.dataChanged(filteredList);
        }
    }

    public void edit(Gadget gadget) {

        for (int i = 0; i< this.drinkList.size(); ++i) {

            if (this.drinkList.get(i).id == gadget.id) {

                this.drinkList.set(i, gadget);
                break;
            }
        }
        this.emitDataChanged();
    }

    public void delete(int id)
    {
        for (int i = 0; i< this.drinkList.size(); ++i) {

            if (this.drinkList.get(i).id == id) {
                this.drinkList.remove(i);
                break;
            }
        }
        this.emitDataChanged();
    }

    public void saveToFile(String path) {

        try (Writer writer =  new FileWriter(path)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerFor(new TypeReference<ArrayList<Gadget>>() { })
                    .withDefaultPrettyPrinter()
                    .writeValue(writer, drinkList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String path) {
        try (Reader reader =  new FileReader(path)) {
            ObjectMapper mapper = new ObjectMapper();
            drinkList = mapper.readerFor(new TypeReference<ArrayList<Gadget>>() { })
                    .readValue(reader);

            this.counter = drinkList.stream()
                    .map(gadget -> gadget.id)
                    .max(Integer::compareTo)
                    .orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.emitDataChanged();
    }




}
