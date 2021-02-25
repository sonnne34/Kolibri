package kolibri.example.kolibri.Singleton;

import android.app.Application;

public class ApartmentAddressMenu extends Application {
    private static ApartmentAddressMenu apartmentAddressMenu;
    private String apartmentMenuFile;


    public static synchronized ApartmentAddressMenu getApartmentAddressMenu() {
        if (apartmentAddressMenu == null) {
            apartmentAddressMenu = new ApartmentAddressMenu();
        }
        return apartmentAddressMenu;
    }

    private ApartmentAddressMenu() {
    }

    public void addApartmentMenuFile(String apartmentAddressTotal) {
        apartmentMenuFile = apartmentAddressTotal;
    }
    public String getApartmentMenuFile () {
        return apartmentMenuFile;
    }

}
