package kolibri.example.kolibri.Main2Activity;

public class ItemsCategory {
    String name;
    String picture;

    public ItemsCategory(){

    }

    public ItemsCategory(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
