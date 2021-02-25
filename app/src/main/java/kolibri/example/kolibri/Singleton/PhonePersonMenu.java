package kolibri.example.kolibri.Singleton;

import android.app.Application;

public class PhonePersonMenu extends Application {
    private static PhonePersonMenu phoneProgamMenu;
    private String phoneMenuFile;


    public static synchronized PhonePersonMenu getPhonePersonMenu() {
        if (phoneProgamMenu == null) {
            phoneProgamMenu = new PhonePersonMenu();
        }
        return phoneProgamMenu;
    }

    private PhonePersonMenu() {

    }

    public void addPhoneMenuFile(String phonePersonTotal) {
        phoneMenuFile = phonePersonTotal;
    }
    public String getPhoneMenuFile () {
        return phoneMenuFile;
    }

}
