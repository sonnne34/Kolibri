package kolibri.example.kolibri.Main2Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import kolibri.example.kolibri.Singleton.ImageSingleton;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class FoodListAdapter extends ArrayAdapter<Ticket> {

    private Context mContext;
    private List<Ticket> ticketsList = new ArrayList<>();
    View listItem;
    Ticket currentTicket;

    public FoodListAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes List<Ticket> list) {
        super(context, 0 , list);
        mContext = context;
        ticketsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        currentTicket = ticketsList.get(position);




        Picture();
        Logo();

        TextView textRestorationName = listItem.findViewById(R.id.textRestorationName);
        textRestorationName.setText(currentTicket.getName());

        TextView textTimeDelivery =  listItem.findViewById(R.id.textTimeDelivery);
        long timeDelivery = currentTicket.getTimeDelivery();
        textTimeDelivery.setText(Long.toString(timeDelivery) + " мин. ");

        TextView textTimeWork = listItem.findViewById(R.id.text_time_start_work_item);
        String timeWork = currentTicket.getTimeWork();
        textTimeWork.setText(String.valueOf(timeWork));

        TextView textFromFreeDelivery = listItem.findViewById(R.id.text_sum_free_item);
        long fromFreeDelivery = currentTicket.getFromSumDelivery();
        textFromFreeDelivery.setText("от " + Long.toString(fromFreeDelivery) + " руб.");



        return listItem;
    }



    private void Picture() {

       final ImageView imagePicture = listItem.findViewById(R.id.imagePicture);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(currentTicket.getPicture());


        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener <Uri>() {
            @Override
            public void onSuccess(Uri uri) {


//                if(imagePicture.getDrawable() == null) {
                    Picasso.get().load(uri).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imagePicture);
//                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast toast = Toast.makeText(mContext,
                        "Ошибка!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private void Logo() {


        final ImageView logoPicture = listItem.findViewById(R.id.imageLogo);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(currentTicket.getLogo());


        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener <Uri>() {
            @Override
            public void onSuccess(Uri uri) {

//                if(logoPicture.getDrawable() == null) {
                    Picasso.get().load(uri).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(logoPicture);

//                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast toast = Toast.makeText(mContext,
                        "Ошибка!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }


}