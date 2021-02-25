package kolibri.example.kolibri.Singleton;

import android.app.Application;

public class MethodPaymentMenu extends Application {
    private static MethodPaymentMenu methodProgamMenu;
    private String methodMenuFile;


    public static synchronized MethodPaymentMenu getMethodPaymentMenu() {
        if (methodProgamMenu == null) {
            methodProgamMenu = new MethodPaymentMenu();
        }
        return methodProgamMenu;
    }

    private MethodPaymentMenu() {

    }

    public void addMethodMenuFile(String methodPaymentTotal) {
        methodMenuFile = methodPaymentTotal;
    }
    public String getMethodMenuFile () {
        return methodMenuFile;
    }

}

