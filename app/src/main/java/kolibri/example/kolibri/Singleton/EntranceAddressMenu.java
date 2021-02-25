package kolibri.example.kolibri.Singleton;

import android.app.Application;

public class EntranceAddressMenu extends Application {
    private static EntranceAddressMenu entranceAddressMenu;
    private String entranceMenuFile;


    public static synchronized EntranceAddressMenu getEntranceAddressMenu() {
        if (entranceAddressMenu == null) {
            entranceAddressMenu = new EntranceAddressMenu();
        }
        return entranceAddressMenu;
    }

    private EntranceAddressMenu() {
    }

    public void addEntranceMenuFile(String entranceAddressTotal) {
        entranceMenuFile = entranceAddressTotal;
    }
    public String getEntranceMenuFile () {
        return entranceMenuFile;
    }

}
