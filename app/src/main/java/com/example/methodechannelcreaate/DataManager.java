package com.example.methodechannelcreaate;



import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private List<byte[]> imageList;
    private List<String> modelNoList;
    private List<String> dot_printerList = new ArrayList<>();

    private DataManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public List<byte[]> getImageList() {
        return imageList;
    }

    public void setImageList(List<byte[]> images) {
        this.imageList = images;
    }
    public void setModelList(List<String> modelNoList) {
        this.modelNoList = modelNoList;
    }
    public List<String> getModelNoList() {
        return modelNoList;
    }

    //
    public void setPrinterList(List<String> dot_printerList) {
        this.dot_printerList = dot_printerList;
    }
    public List<String> getPrinterListt() {
        return dot_printerList;
    }
}