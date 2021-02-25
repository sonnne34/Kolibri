package kolibri.example.kolibri.MenuListActivity;

import java.util.HashMap;
import java.util.Map;

public class CategoryName {

    public boolean isHeader;
    private String CategoryName;
    private Map<String,MenuTicket> Items = new HashMap <>();



    public CategoryName() {

    }

    public CategoryName(String categoryName, Map<String,MenuTicket> items) {
        CategoryName = categoryName;
        Items = items;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public Map <String,MenuTicket> getItems() {
        return Items;
    }

    public void setItems(Map<String,MenuTicket> items) {
        Items = items;
    }




//    @Override
//    public String toString() {
//        String tmp="";
//        for (Map.Entry<String,MenuTicket> i : Items.entrySet()){
//            tmp+="Имя = "+ i.getValue().getName()+"\n"+"Цена = " + i.getValue().getCost() + "\n" +"Описание:\n"+i.getValue().getDescription()+"\n";
//        }
//        return tmp;
//    }
}
