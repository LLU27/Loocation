package learn.loocation.models;

import java.util.Objects;

public class Address {
    private int addressId;
    private String street;
    private String city;
    private String state;
    private double latitude;
    private double longitude;

    public Address() {
    }

    public Address(int addressId, String street, String city, String state, double latitude, double longitude) {
        this.addressId = addressId;
        this.street = street;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressId == address.addressId && Double.compare(latitude, address.latitude) == 0 && Double.compare(longitude, address.longitude) == 0 && Objects.equals(street, address.street) && Objects.equals(city, address.city) && Objects.equals(state, address.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, street, city, state, latitude, longitude);
    }
}