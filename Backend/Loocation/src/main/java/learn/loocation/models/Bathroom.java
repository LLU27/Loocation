package learn.loocation.models;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bathroom bathroom = (Bathroom) o;
        return bathroomId == bathroom.bathroomId && accessibility == bathroom.accessibility && changing_station == bathroom.changing_station && unisex == bathroom.unisex && Objects.equals(name, bathroom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bathroomId, name, accessibility, changing_station, unisex);
    }
}