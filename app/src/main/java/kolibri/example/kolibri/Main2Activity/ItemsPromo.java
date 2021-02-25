package kolibri.example.kolibri.Main2Activity;

public class ItemsPromo  {

    String PictureSale;
    String Name;


    public ItemsPromo(){ }

    public ItemsPromo(String pictureSale, String name) {
        PictureSale = pictureSale;
        Name = name;
    }

    public String getPictureSale() {
        return PictureSale;
    }

    public void setPictureSale(String pictureSale) {
        PictureSale = pictureSale;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
