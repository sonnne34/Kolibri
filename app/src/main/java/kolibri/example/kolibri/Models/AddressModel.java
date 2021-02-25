package kolibri.example.kolibri.Models;

public class AddressModel {
    private String street;
    private long numderHouse;
    private long numberFlat;
    private long numberPodeza;
    private long numberFloor;


    public AddressModel(String street, long numderHouse, long numberFlat, long numberPodeza, long numberFloor) {
        this.street = street;
        this.numderHouse = numderHouse;
        this.numberFlat = numberFlat;
        this.numberPodeza = numberPodeza;
        this.numberFloor = numberFloor;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public long getNumderHouse() {
        return numderHouse;
    }

    public void setNumderHouse(long numderHouse) {
        this.numderHouse = numderHouse;
    }

    public long getNumberFlat() {
        return numberFlat;
    }

    public void setNumberFlat(long numberFlat) {
        this.numberFlat = numberFlat;
    }

    public long getNumberPodeza() {
        return numberPodeza;
    }

    public void setNumberPodeza(long numberPodeza) {
        this.numberPodeza = numberPodeza;
    }

    public long getNumberFloor() {
        return numberFloor;
    }

    public void setNumberFloor(long numberFloor) {
        this.numberFloor = numberFloor;
    }
}
