package kolibri.example.kolibri.Main2Activity;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import kolibri.example.kolibri.MainActivity;
import kolibri.example.kolibri.MenuActivity;
import kolibri.example.kolibri.Singleton.IdNameRestMenu;
import kolibri.example.kolibri.Singleton.NamePersonMenu;
import kolibri.example.kolibri.Singleton.NameRestMenu;

import com.example.kalibri.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapterFood extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private List <Ticket> CustomAdapterFoodList = new ArrayList <>();
    MainActivity mainActivity;

    public CustomAdapterFood (MainActivity mainActivityT,  List<Ticket> ticketsList){

        CustomAdapterFoodList = ticketsList;
        mainActivity = mainActivityT;
    }

    @Override
    public int getItemCount() {
        return CustomAdapterFoodList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent ,false);
        holder = new CustomAdapterFood.MyViewHolder(view);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final CustomAdapterFood.MyViewHolder vaultItemHolder = (CustomAdapterFood.MyViewHolder) holder;

        Ticket food = CustomAdapterFoodList.get(position);

        vaultItemHolder.textRestorationName.setText(food.getName());
        vaultItemHolder.textTimeDelivery.setText(food.getTimeDelivery() + " мин.");
        vaultItemHolder.textStartTimeWorkItem.setText("с " + food.getTimeWorkStart());
        vaultItemHolder.textEndTimeWorkItem.setText(" до " + food.getTimeWorkEnd());
        vaultItemHolder.textSumFreeItem.setText("от " + (food.getFromSumDelivery()) + " руб.");
        vaultItemHolder.loadImage(food);
        vaultItemHolder.loadPicture(food);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textRestorationName;
        TextView textTimeDelivery;
        TextView textSumFreeItem;
        TextView textStartTimeWorkItem;
        TextView textEndTimeWorkItem;
        ImageView imagePicture;
        ImageView logo;

        public MyViewHolder (View itemView) {
            super(itemView);

            imagePicture = itemView.findViewById(R.id.imagePicture);
            logo = itemView.findViewById(R.id.imageLogo);
            textRestorationName = itemView.findViewById(R.id.textRestorationName);
            textTimeDelivery = itemView.findViewById(R.id.textTimeDelivery);
            textSumFreeItem = itemView.findViewById(R.id.text_sum_free_item);
            textStartTimeWorkItem = itemView.findViewById(R.id.text_time_start_work_item);
            textEndTimeWorkItem = itemView.findViewById(R.id.text_end_time_work_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Ticket restaurant = CustomAdapterFoodList.get(getAdapterPosition());
                    Intent intent = new Intent(mainActivity, MenuActivity.class);
//                    intent.putExtra("position",  restaurant);

                    Log.d("RENEW", "ticket = " + restaurant.getName());
                    mainActivity.startActivity(intent);

                    String nameRest = restaurant.getName();
                    NameRestMenu.getNameRestMenu().addRestMenuFile(nameRest);
                    NameRestMenu.getNameRestMenu().showRestMenuFile();

                    String idNameRest = restaurant.getId();
                    IdNameRestMenu.getIdNameRestMenu().addIdRestMenuFile(idNameRest);
                    IdNameRestMenu.getIdNameRestMenu().showIdRestMenuFile();

                }
            });
        }

        private void loadPicture(Ticket food) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(food.getPicture());

            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener <Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Picasso.get().load(uri).into(imagePicture);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast toast = Toast.makeText(mainActivity,
                            "Ошибка!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }

        private void loadImage(Ticket food) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(food.getLogo());
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener <Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Picasso.get().load(uri).into(logo);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast toast = Toast.makeText(mainActivity,
                            "Ошибка!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }
}