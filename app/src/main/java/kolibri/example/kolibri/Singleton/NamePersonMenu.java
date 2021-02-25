package kolibri.example.kolibri.Singleton;

import android.app.Application;
import android.util.Log;

public class NamePersonMenu extends Application {
    private static NamePersonMenu nameProgamMenu;
    private String nameMenuFile;


    public static synchronized NamePersonMenu getNamePersonMenu() {
        if (nameProgamMenu == null) {
            nameProgamMenu = new NamePersonMenu();
        }
        return nameProgamMenu;
    }

    private NamePersonMenu() {

    }

    public void addNameMenuFile(String namePersonTotal) {
        nameMenuFile = namePersonTotal;
    }
    public String getNameMenuFile () {
        return nameMenuFile;
    }
    public void showNameMenuFile (){
        Log.d("ProgramName","namePerson = " + nameMenuFile);
    }

}
