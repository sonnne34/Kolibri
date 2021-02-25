package kolibri.example.kolibri.Singleton;

import android.app.Application;
import android.util.Log;

public class IdNameRestMenu extends Application {
    private static IdNameRestMenu idRestProgamMenu;
    private String idRestMenuFile;


    public static synchronized IdNameRestMenu getIdNameRestMenu() {
        if (idRestProgamMenu == null) {
            idRestProgamMenu = new IdNameRestMenu();
        }
        return idRestProgamMenu;
    }

    private IdNameRestMenu() {

    }

    public void addIdRestMenuFile(String idNameRest) {
        idRestMenuFile = idNameRest;
    }
    public String getIdRestMenuFile () {
        return idRestMenuFile;
    }
    public void showIdRestMenuFile (){
        Log.d("ProgramName","idNameRestSing = " + idRestMenuFile);
    }

}