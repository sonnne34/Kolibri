package kolibri.example.kolibri;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kolibri.example.kolibri.Main2Activity.CustomAdapterCategory;
import kolibri.example.kolibri.Main2Activity.CustomAdapterFood;
import kolibri.example.kolibri.Main2Activity.CustomAdapterPromo;
import kolibri.example.kolibri.Main2Activity.ItemsCategory;
import kolibri.example.kolibri.Main2Activity.ItemsPromo;
import kolibri.example.kolibri.Main2Activity.OptionActivity;
import kolibri.example.kolibri.Main2Activity.Ticket;
import kolibri.example.kolibri.Singleton.CitySington;
import kolibri.example.kolibri.Singleton.ProgamMenu;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.kalibri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRestaurant;
    private RecyclerView recyclerViewPromo;
    private RecyclerView recyclerViewCategory;
    CustomAdapterFood rAdapter;
    CustomAdapterPromo customAdapterPromo;
    CustomAdapterCategory customAdapterCategory;
    private List<Ticket> ticketsList = new ArrayList<>();
    private List<ItemsPromo> itemPromo = new ArrayList<>();
    private List<ItemsCategory>  itemList = new ArrayList<>();
    ArrayList<Ticket> newList;
    List<Ticket> name;
    final String SAVED_TEXT = "saved_text";
    final String SAVED_TEXT_ENG = "saved_text_eng";
    long summ;
    String saveText;
    String saveTextEnglish;
    SharedPreferences sPref;
    public Boolean isFiltered = false;
    private DatabaseReference mCategoryRef;
    RelativeLayout registrationBasketOrder;
    Button cityButton;
    TextView textCostBasket;
    TextView scoreRestaurantOrder;
    TextView nameRestorauntOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityButton = findViewById(R.id.citiesButton);
        cityButton.setTextColor(Color.parseColor("#000000"));
        cityButton.setText("Выбери Город");

        recyclerViewCategory = findViewById(R.id.recycler_items_category_mainactivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setHasFixedSize(true);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        int itemViewType = 0;
        recyclerViewCategory.getRecycledViewPool().setMaxRecycledViews(itemViewType, 0);

        recyclerViewPromo = findViewById(R.id.recycler_items_promo_mainactivity);
        LinearLayoutManager layoutManagerPromo = new LinearLayoutManager(this);
        recyclerViewPromo.setLayoutManager(layoutManagerPromo);
        recyclerViewPromo.setHasFixedSize(true);
        recyclerViewPromo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPromo.getRecycledViewPool().setMaxRecycledViews(itemViewType, 0);

        recyclerViewRestaurant = findViewById(R.id.movies_list);
        LinearLayoutManager layoutManagerRest = new LinearLayoutManager(this);
        recyclerViewRestaurant.setLayoutManager(layoutManagerRest);
        recyclerViewRestaurant.setHasFixedSize(true);
        recyclerViewRestaurant.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewRestaurant.getRecycledViewPool().setMaxRecycledViews(itemViewType, 0);

        registrationBasketOrder = findViewById(R.id.registration_basket_order); //строка с названием ресторана и счётом
        registrationBasketOrder.setVisibility(View.GONE);//по умолчанию она невидима
        textCostBasket = findViewById(R.id.text_cost_basket);
        scoreRestaurantOrder = findViewById(R.id.score_restaurant_order); //сумма заказа
        summ = ProgamMenu.getProgamMenu().count();
        scoreRestaurantOrder.setText(Long.toString(summ)+ " р.");
        nameRestorauntOrder = findViewById(R.id.name_restaurant_order); //название ресторана с заказом
        name = ProgamMenu.getProgamMenu().getRestaranFile();
        String rrr = null;
        for(Ticket i : name){
            rrr = i.getName();
        }
        nameRestorauntOrder.setText(rrr);
//        Log.d("nameRest", "name =" + nameRestorauntOrder);

//        if (summ > 0) //если сумма заказа > 0 то сделать строку с названием ресторана и суммой заказа видимой
//        {registrationBasketOrder.setVisibility(View.VISIBLE);}

        loadTextCity();
        checkCity();
        categoryTop();
        chooseRestaurant();
        promoSale();
    }

    // сохранение текста "город"
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        String name = data.getStringExtra("pos");
        String nameEng = data.getStringExtra("posEng");
        cityButton.setText(name);
        saveText(name, nameEng);
        chooseRestaurant();
        promoSale();
        CitySington.getCitySington().addCityFile(name);
        CitySington.getCitySington().addCityEng(nameEng);
        CitySington.getCitySington().showFile();
    }

    // Загрузка данных при запуске приложения
    private void loadTextCity() {
        sPref = getPreferences(MODE_PRIVATE);
        saveText = sPref.getString(SAVED_TEXT,"");
        saveTextEnglish = sPref.getString(SAVED_TEXT_ENG,"");
        cityButton.setText(saveText);
        CitySington.getCitySington().addCityFile(saveText);
        CitySington.getCitySington().addCityEng(saveTextEnglish);
        CitySington.getCitySington().showFile();
//        Toast.makeText(MainActivity.this,"Text loaded",Toast.LENGTH_SHORT).show();
    }

    public void firstSortListCategories(String text){
        CustomAdapterFood Adapter = new CustomAdapterFood(this, ticketsList);
        recyclerViewRestaurant.setAdapter(Adapter);
    }

    public void sortListCategories(String text) {
        newList = getSortedNamess(text);
        CustomAdapterFood Adapter = new CustomAdapterFood(this, newList);
        recyclerViewRestaurant.setAdapter(Adapter);
    }

    public ArrayList<Ticket> getSortedNamess(String text) {
        ArrayList<Ticket> list = new ArrayList<Ticket>();
        for (Ticket x : ticketsList) {
            for (String y : x.getCategories()) {
                Log.w("y = ", y);
                Log.w("text = ", text);
                if (y.equals(text)) {
                    list.add(x);
                    Log.w("dima", "Да");
                }
            }
        }
        return list;
    }

    public void sortList(String s) {
        ArrayList<Ticket> newList = getSortedNames(s);
        CustomAdapterFood Adapter = new CustomAdapterFood(this, newList);
        recyclerViewRestaurant.setAdapter(Adapter);
    }

    // сортировака списка "категории"
    public ArrayList<Ticket> getSortedNames(String text) {
        ArrayList<Ticket> list = new ArrayList<Ticket>();
        for (Ticket x : ticketsList) {
            if (x.getName().contains(text)) {
                list.add(x);
                Log.w("dima", "Японская");
            }
        }
        return list;
    }

    //обновление адаптера "рестораны"
    public void update() {
        rAdapter = new CustomAdapterFood(this, ticketsList);
        recyclerViewRestaurant.setAdapter(rAdapter);
    }

    private void updatePromo() {
        customAdapterPromo = new CustomAdapterPromo(this, itemPromo);
        recyclerViewPromo.setAdapter(customAdapterPromo);
    }

    //кнопки

    public void citiesButton(View v) {
        Intent intent = new Intent(this, CitiesActivity.class);
        startActivityForResult(intent, 1);
    }

    // сохранение текста "город"
    private void saveText(String rus, String eng){
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT,rus);
        ed.putString(SAVED_TEXT,rus).toString();
        ed.putString(SAVED_TEXT_ENG,eng);

        ed.commit();
    }
    private void checkCity() {
        Log.d("ProgramName","SAVED_TEXT = " + saveText);
        if(saveText == ""){
            Intent intent = new Intent(MainActivity.this, CitiesActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void categoryTop(){
// Загрузка информации  из фаербейс в раздел категории
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mCategoryRef = database.getReference("Category");
        mCategoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot dss : dataSnapshot.getChildren()){
                    Map<String,String> items = (Map <String, String>) dss.getValue();
                    ItemsCategory category = new ItemsCategory();
                    category.setName(items.get("name"));
                    category.setPicture(items.get("picture"));
                    itemList.add(category);
                }
                updateCat();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    private void updateCat() {
        customAdapterCategory = new CustomAdapterCategory(this,itemList);
        recyclerViewCategory.setAdapter(customAdapterCategory);
    }

    private void chooseRestaurant() {

        final String saveTextEng = sPref.getString(SAVED_TEXT_ENG,"");
        if(saveTextEng == ""){
        }else {
            Log.d("Gorod", "restoran2 = " + saveTextEng);
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("RestaurantsInCities/" + saveTextEng);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    ticketsList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Ticket value = ds.getValue(Ticket.class);
                        ticketsList.add(value);
                        ProgamMenu.getProgamMenu().addRestaranFile(value);
                        Log.d("name", "tiket = " + value);
                    }
                    update();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w("dima", "Failed to read value.", error.toException());
                }
            });
        }
    }

    private void promoSale(){

        final String saveTextEng = sPref.getString(SAVED_TEXT_ENG,"");
        if(saveTextEng == ""){
        }else {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            mCategoryRef = database.getReference("Sale/" + saveTextEng);
            mCategoryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    itemPromo.clear();
                    for (DataSnapshot dss : dataSnapshot.getChildren()) {
                        Log.d("ddd", "SAL = " + dss);
                        Map <String, String> items = (Map <String, String>) dss.getValue();
                        Log.d("ddd", "SALEDDS = " + items);
                        ItemsPromo promo = new ItemsPromo();
                        promo.setPictureSale(items.get("PictureSale"));
                        promo.setName(items.get("Name"));
                        itemPromo.add(promo);
                        Log.d("ddd", "SALES = " + promo);
                    }
                    updatePromo();
                }
                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        }
    }

    //кнопка - переход в корзину
    public void BasketRelativOnClickMain(View v){
        Intent intent = new Intent(this,BasketActivity.class);
        startActivity(intent);
    }
    //Реакция на нажание на аппаратную кнопку "назад" (выход из приложения):
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                MainActivity.this);
        quitDialog.setTitle("Выход");
        quitDialog.setTitle("Вы уверенны?");
        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDestroy();
                finish();
            }
        });
        quitDialog.setNegativeButton("Ой, нет!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        quitDialog.show();
    }

    public void openOption(View view) { //открыть опции
        Intent intent1 = new Intent(MainActivity.this, OptionActivity.class);
        startActivity(intent1);
    }
}