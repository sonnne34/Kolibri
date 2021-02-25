package kolibri.example.kolibri.Singleton;

import android.app.Application;

public class LevelAddressMenu extends Application {
    private static LevelAddressMenu levelProgramMenu;
    private String levelMenuFile;


    public static synchronized LevelAddressMenu getLevelAddressMenu() {
        if (levelProgramMenu == null) {
            levelProgramMenu = new LevelAddressMenu();
        }
        return levelProgramMenu;
    }

    private LevelAddressMenu() {
    }

    public void addLevelMenuFile(String levelAddressTotal) {
        levelMenuFile = levelAddressTotal;
    }
    public String getLevelMenuFile () {
        return levelMenuFile;
    }

}
