package kolibri.example.kolibri;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kolibri.example.kolibri.Interface.EventListenerR;
import kolibri.example.kolibri.MenuListActivity.MenuCategoryCustomAdapter;
import kolibri.example.kolibri.Singleton.IdNameRestMenu;
import kolibri.example.kolibri.Singleton.NameRestMenu;
import kolibri.example.kolibri.Singleton.ProgamMenu;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import kolibri.example.kolibri.MenuListActivity.CategoryName;
import kolibri.example.kolibri.MenuListActivity.MenuCustomAdapter;
import kolibri.example.kolibri.MenuListActivity.MenuTicket;
import com.example.kalibri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuActivity  extends AppCompatActivity implements EventListenerR {

    Map<String,MenuTicket> categoryList = new HashMap <>();
    List<CategoryName> categoryNameList = new ArrayList <>();
    List<MenuTicket> basketRobins;
//    Ticket restaurantName;
    private RecyclerView numberList;
    private MenuCustomAdapter numberAdapter;
    MenuCategoryCustomAdapter menuCategoryCustomAdapter;
    private RecyclerView recyclerViewCategoryMenu;
    DatabaseReference myRef;
    CategoryName catName;
    LinearLayoutManager layoutManager;
    RelativeLayout basketRelativeLayout;
    TextView textCostBasket;
    TextView nameRestaurantMenu;
    long result;
    String restName;
    String idRestName;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        nameRestaurantMenu = findViewById(R.id.name_restaurant_menu);
        basketRobins = ProgamMenu.getProgamMenu().getMenuInfo();
        numberList = findViewById(R.id.recycler);
        basketRelativeLayout = findViewById(R.id.basket_relativeLayout);
        basketRelativeLayout.setVisibility(View.GONE);//по умолчанию корзина и сумма заказа невидимы
        ProgamMenu.getProgamMenu().subscribe(this);

        recyclerViewCategoryMenu = findViewById(R.id.recycler_items_category_menuactivity);
        LinearLayoutManager layoutManagerCat = new LinearLayoutManager(this);
        recyclerViewCategoryMenu.setLayoutManager(layoutManagerCat);
        recyclerViewCategoryMenu.setHasFixedSize(true);
        updateCatMenu();

        layoutManager = new LinearLayoutManager(this);
        numberList.setLayoutManager(layoutManager);
        numberList.setHasFixedSize(true);
        Parcelable recyclerViewState;
        recyclerViewState = numberList.getLayoutManager().onSaveInstanceState();
        numberList.getLayoutManager().onRestoreInstanceState(recyclerViewState);

//            Intent intent = getIntent();
//            restaurantName = intent.getParcelableExtra("position");
//            Log.d("topic", " top" + restaurantName);
//            ProgamMenu.getProgamMenu().addRestaranFile(restaurantName);
//            nameRestaurantMenu.setText(restaurantName.getName());
//        Log.d("rer","nameRestaraunt = " + restaurantName.getName());

        restName = NameRestMenu.getNameRestMenu().getRestMenuFile();
        nameRestaurantMenu.setText(restName);
        Log.d("rest", "restName = " + restName );

        idRestName = IdNameRestMenu.getIdNameRestMenu().getIdRestMenuFile();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("RestaurantsMenu/" + restaurantName.getId());
        myRef = database.getReference("RestaurantsMenu/" + idRestName);

        myRef.addValueEventListener(new ValueEventListener() {

            String tmp="";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryNameList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    catName = ds.getValue(CategoryName.class);
                    categoryNameList.add(catName);
                    Map<String,MenuTicket> test = catName.getItems();
                    categoryList = test;
                    tmp+="Категория:" + ds.getKey() +"\nБлюда:\n";
                }
                upDateUI();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("dima", "Failed to read value.", error.toException());
            }
        });
    }

    public void updateRR() {
        textCostBasket = findViewById(R.id.text_cost_basket);
        result = ProgamMenu.getProgamMenu().count();
        textCostBasket.setText(Long.toString(result) + " р. ");
        upDateUI();

        if (result > 0){ //если сумма заказа > 0 то сделать строку с названием ресторана и суммой заказа видимой
            basketRelativeLayout.setVisibility(View.VISIBLE);
        } else {
            basketRelativeLayout.setVisibility(View.GONE);
        }
    }

    public void upDateUI(){
        Parcelable recyclerViewState;
        recyclerViewState = numberList.getLayoutManager().onSaveInstanceState();
        numberAdapter = new MenuCustomAdapter( this, categoryNameList );
        numberList.setAdapter(numberAdapter);
        numberList.getLayoutManager().onRestoreInstanceState(recyclerViewState);
    }

    private void updateCatMenu() {
        menuCategoryCustomAdapter = new MenuCategoryCustomAdapter(this,categoryNameList);
        recyclerViewCategoryMenu.setAdapter(menuCategoryCustomAdapter);
        Log.d("Categ", "catMenu = " + categoryNameList);
    }

    public void BasketRelativOnClick(View v){
        Intent intent = new Intent(this,BasketActivity.class);
        startActivity(intent);
    }

//    public void categoryMenuButtonOnClick(View view, int whith) {
//        CategoryName fff = categoryNameList.get(whith);
//        Log.d("Which","Which = " + fff.getCategoryName());
//        layoutManager.scrollToPositionWithOffset(numberAdapter.scrollToCategory(fff.getCategoryName()), 0);
//    }

    public void categoryRoundButtonOnClick(View v){
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.select_dialog_item);
        for(CategoryName ff : categoryNameList ){
            arrayAdapter.add(ff.getCategoryName());
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("ВЫБЕРИТЕ КАТЕГОРИЮ");
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CategoryName fff = categoryNameList.get(which);
                Log.d("Which","Which = " + fff.getCategoryName());
                layoutManager.scrollToPositionWithOffset(numberAdapter.scrollToCategory(fff.getCategoryName()), 0);
//                numberList.smoothScrollToPosition(numberAdapter.scrollToCategory(fff.getCategoryName()));
            }
        });
        builder.show();
    }
    //Реакция на нажание на аппаратную кнопку "назад" (в список ресторанов):
    @Override
    public void onBackPressed() {
        if (result > 0){ //если корзина наполнена, то открывать диалог
            openQuitDialog();
        } else {
            finish();
        }
    }

    private void openQuitDialog() {
        final androidx.appcompat.app.AlertDialog.Builder quitDialog1 = new androidx.appcompat.app.AlertDialog.Builder(
                MenuActivity.this);
        quitDialog1.setTitle("Вы выходите из ресторана, очистить корзину?");
        quitDialog1.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                basketRobins.clear(); //очищаем корзину
                ProgamMenu.getProgamMenu().notifyTwo();
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        quitDialog1.setNegativeButton("Нет, я только посмотреть", new DialogInterface.OnClickListener() {
        quitDialog1.setNegativeButton("Ой, нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        quitDialog1.show();
    }
}