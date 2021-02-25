package kolibri.example.kolibri;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import kolibri.example.kolibri.BasketItems.BasketCustomAdapter;
import kolibri.example.kolibri.BasketItems.SimpleItemTouchHelperCallback;
import kolibri.example.kolibri.Interface.EventListenerR;
import kolibri.example.kolibri.Main2Activity.Ticket;
import kolibri.example.kolibri.Registration.PersonActivity;
import kolibri.example.kolibri.Singleton.ProgamMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import kolibri.example.kolibri.MenuListActivity.MenuTicket;
import com.example.kalibri.R;
import java.util.List;

public class BasketActivity extends  AppCompatActivity implements EventListenerR {

    RecyclerView basketList;
    BasketCustomAdapter basketADapter;
    TextView  summResult;
    TextView nameRestaurant;
    long summ;
    List<MenuTicket> basketRobins;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        basketList = findViewById(R.id.basket_recyclerView);
        summResult = findViewById(R.id.summa_items);
        nameRestaurant = findViewById(R.id.name_restaurant);

        List <Ticket> names = ProgamMenu.getProgamMenu().getRestaranFile();
        Log.d("NameREST","NameREST =" + names);
        Ticket rr = null;
        for(Ticket i : names){
            rr = i;
        }
        Log.d("NameREST","NameREST =" + rr.getName());
        nameRestaurant.setText(rr.getName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        basketList.setLayoutManager(layoutManager);
        basketList.setHasFixedSize(true);
        summ = ProgamMenu.getProgamMenu().count();
        summResult.setText(Long.toString(summ)+ " р.");
        ProgamMenu.getProgamMenu().subscribe(this);
        basketRobins = ProgamMenu.getProgamMenu().getMenuInfo();
        basketADapter = new BasketCustomAdapter(this, basketRobins);
        basketList.setAdapter(basketADapter);
        //свайп
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(basketADapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(basketList);
    }

    public void keepFF(View view) {
        List<Ticket> fromSumDelivery = ProgamMenu.getProgamMenu().getRestaranFile();
        Ticket free = null;
        for (Ticket i: fromSumDelivery) {
            free = i;
        }
        long textFromFreeDelivery = free.getFromSumDelivery();
        if (summ < 1) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Но в вашей корзине совсем-совсем пусто!",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if (textFromFreeDelivery > summ) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Сумма заказа должна быть не менее " + textFromFreeDelivery + " руб.",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {
            Intent intent = new Intent(BasketActivity.this, PersonActivity.class);
            startActivity(intent);
        }
    }

    public void clearBasket(View view) {  //кнопка "Очистить"
        AlertDialog.Builder builder1 = new AlertDialog.Builder(BasketActivity.this);
        builder1.setTitle("Очистить")
                .setMessage("Очистить корзину?")
                .setPositiveButton("Да",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogCancel, int id) {
                                basketRobins.clear(); //очищаем корзину
                                ProgamMenu.getProgamMenu().notifyTwo();
                                finish();
                                dialogCancel.cancel();
                            }
                        })
                .setNegativeButton("Ой, нет!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        builder1.show();
    }

    @Override
    public void updateRR() {
        basketRobins = ProgamMenu.getProgamMenu().getMenuInfo();
        basketADapter = new BasketCustomAdapter(this, basketRobins);
        basketList.setAdapter(basketADapter);
        summ = ProgamMenu.getProgamMenu().count();
        summResult.setText(Long.toString(summ)+ " р.");
    }

    public void menu_basket(View view) {
        Intent intent = new Intent(BasketActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
