package kolibri.example.kolibri.MenuListActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalibri.R;

import java.util.ArrayList;
import java.util.List;

import kolibri.example.kolibri.MenuActivity;

public class MenuCategoryCustomAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private List<MenuCustomAdapterModel> adapterModelsCategory = new ArrayList<>();
    MenuActivity menuActivity;

    public MenuCategoryCustomAdapter(MenuActivity activity, List<CategoryName> categoryList){
        for (CategoryName i : categoryList) {

            MenuCustomAdapterModel itemsAdaperModel = new MenuCustomAdapterModel();
            itemsAdaperModel.setCategoryName(i.getCategoryName());
            adapterModelsCategory.add(itemsAdaperModel);

        }
        menuActivity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_category_menuactivity, parent ,false);
        holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MenuCategoryCustomAdapter.MyViewHolder vaultItemHolder = (MyViewHolder) holder;
        vaultItemHolder.categoryMenu.setText(adapterModelsCategory.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return adapterModelsCategory.size();
    }

    public int scrollToCategory(String name){
        Log.d("Which","name = " + name);
        int position = 0;
        for(int i = 0; i < adapterModelsCategory.size(); i++ ){
            MenuCustomAdapterModel element = adapterModelsCategory.get(i);
            if (element.isHeader == true){
                if(element.getCategoryName().equals(name)){
                    position = i;
                }
            }
        }
        return position;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryMenu;

        public MyViewHolder(View view) {
            super(view);
            categoryMenu = view.findViewById(R.id.text_items_category);

        }
    }
}