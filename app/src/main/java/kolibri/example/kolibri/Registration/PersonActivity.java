package kolibri.example.kolibri.Registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.example.kalibri.R;
import kolibri.example.kolibri.MenuActivity;
import kolibri.example.kolibri.Singleton.CommentPersonMenu;
import kolibri.example.kolibri.Singleton.NamePersonMenu;
import kolibri.example.kolibri.Singleton.PhonePersonMenu;

public class PersonActivity  extends AppCompatActivity {

    private EditText phonePersonData;
    private EditText namePersonData;
    private TextInputLayout inputName;
    private TextInputLayout inputPhone;
    private EditText commentPersonData;

    final String PHONE = "PHONE";
    SharedPreferences sPhone;
    String saveTextPhone;

    final String NAME = "NAME";
    SharedPreferences sName;
    String saveTextName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_person_data);

        phonePersonData = findViewById(R.id.phone_person_data);
        namePersonData = findViewById(R.id.name_person_date);
        inputName = findViewById(R.id.input_Name);
        inputPhone = findViewById(R.id.input_Phone);
        commentPersonData = findViewById(R.id.comment_person_data);
        loadTextPerson();
    }

    public void keepBB(View view) {

        //уведомление об обязательном заполнении полей
        if (phonePersonData.getText().length() == 0 || namePersonData.getText().length() == 0) {
            if (phonePersonData.getText().length() == 0) {
                inputPhone.setError("Обязательное поле");
            } else {
                inputPhone.setError(null);
            }

            if (namePersonData.getText().length() == 0) {
                inputName.setError("Обязательное поле");
            } else {
                inputName.setError(null);
            }
        }
        //переход к сл. активити и передача данных
        else {
            Intent intent = new Intent(PersonActivity.this, AddressActivity.class);

            String name = namePersonData.getText().toString();
            saveTextName(name);
            NamePersonMenu.getNamePersonMenu().addNameMenuFile(name);
            NamePersonMenu.getNamePersonMenu().showNameMenuFile();

            String phone = phonePersonData.getText().toString();
            saveTextPhone(phone);
            PhonePersonMenu.getPhonePersonMenu().addPhoneMenuFile(phone);

            String comment = commentPersonData.getText().toString();
            CommentPersonMenu.getCommentPersonMenu().addCommentMenuFile(comment);

            startActivity(intent);
        }
    }

    private void saveTextPhone(String phone) {
        sPhone = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ph = sPhone.edit();
        ph.putString(PHONE, phone).toString();
        ph.commit();
    }

    private void saveTextName(String name) {
        sName = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor na = sName.edit();
        na.putString(NAME, name).toString();
        na.commit();
    }

    private void loadTextPerson() {
        sPhone = getPreferences(MODE_PRIVATE);
        saveTextPhone = sPhone.getString(PHONE,"");
        phonePersonData.setText(saveTextPhone);

        sName = getPreferences(MODE_PRIVATE);
        saveTextName = sName.getString(NAME,"");
        namePersonData.setText(saveTextName);

    }

    public void menu_person(View view) {
        Intent intent = new Intent(PersonActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
