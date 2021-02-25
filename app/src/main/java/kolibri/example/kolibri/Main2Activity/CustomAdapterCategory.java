package kolibri.example.kolibri.Main2Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kolibri.example.kolibri.MainActivity;

import com.example.kalibri.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterCategory extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private List<ItemsCategory> CustomAdapterList = new ArrayList<>();
    MainActivity mainActivity;
    ItemsCategory category;

    public CustomAdapterCategory (MainActivity mainActivityT,  List<ItemsCategory> itemList){
        for(ItemsCategory i : itemList){

            ItemsCategory itemsAdaperModel = new ItemsCategory();
            itemsAdaperModel.setName(i.name);
//            itemsAdaperModel.setPicture(i.picture);
            CustomAdapterList.add(itemsAdaperModel);

        }

        mainActivity = mainActivityT;
    }
    @Override
    public int getItemCount() {
        return CustomAdapterList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_category_mainactivity, parent ,false);
        holder = new MyViewHolder(view);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {


        final CustomAdapterCategory.MyViewHolder vaultItemHolder = (CustomAdapterCategory.MyViewHolder) holder;

        category = CustomAdapterList.get(position);

        vaultItemHolder.category.setText(category.getName());

//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReferenceFromUrl(category.getPicture());
//        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener <Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                    Log.d("ttt","uri = " + uri );
//                    Picasso.get().load(uri).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(vaultItemHolder.picture);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Toast toast = Toast.makeText(mainActivity,
//                        "Ошибка!", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView category;
//        ImageView picture;


        public MyViewHolder ( View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.text_items);
//            picture = itemView.findViewById(R.id.image_items);

            //анимация альфа канала (прозрачности от 0 до 1)
//            Animation animation = new AlphaAnimation(0.7f, 1.0f);
////длительность анимации 1/10 секунды
//            animation.setDuration(700);
////сдвижка начала анимации (с середины)
//            animation.setStartOffset(50);
////режим повтора - сначала или в обратном порядке
//            animation.setRepeatMode(Animation.REVERSE);
////режим повтора (бесконечно)
//            animation.setRepeatCount(Animation.INFINITE);
////накладываем анимацию на TextView
//            category.startAnimation(animation);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemsCategory name = CustomAdapterList.get(getAdapterPosition());
                    String pos =  name.getName();

                    Log.d("RRR","pos = " + pos);
                    if(pos.equals("Все")){
                        Log.d("RRR", "poss = " + pos );
                        mainActivity.firstSortListCategories(pos);
                        mainActivity.isFiltered = false;
                    }else{

                        mainActivity.sortListCategories(pos);
                        mainActivity.isFiltered = true;
                    }
                }
            });
        }

    }
}

