package kolibri.example.kolibri.Singleton;

import android.app.Application;

public class BanknotePaymentMenu extends Application {
    private static BanknotePaymentMenu banknoteProgamMenu;
    private String banknoteMenuFile;


    public static synchronized BanknotePaymentMenu getBanknotePaymentMenu() {
        if (banknoteProgamMenu == null) {
            banknoteProgamMenu = new BanknotePaymentMenu();
        }
        return banknoteProgamMenu;
    }

    private BanknotePaymentMenu() {

    }

    public void addBanknoteMenuFile(String banknotePaymentTotal) {
        banknoteMenuFile = banknotePaymentTotal;
    }
    public String getBanknoteMenuFile () {
        return banknoteMenuFile;
    }

}
