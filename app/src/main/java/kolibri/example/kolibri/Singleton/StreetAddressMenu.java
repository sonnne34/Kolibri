package kolibri.example.kolibri.Singleton;

import android.app.Application;

public class StreetAddressMenu extends Application {
    private static StreetAddressMenu streetAddressMenu;
    private String streetMenuFile;


    public static synchronized StreetAddressMenu getStreetAddressMenu() {
        if (streetAddressMenu == null) {
            streetAddressMenu = new StreetAddressMenu();
        }
        return streetAddressMenu;
    }

    private StreetAddressMenu() {
    }

    public void addStreetMenuFile(String streetAddressTotal) {
        streetMenuFile = streetAddressTotal;
    }
    public String getStreetMenuFile () {
        return streetMenuFile;
    }

}
