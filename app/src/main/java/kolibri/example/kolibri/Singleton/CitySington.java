package kolibri.example.kolibri.Singleton;

import android.app.Application;
import android.util.Log;

public class CitySington extends Application {
    private static CitySington citySington;
    private String cityFile;
    private String cityFileENG;


    public static synchronized CitySington getCitySington() {
        if (citySington == null) {
            citySington = new CitySington();
        }
        return citySington;
    }

    private CitySington() {

    }

    public void addCityFile(String cityFileTwo) {
        cityFile = cityFileTwo;
    }

    public String getCityFile() {
        return cityFile;
    }

    public void addCityEng(String cityFileTwo) {
        cityFileENG = cityFileTwo;
    }

    public String getCityFileEng() {
        return cityFileENG;
    }

    public void showFile() {
        Log.d("ProgramName", "CityName = " + cityFile);
    }

}
