package kolibri.example.kolibri.Models;

public class CityModels {
    private String  rus;
    private String  eng;

    public CityModels(String rus, String eng) {
        this.rus = rus;
        this.eng = eng;

    }

    public CityModels() {

    }
    public String getRus() {

        return rus;
    }

    public void setRus(String rus) {
        this.rus = rus;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }


}
