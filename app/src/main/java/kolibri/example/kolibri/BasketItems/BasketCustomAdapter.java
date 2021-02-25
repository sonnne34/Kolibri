package kolibri.example.kolibri.BasketItems;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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

import kolibri.example.kolibri.BasketActivity;
import kolibri.example.kolibri.MenuListActivity.MenuCustomAdapterModel;
import kolibri.example.kolibri.MenuListActivity.MenuTicket;
import com.example.kalibri.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import kolibri.example.kolibri.Singleton.ProgamMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class BasketCustomAdapter extends  RecyclerView.Adapter <RecyclerView.ViewHolder> implements ItemTouchHelperAdapter{ //свайп

    private List<MenuTicket> BasketCustomAdapterList = new ArrayList <>();
    BasketActivity basketActivity;
    Dialog myDialog;
    TextView count;
    long priceDialog;
    int sums;
    int pz;
    TextView description;
    ImageView pictureDialog;
    MenuCustomAdapterModel namesDish;
    MenuTicket namesDish1;
    TextView namesDialog;

    public BasketCustomAdapter(BasketActivity basketActivityT,  List<MenuTicket> basketRobins){

        BasketCustomAdapterList = basketRobins;

        basketActivity = basketActivityT;

    }
    @Override
    public int getItemCount() {
        return BasketCustomAdapterList.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_basket, parent ,false);
        holder = new MyViewHolder(view);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {


        BasketCustomAdapter.MyViewHolder vaultItemHolder = (BasketCustomAdapter.MyViewHolder) holder;
        vaultItemHolder.namesBasket.setText(BasketCustomAdapterList.get(position).getName());
        vaultItemHolder.counterBasket.setText((Long.toString(BasketCustomAdapterList.get(position).getCountDialog())));
        long sim = BasketCustomAdapterList.get(position).getCost();
        long slon = BasketCustomAdapterList.get(position).getCountDialog();
        long summ = sim * slon;

        vaultItemHolder.countBasket.setText(Long.toString(summ) + " р. ");

    }
    //свайп:
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(BasketCustomAdapterList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(BasketCustomAdapterList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
    //Очень важно вызвать методы notifyItemRemoved() и notifyItemMoved(),
    // чтобы адаптер увидел изменения. Также нужно отметить, что мы меняем позицию
    // элемента каждый раз, когда view-компонент смещается на новый индекс,
    // а не в самом конце перемещения (событие «drop»).
    @Override
    public void onItemDismiss(int position) {
        BasketCustomAdapterList.remove(position);
        notifyItemRemoved(position);
        ProgamMenu.getProgamMenu().notifyTwo();
    }

    //конец свайпа

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView namesBasket;
        TextView countBasket;
        TextView counterBasket;
        ImageView picture;

        public MyViewHolder (View itemView) {
            super(itemView);

            namesBasket = itemView.findViewById(R.id.names_basket);
            countBasket = itemView.findViewById(R.id.count_basket); //итоговая стоимомть блюда (кол-во*цена)
            counterBasket = itemView.findViewById(R.id.counter_basket); //количество штук
            picture = itemView.findViewById(R.id.imageViewPictureDish);

            myDialog = new Dialog(basketActivity,  R.style.CustomDialog);
            myDialog.setContentView(R.layout.dialog_fragment_choice_food);
            myDialog.getWindow().setGravity(Gravity.BOTTOM);
            myDialog.getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);

            // Setting the title and layout for the dialog
            TextView names =  myDialog.findViewById(R.id.names);
            final TextView cost = myDialog.findViewById(R.id.cost);
            Button add =  myDialog.findViewById(R.id.btn_add);
            Button cancel = myDialog.findViewById(R.id.cancel);
            Button plus = myDialog.findViewById(R.id.image_plus);
            Button minus = myDialog.findViewById(R.id.image_minus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    namesDish1 = BasketCustomAdapterList.get(getAdapterPosition());
                    priceDialog =  namesDish1.getCost();
                    MenuTicket file = ProgamMenu.getProgamMenu().proverkaNaNalichie(namesDish1);

                    if (file != null){

                        namesDialog = myDialog.findViewById(R.id.names);
                        TextView cost = myDialog.findViewById(R.id.cost);
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
                                Toast toast = Toast.makeText(basketActivity,
                                        "Ошибка!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                        long sim = file.getCost();
                        long som = file.getCountDialog();
                        long zim = sim * som;
                        cost.setText(String.valueOf(zim));
                        count = myDialog.findViewById(R.id.count);
                        count.setText(String.valueOf(file.getCountDialog()));
                        Log.d("Count", "TRUY = " + file.getCountDialog());
                        myDialog.show();

                    }else {
                        long one = 1;
                        namesDish1 = BasketCustomAdapterList.get(getAdapterPosition());
                        namesDialog = myDialog.findViewById(R.id.names);
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
                                Toast toast = Toast.makeText(basketActivity,
                                        "Ошибка!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });

                        TextView cost = myDialog.findViewById(R.id.cost);
                        namesDialog.setText(namesDish1.getName());
                        cost.setText(String.valueOf(priceDialog));
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
                    TextView sum = myDialog.findViewById(R.id.cost);
                    CharSequence count44 = count.getText();
                    int countFF = Integer.valueOf(count44.toString());
                    sums = (int) (countFF * priceDialog);
                    Log.d("MMM","priceDialog = " + priceDialog);
                    sum.setText(Integer.toString(sums));
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
                    TextView sum = myDialog.findViewById(R.id.cost);
                    CharSequence count44 = count.getText();
                    int countTwo = Integer.valueOf(count44.toString());
                    sums = (int) (countTwo * priceDialog);
                    Log.d("MMM","priceDialog = " + priceDialog);
                    sum.setText(Integer.toString(sums));
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence zz = count.getText(); // получем содержимое обьекта
                    Log.d("Count", "ADD = " + zz);
                    pz = Integer.valueOf(zz.toString()); // преобразовываем в число
                    namesDish1.setCountDialog(pz);
                    ProgamMenu.getProgamMenu().addMenuInfo(namesDish1);
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
    }
}