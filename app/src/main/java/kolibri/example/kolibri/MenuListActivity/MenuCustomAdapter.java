package kolibri.example.kolibri.MenuListActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kolibri.example.kolibri.MenuActivity;
import kolibri.example.kolibri.Singleton.ImageSingleton;
import kolibri.example.kolibri.Singleton.ProgamMenu;

import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalibri.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class MenuCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<MenuCustomAdapterModel> adapterModels = new ArrayList <>();
    private static final int LAYOUT_HEADER= 0;
    private static final int LAYOUT_CHILD= 1;
    MenuActivity activity;
    Dialog myDialog;
    TextView count;
    long priceDialog;
    int sums;
    int pz;
    TextView namesDialog;
    TextView description;
    ImageView pictureDialog;
    MenuCustomAdapterModel namesDish;

    public MenuCustomAdapter(MenuActivity activity1, List<CategoryName> categoryNameList){
        for (CategoryName i : categoryNameList) {

            MenuCustomAdapterModel itemsAdaperModel = new MenuCustomAdapterModel();
            itemsAdaperModel.setCategoryName(i.getCategoryName());
            itemsAdaperModel.setIsHeader(true);
            adapterModels.add(itemsAdaperModel);
            for (Map.Entry<String,MenuTicket> f : i.getItems().entrySet()){
                MenuCustomAdapterModel itemsAdaperModelChild = new MenuCustomAdapterModel();
                itemsAdaperModelChild.setItems(f.getValue());
                itemsAdaperModelChild.setIsHeader(false);
                adapterModels.add(itemsAdaperModelChild);
            }
        }
        activity = activity1;
    }

    @Override
    public int getItemCount() {
        return adapterModels.size();
    }

    public int scrollToCategory(String name){
        Log.d("Which","name = " + name);
        int position = 0;
        for(int i = 0; i < adapterModels.size(); i++ ){
            MenuCustomAdapterModel element = adapterModels.get(i);
            if (element.isHeader == true){
                if(element.getCategoryName().equals(name)){
                    position = i;
                }
            }
        }
        return position;
    }

    @Override
    public int getItemViewType(int position)
    {
        if(adapterModels.get(position).getIsHeader() == true) {
            return LAYOUT_HEADER;
        }else
            return LAYOUT_CHILD;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if(viewType==LAYOUT_HEADER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header_category_menu, parent ,false);
            holder = new MyViewHolderHeader(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menuitem, parent ,false);
            holder = new MyViewHolderChild(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if(holder.getItemViewType() == LAYOUT_HEADER) {
            MyViewHolderHeader vaultItemHolder = (MyViewHolderHeader) holder;
            vaultItemHolder.categoryHeader.setText(adapterModels.get(position).getCategoryName());
        } else {
            final MyViewHolderChild vaultItemHolder = (MyViewHolderChild) holder;
            vaultItemHolder.textViewDishName.setText(adapterModels.get(position).getItems().getName());
            String simTwo = adapterModels.get(position).getItems().getDescription();
            vaultItemHolder.textViewGoodsDescription.setText(String.valueOf(simTwo));
            long sim = adapterModels.get(position).getItems().getCost();
            vaultItemHolder.textViewCost.setText(Long.toString(sim) + " р.");
            Boolean rr = ProgamMenu.getProgamMenu().checkingThelist(adapterModels.get(position).getItems());
            if (rr == true) {
                vaultItemHolder.checkBoxItem.setBackgroundResource(R.drawable.okrugl_checkbox);
            }else{
                vaultItemHolder.checkBoxItem.setBackgroundResource(R.color.transparent);
            }
            vaultItemHolder.LoadImage();
        }
    }

    class MyViewHolderHeader extends RecyclerView.ViewHolder{
        TextView categoryHeader;
        public MyViewHolderHeader(View itemView) {
            super(itemView);
            categoryHeader = itemView.findViewById(R.id.tvHeader);
        }
    }

    class MyViewHolderChild extends RecyclerView.ViewHolder {
        TextView textViewDishName;
        TextView textViewGoodsDescription;
        TextView textViewCost;
        TextView checkBoxItem;
        ImageView picture;

        public MyViewHolderChild(View itemView) {
            super(itemView);

            textViewDishName = itemView.findViewById(R.id.textViewDishName);
            textViewGoodsDescription = itemView.findViewById(R.id.textViewGoodsDescription);
            textViewCost = itemView.findViewById(R.id.textViewCost);
            checkBoxItem = itemView.findViewById(R.id.checkbox_items);

            picture = itemView.findViewById(R.id.imageViewPictureDish);
            myDialog = new Dialog(activity, R.style.CustomDialog);
            myDialog.setContentView(R.layout.dialog_fragment_choice_food);
            myDialog.getWindow().setGravity(Gravity.BOTTOM);
            myDialog.getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);

            Button add = myDialog.findViewById(R.id.btn_add);
            Button cancel = myDialog.findViewById(R.id.cancel);
            Button plus = myDialog.findViewById(R.id.image_plus);
            Button minus = myDialog.findViewById(R.id.image_minus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    namesDish = adapterModels.get(getAdapterPosition());
                    priceDialog =  namesDish.getItems().getCost();

                    final MenuTicket file = ProgamMenu.getProgamMenu().proverkaNaNalichie(namesDish.getItems());

                    if (file != null){

                        namesDialog = myDialog.findViewById(R.id.names);
                        namesDialog.setText(file.getName());
                        description = myDialog.findViewById(R.id.textViewGoodsDescriptionDialog);
                        description.setText(file.getDescription());

                        pictureDialog = myDialog.findViewById(R.id.imageViewPictureDishDialog);

                        FirebaseStorage storageTwo = FirebaseStorage.getInstance();
                        StorageReference storageRefTwo = storageTwo.getReferenceFromUrl(file.getPicture());
                        storageRefTwo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener <Uri>() {

                            @Override
                            public void onSuccess(Uri uri) {

                                Picasso.get().load(uri).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE,
                                        MemoryPolicy.NO_STORE).into(pictureDialog);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast toast = Toast.makeText(activity,
                                        "Ошибка!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });


                        TextView cost = myDialog.findViewById(R.id.cost);
                        long sim = file.getCost();
                        long som = file.getCountDialog();
                        long zim = sim * som;
                        cost.setText(String.valueOf(zim) + "р.");
                        count = myDialog.findViewById(R.id.count);
                        count.setText(String.valueOf(file.getCountDialog()));
                        Log.d("Count", "TRUY = " + file.getCountDialog());
                        myDialog.show();

                    }else {
                        long one = 1;
                        namesDish = adapterModels.get(getAdapterPosition());
                        namesDialog = myDialog.findViewById(R.id.names);
                        namesDialog.setText(namesDish.getItems().getName());
                        description = myDialog.findViewById(R.id.textViewGoodsDescriptionDialog);
                        description.setText(namesDish.getItems().getDescription());

                        pictureDialog = myDialog.findViewById(R.id.imageViewPictureDishDialog);

                        FirebaseStorage storageTwo = FirebaseStorage.getInstance();
                        StorageReference storageRefTwo = storageTwo.getReferenceFromUrl(namesDish.getItems().getPicture());
                        storageRefTwo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener <Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Picasso.get().load(uri).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE,
                                        MemoryPolicy.NO_STORE).into(pictureDialog);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast toast = Toast.makeText(activity,
                                        "Ошибка!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });

                        TextView cost = myDialog.findViewById(R.id.cost);
                        cost.setText(String.valueOf(priceDialog) + "р.");
                        count = myDialog.findViewById(R.id.count);
                        count.setText(String.valueOf(one));
                        Log.d("Count","ELSE = " + count );
                        myDialog.show();
                    }
                }
            });

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence zz = count.getText(); // получем содержимое обьекта
                    Log.d("Count", "PLUS = " + zz);
                    pz = Integer.valueOf(zz.toString()); // преобразовываем в число
                    pz++; // прибавляем 1
                    count.setText(Integer.toString(pz)); // преобразовываем в строку и возвращаем в обьект "count"
                    TextView sum = (TextView) myDialog.findViewById(R.id.cost);
                    CharSequence count44 = count.getText();
                    int countFF = Integer.valueOf(count44.toString());
                    sums = (int) (countFF * priceDialog);
                    Log.d("MMM","priceDialog = " + priceDialog);
                    sum.setText(Integer.toString(sums) + "р.");
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CharSequence zz = count.getText(); // получем содержимое обьекта
                    Log.d("Count", "Minus = " + zz);
                    pz = Integer.valueOf(zz.toString()); // преобразовываем в число
                    if(pz>=1){ //если pz больше или равно 1
                        pz--; // убавляем 1
                    }else{
                        pz=0; //если pz равно 0 то возвращаем значение 0//
                    }
                    count.setText(Integer.toString(pz)); // преобразовываем в строку и возвращаем в обьект "count"
                    TextView sum = (TextView) myDialog.findViewById(R.id.cost);
                    CharSequence count44 = count.getText();
                    int countTwo = Integer.valueOf(count44.toString());
                    sums = (int) (countTwo * priceDialog);
                    Log.d("MMM","priceDialog = " + priceDialog);
                    sum.setText(Integer.toString(sums) + "р.");
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence zz = count.getText(); // получем содержимое обьекта
                    Log.d("Count", "ADD = " + zz);
                    pz = Integer.valueOf(zz.toString()); // преобразовываем в число
                    namesDish.getItems().setCountDialog(pz);
                    ProgamMenu.getProgamMenu().addMenuInfo(namesDish.getItems());
                    ProgamMenu.getProgamMenu().notifyTwo();
                    myDialog.cancel();

                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.cancel();
                }
            });
        }

        private void LoadImage()  {

            MenuCustomAdapterModel menuPosition = adapterModels.get(getAdapterPosition());

            FirebaseStorage storageTwo = FirebaseStorage.getInstance();

            StorageReference storageRefTwo = storageTwo.getReferenceFromUrl(menuPosition.getItems().getPicture());

            storageRefTwo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener <Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Picasso.get().load(uri).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE,
                            MemoryPolicy.NO_STORE).into(picture);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast toast = Toast.makeText(activity,
                            "Ошибка!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }
}