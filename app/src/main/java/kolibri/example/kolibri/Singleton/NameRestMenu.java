package kolibri.example.kolibri.Singleton;

import android.app.Application;
import android.util.Log;

public class NameRestMenu extends Application {
    private static NameRestMenu restProgamMenu;
    private String restMenuFile;


    public static synchronized NameRestMenu getNameRestMenu() {
        if (restProgamMenu == null) {
            restProgamMenu = new NameRestMenu();
        }
        return restProgamMenu;
    }

    private NameRestMenu() {

    }

    public void addRestMenuFile(String nameRest) {
        restMenuFile = nameRest;
    }
    public String getRestMenuFile () {
        return restMenuFile;
    }
    public void showRestMenuFile (){
        Log.d("ProgramName","nameRestSing = " + restMenuFile);
    }

}