package kolibri.example.kolibri.Main2Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalibri.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kolibri.example.kolibri.MainActivity;
import kolibri.example.kolibri.MenuActivity;
import kolibri.example.kolibri.MenuListActivity.MenuTicket;
import kolibri.example.kolibri.Singleton.ProgamMenu;

public class CustomAdapterPromo extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private List<ItemsPromo> CustomAdapterListPromo = new ArrayList<>();
    MainActivity activity;
    ItemsPromo promo;

    public CustomAdapterPromo (MainActivity activity1, List<ItemsPromo> itemPromo){
        for(ItemsPromo i : itemPromo){

            ItemsPromo itemListPromo = new ItemsPromo();
            itemListPromo.setPictureSale(i.PictureSale);
            itemListPromo.setName(i.Name);
            Log.d("ddd","AdapterPromo = " + i.getName());
            CustomAdapterListPromo.add(itemListPromo);
        }

        activity = activity1;
    }
    @Override
    public int getItemCount() {
        return CustomAdapterListPromo.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_promo_mainactivity, parent ,false);
        holder = new MyViewHolder(view);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final CustomAdapterPromo.MyViewHolder vaultItemHolder = (CustomAdapterPromo.MyViewHolder) holder;

        promo = (ItemsPromo) CustomAdapterListPromo.get(position);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(promo.getPictureSale());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("ttt","uri = " + uri );
                Picasso.get().load(uri).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(vaultItemHolder.promoView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
//                Toast toast = Toast.makeText(mainActivity,
//                        "Ошибка!", Toast.LENGTH_SHORT);
//                toast.show();
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView promoView;

        public MyViewHolder ( View itemView) {
            super(itemView);

            promoView = itemView.findViewById(R.id.image_items_promo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    promo = CustomAdapterListPromo.get(getAdapterPosition());
                    Log.d("name","position = " + promo.getName());
                    String namesPos = promo.getName();

                    Log.d("name","namePos = " + namesPos);
                    List<Ticket> nameRestoran =  ProgamMenu.getProgamMenu().getRestaranFile();

                    Log.d("name","nameRestoran = " + nameRestoran );

                    Ticket ff = null;

                    for (Ticket i : nameRestoran){
                        Log.d("name", "i = " + i.getId());

                        if(namesPos.equals(i.getId())){

                            ff = i;
                        }
                    }
                    if(ff != null) {
                        Intent intent = new Intent(activity, MenuActivity.class);
                        intent.putExtra("position", ff);
                        activity.startActivity(intent);
                    }
                }
            });
        }
    }
}

