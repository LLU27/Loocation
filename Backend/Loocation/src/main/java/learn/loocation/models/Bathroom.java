package learn.loocation.models;

public class Bathroom {
    private int bathroomId;
    private String name;
    private boolean accessibility;
    private boolean changing_station;
    private boolean unisex;

    public Bathroom() {
    }

    public Bathroom(int bathroomId, String name, boolean accessibility, boolean changing_station, boolean unisex) {
        this.bathroomId = bathroomId;
        this.name = name;
        this.accessibility = accessibility;
        this.changing_station = changing_station;
        this.unisex = unisex;
    }

    public int getBathroomId() {
        return bathroomId;
    }

    public void setBathroomId(int bathroomId) {
        this.bathroomId = bathroomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAccessibility() {
        return accessibility;
    }

    public void setAccessibility(boolean accessibility) {
        this.accessibility = accessibility;
    }

    public boolean isChanging_station() {
        return changing_station;
    }

    public void setChanging_station(boolean changing_station) {
        this.changing_station = changing_station;
    }

    public boolean isUnisex() {
        return unisex;
    }

    public void setUnisex(boolean unisex) {
        this.unisex = unisex;
    }

}