package kolibri.example.kolibri.MenuListActivity;

public class MenuCustomAdapterModel {
    private String CategoryName;
    boolean isHeader;
    private MenuTicket Items;




    public MenuCustomAdapterModel(String categoryName) {
        CategoryName = categoryName;
    }

    public MenuCustomAdapterModel() {

    }


    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public boolean getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(boolean header) {
        isHeader = header;
    }

    public MenuTicket getItems() {
        return Items;
    }

    public void setItems(MenuTicket items) {
        Items = items;
    }
}
