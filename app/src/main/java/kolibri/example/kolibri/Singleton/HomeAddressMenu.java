package kolibri.example.kolibri.Singleton;

import android.app.Application;

public class HomeAddressMenu extends Application {
    private static HomeAddressMenu homeAddressMenu;
    private String homeMenuFile;


    public static synchronized HomeAddressMenu getHomeAddressMenu() {
        if (homeAddressMenu == null) {
            homeAddressMenu = new HomeAddressMenu();
        }
        return homeAddressMenu;
    }

    private HomeAddressMenu() {
    }

    public void addHomeMenuFile(String homeAddressTotal) {
        homeMenuFile = homeAddressTotal;
    }
    public String getHomeMenuFile () {
        return homeMenuFile;
    }

}