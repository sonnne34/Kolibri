package kolibri.example.kolibri.Registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import kolibri.example.kolibri.Main2Activity.Ticket;
import kolibri.example.kolibri.MainActivity;
import kolibri.example.kolibri.MenuActivity;
import kolibri.example.kolibri.MenuListActivity.MenuTicket;
import com.example.kalibri.R;
import kolibri.example.kolibri.Singleton.ApartmentAddressMenu;
import kolibri.example.kolibri.Singleton.BanknotePaymentMenu;
import kolibri.example.kolibri.Singleton.CitySington;
import kolibri.example.kolibri.Singleton.CommentPersonMenu;
import kolibri.example.kolibri.Singleton.EntranceAddressMenu;
import kolibri.example.kolibri.Singleton.HomeAddressMenu;
import kolibri.example.kolibri.Singleton.LevelAddressMenu;
import kolibri.example.kolibri.Singleton.MethodPaymentMenu;
import kolibri.example.kolibri.Singleton.NamePersonMenu;
import kolibri.example.kolibri.Singleton.PhonePersonMenu;
import kolibri.example.kolibri.Singleton.ProgamMenu;
import kolibri.example.kolibri.Singleton.StreetAddressMenu;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.List;

public class TotalActivity extends AppCompatActivity {

    RecyclerView totalList;
    TotalCustomAdapter totalAdapter;
    List<MenuTicket> purchasesTotal;
    long sumPersonTotal;
    TextView summTotalResult, namePersonTotal, phonePersonTotal, streetAddressTotal, homeAddressTotal;
    TextView  entranceAddressTotal, apartmentAddressTotal, levelAddressTotal, banknotePaymentText;
    TextView banknotePaymentTotal, methodPaymentTotal, city, commentTotal, sendTotal;
    String namePerson, phonePerson, streetAddress, homeAddress, entranceAddress, apartmentAddress;
    String levelAddress, banknotePayment, methodPayment, cityTwo, cityENG, comment;
    List<Ticket> restaranName;
    Ticket restToranModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_total);

        totalList = findViewById(R.id.total_recyclerView);
        sendTotal = findViewById(R.id.send_total);
        summTotalResult = findViewById(R.id.sum_person_total);
        namePersonTotal = findViewById(R.id.name_person_order);
        phonePersonTotal = findViewById(R.id.phone_person_order);
        commentTotal = findViewById(R.id.comment_order);
        streetAddressTotal = findViewById(R.id.street_total);
        homeAddressTotal = findViewById(R.id.home_total);
        entranceAddressTotal = findViewById(R.id.entrance_total);
        apartmentAddressTotal = findViewById(R.id.apartment_total);
        levelAddressTotal = findViewById(R.id.level_total);
        banknotePaymentText = findViewById(R.id.banknote_payment_total);
        banknotePaymentTotal = findViewById(R.id.banknote_payment_person_total);
        methodPaymentTotal = findViewById(R.id.method_payment_person_total);
        city = findViewById(R.id.cities_total);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        totalList.setLayoutManager(layoutManager);
        totalList.setHasFixedSize(true);
        restaranName = ProgamMenu.getProgamMenu().getRestaranFile();
        purchasesTotal = ProgamMenu.getProgamMenu().getMenuInfo();
        totalAdapter = new TotalCustomAdapter(this, purchasesTotal);
        totalList.setAdapter(totalAdapter);
        sumPersonTotal = ProgamMenu.getProgamMenu().count();
        summTotalResult.setText("Итого: " + Long.toString(sumPersonTotal) + " р. ");
        cityTwo = CitySington.getCitySington().getCityFile();
        city.setText(cityTwo);
        cityENG = CitySington.getCitySington().getCityFileEng();
        for (Ticket i : restaranName){
            restToranModel = i;
        }

        namePerson = NamePersonMenu.getNamePersonMenu().getNameMenuFile();
        namePersonTotal.setText("Заказчик: " + namePerson);

        phonePerson = PhonePersonMenu.getPhonePersonMenu().getPhoneMenuFile();
        phonePersonTotal.setText("Телефон: " + phonePerson);

        comment = CommentPersonMenu.getCommentPersonMenu().getCommentMenuFile();
        commentTotal.setText("Комментарий: " + comment);
        if (comment.length() == 0)
        { commentTotal.setVisibility(View.GONE);}

        streetAddress = StreetAddressMenu.getStreetAddressMenu().getStreetMenuFile();
        streetAddressTotal.setText(", ул. " + streetAddress);

        homeAddress = HomeAddressMenu.getHomeAddressMenu().getHomeMenuFile();
        homeAddressTotal.setText(", д. " + homeAddress);

        entranceAddress = EntranceAddressMenu.getEntranceAddressMenu().getEntranceMenuFile();
        entranceAddressTotal.setText(", под. " + entranceAddress);
        if (entranceAddress.length() == 0)
        { entranceAddressTotal.setVisibility(View.GONE);}

        apartmentAddress = ApartmentAddressMenu.getApartmentAddressMenu().getApartmentMenuFile();
        apartmentAddressTotal.setText(" кв./оф. " + apartmentAddress);
        if (apartmentAddress.length() == 0)
        { apartmentAddressTotal.setVisibility(View.GONE);}

        levelAddress = LevelAddressMenu.getLevelAddressMenu().getLevelMenuFile();
        levelAddressTotal.setText(", эт. " + levelAddress);
        if (levelAddress.length() == 0)
        { levelAddressTotal.setVisibility(View.GONE);}

        banknotePayment = BanknotePaymentMenu.getBanknotePaymentMenu().getBanknoteMenuFile();
        banknotePaymentTotal.setText(banknotePayment);

        methodPayment = MethodPaymentMenu.getMethodPaymentMenu().getMethodMenuFile();
        methodPaymentTotal.setText(methodPayment);
        //если заказчик оставил метод оплаты по умолчанию и не нажимал кнопки, то вывести значение "наличными"
        if (methodPaymentTotal.getText().length() ==0){
            methodPaymentTotal.setText("Наличными") ;
        }
        //если метод оплаты выбран Картой то спрятать "Сдача с"
        if (methodPaymentTotal.getText() == "Картой"){
            banknotePaymentTotal.setVisibility(View.GONE);
            banknotePaymentText.setVisibility(View.GONE);
        }
    }

    public void send(View v) throws IOException {
        v.setClickable(false);
        sendTotal.setTextColor(ContextCompat.getColor(this, R.color.colorDark));

        String pay = methodPaymentTotal.getText().toString();
        String send = "";
        send = send + "Заказ: \n";

        for (MenuTicket i : purchasesTotal) {
            send = (send + i.getCountDialog() + "шт" + " " + i.getName() + " " + i.getCost() + "р") + "\n" ;
        }
        send = send + "\n" + "Итого: " + Long.toString(sumPersonTotal) + " р. \n ";
        send = send + "Информация о заказе: \n" + namePerson + "\n" + phonePerson + "\n";
        send = send + "Адрес доставки: \n" + cityTwo + ", ул. " + streetAddress + ", д. " + homeAddress + ", кв./оф. " + apartmentAddress + ", под. " + entranceAddress + ", эт. " + levelAddress + "\n";
        send = send + "Способы оплаты: \n" + pay + "\n" + "Сдача с: \n" + banknotePayment + "\n";
        send = send + "Комментарий к заказу: \n" + comment;

        Log.d("OOO", "send \n" + send);

        final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
        String order = Uri.encode(send,ALLOWED_URI_CHARS);
        Log.d("RRRR","mail = " + order);

        String base = "https://us-central1-kalibri-845e2.cloudfunctions.net/addOrder";
        base += "?money=" + Long.toString(sumPersonTotal); //деньги
        base += "&city=" + cityENG; //город
        base += "&restaurant=" + restToranModel.getId(); //название ресторана
        base += "&tel=" + phonePerson; //телефон
        base += "&order=" + order ; //заказ

        Log.d("RRRRTest","Base = " + base);

        OkHttpClient client = new OkHttpClient();
        String url = base;
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String myResponse = response.body().string();
                    Log.d("Spot","myResponse = \n " + myResponse );
                }
            }
        });

        AlertDialog.Builder builder2 = new AlertDialog.Builder(TotalActivity.this);
        builder2.setTitle("Ваш заказ отправлен")
                .setMessage("В течении 5 минут оператор свяжется с Вами")
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(TotalActivity.this, MainActivity.class);
                                startActivity(intent);
                                purchasesTotal.clear(); //очищаем корзину
                                ProgamMenu.getProgamMenu().notifyTwo();
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder2.create();
        alert.show();
    }

    public void menu_total(View view) {
        Intent intent = new Intent(TotalActivity.this, MainActivity.class);
        startActivity(intent);
        purchasesTotal.clear(); //очищаем корзину
        ProgamMenu.getProgamMenu().notifyTwo();
    }
}