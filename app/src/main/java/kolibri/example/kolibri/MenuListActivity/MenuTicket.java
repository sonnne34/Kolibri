package kolibri.example.kolibri.MenuListActivity;


public class MenuTicket{
    private long Cost;
    private String Description;
    private String Name;
    private String Picture;
    private long CountDialog;
    private String PictureSale;


    public MenuTicket() {

    }



    public MenuTicket(long cost,long countDialog,String description, String name, String picture, String pictureSale) {
        Cost = cost;
        Description = description;
        Name = name;
        Picture = picture;
        PictureSale = pictureSale;
        CountDialog = countDialog;
    }

    public String getPictureSale() {
        return PictureSale;
    }

    public void setPictureSale(String pictureSale) {
        PictureSale = pictureSale;
    }

    public long getCountDialog(){
        return CountDialog;
    }
    public void setCountDialog(long countDialog){
        CountDialog = countDialog;
    }

    public long getCost() {

        return Cost;
    }

    public void setCost(long cost) {
        Cost = cost;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }
}

