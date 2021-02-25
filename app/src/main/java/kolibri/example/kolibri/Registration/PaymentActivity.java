package kolibri.example.kolibri.Registration;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import com.example.kalibri.R;
import kolibri.example.kolibri.MenuActivity;
import kolibri.example.kolibri.Singleton.BanknotePaymentMenu;
import kolibri.example.kolibri.Singleton.MethodPaymentMenu;
import kolibri.example.kolibri.Singleton.ProgamMenu;

public class PaymentActivity  extends AppCompatActivity {

    private TextView sumPayment;
    long sumPersonPayment;
    private EditText banknotePayment;
    private String method;
    private TextInputLayout inputCash;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_payment_details);

        sumPayment = findViewById(R.id.sum_payment);
        banknotePayment = findViewById(R.id.banknote_payment);
        sumPersonPayment = ProgamMenu.getProgamMenu().count();
        sumPayment.setText("Заказ на: " + Long.toString(sumPersonPayment) + " р.");
        RadioButton methodCardPayment = (RadioButton) findViewById(R.id.method_card_payment);
        methodCardPayment.setOnClickListener(radioButtonClickListener);
        RadioButton methodCashPayment = (RadioButton) findViewById(R.id.method_cash_payment);
        methodCashPayment.setChecked(true);
        methodCashPayment.setOnClickListener(radioButtonClickListener);
        inputCash = findViewById(R.id.input_cash);
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton card = (RadioButton) view;
            switch (card.getId()) {
                case R.id.method_card_payment:
                    method = "Картой"; //при нажатии на "Картой" значение переменной - "Картой"
                    inputCash.setError(null); //при нажатии на "Картой" ошибок с обязательным полем чтобы не всплывало
                    inputCash.setVisibility(View.GONE); //
                    break;
                case R.id.method_cash_payment:
                    method = "Наличными"; //при нажатии "Наличными" значение переменной - "Наличными"
                    inputCash.setVisibility(View.VISIBLE); //чтобы возвращалась строка "Сдача с"
                    break;
                default:
                    break;
            }
        }
    };

    public void keep(View view) {
        {
            Intent intent = new Intent(PaymentActivity.this, TotalActivity.class);
            String banknote = banknotePayment.getText().toString();
            BanknotePaymentMenu.getBanknotePaymentMenu().addBanknoteMenuFile(banknote);
            MethodPaymentMenu.getMethodPaymentMenu().addMethodMenuFile(method);
            //при методе оплаты не картой (наличными) "Сдача с" становится обязательным полем
            if (method != "Картой") {
                if (banknotePayment.getText().length() == 0) { // если вес "Сдача с" = 0, то
                    inputCash.setError("Обязательное поле");  } //ошибка
                else { inputCash.setError(null); //если не равно 0 то
                    startActivity(intent);} //переход в сл. активити
            }
            else {  startActivity(intent); } //если картой - переходим сразу в сл. активити
        }
    }

    public void menu_payment(View view) {
        Intent intent = new Intent(PaymentActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}