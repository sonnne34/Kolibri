package kolibri.example.kolibri.Registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kalibri.R;

import kolibri.example.kolibri.MenuActivity;
import kolibri.example.kolibri.Singleton.ApartmentAddressMenu;
import kolibri.example.kolibri.Singleton.CitySington;
import kolibri.example.kolibri.Singleton.EntranceAddressMenu;
import kolibri.example.kolibri.Singleton.HomeAddressMenu;
import kolibri.example.kolibri.Singleton.LevelAddressMenu;
import kolibri.example.kolibri.Singleton.StreetAddressMenu;

public class AddressActivity extends AppCompatActivity {

    private EditText streetAddress;
    private EditText homeAddress;
    private TextInputLayout inputStreet;
    private TextInputLayout inputHome;
    private EditText entranceAddress;
    private EditText apartmentAddress;
    private EditText levelAddress;
    private TextView city;

    final String STREET = "STREET";
    SharedPreferences sStreet;
    String saveTextStreet;

    final String HOME = "HOME";
    SharedPreferences sHome;
    String saveTextHome;

    final String ENTRANCE = "ENTRANCE";
    SharedPreferences sEntrance;
    String saveTextEntrance;

    final String APARTMENT = "APARTMENT";
    SharedPreferences sApartment;
    String saveTextApartment;

    final String LEVEL = "LEVEL";
    SharedPreferences sLevel;
    String saveTextLevel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_address);

        streetAddress = findViewById(R.id.street_address);
        homeAddress = findViewById(R.id.home_address);
        inputStreet = findViewById(R.id.inputstreet);
        inputHome = findViewById(R.id.inputhome);
        entranceAddress = findViewById(R.id.entrance_address);
        apartmentAddress = findViewById(R.id.apartment_address);
        levelAddress = findViewById(R.id.level_address);
        city = findViewById(R.id.cities_specified_address);
        String name =  CitySington.getCitySington().getCityFile();
        city.setText(name);
        loadTextAddress();
    }

    public void keepDD(View view) {


        if (streetAddress.getText().length() == 0 || homeAddress.getText().length() == 0)
        {
            if (streetAddress.getText().length() == 0)
            { inputStreet.setError("Обязательное поле"); }
            else { inputStreet.setError(null);}

            if (homeAddress.getText().length() == 0)
            { inputHome.setError("Обязательное поле"); }
            else { inputHome.setError(null);}
        }
        else {
            Intent intent = new Intent(AddressActivity.this, PaymentActivity.class);

            String street=streetAddress.getText().toString();
            saveTextStreet(street);
            StreetAddressMenu.getStreetAddressMenu().addStreetMenuFile(street);

            String home=homeAddress.getText().toString();
            saveTextHome(home);
            HomeAddressMenu.getHomeAddressMenu().addHomeMenuFile(home);

            String entrance=entranceAddress.getText().toString();
            saveTextEntrance(entrance);
            EntranceAddressMenu.getEntranceAddressMenu().addEntranceMenuFile(entrance);

            String apartment=apartmentAddress.getText().toString();
            saveTextApartment(apartment);
            ApartmentAddressMenu.getApartmentAddressMenu().addApartmentMenuFile(apartment);

            String level=levelAddress.getText().toString();
            saveTextLevel(level);
            LevelAddressMenu.getLevelAddressMenu().addLevelMenuFile(level);

            startActivity(intent);
        }
    }

    private void saveTextLevel(String level) {
        sLevel = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor le = sLevel.edit();
        le.putString(LEVEL, level).toString();
        le.commit();
    }

    private void saveTextApartment(String apartment) {
        sApartment = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ap = sApartment.edit();
        ap.putString(APARTMENT, apartment).toString();
        ap.commit();
    }

    private void saveTextEntrance(String entrance) {
        sEntrance = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor en = sEntrance.edit();
        en.putString(ENTRANCE, entrance).toString();
        en.commit();
    }

    private void saveTextHome(String home) {
        sHome = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ho = sHome.edit();
        ho.putString(HOME, home).toString();
        ho.commit();
    }

    private void saveTextStreet(String street) {
        sStreet = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor st = sStreet.edit();
        st.putString(STREET, street).toString();
        st.commit();
    }

    private void loadTextAddress() {
        sLevel = getPreferences(MODE_PRIVATE);
        saveTextLevel = sLevel.getString(LEVEL,"");
        levelAddress.setText(saveTextLevel);

        sApartment = getPreferences(MODE_PRIVATE);
        saveTextApartment = sApartment.getString(APARTMENT,"");
        apartmentAddress.setText(saveTextApartment);

        sEntrance = getPreferences(MODE_PRIVATE);
        saveTextEntrance = sEntrance.getString(ENTRANCE,"");
        entranceAddress.setText(saveTextEntrance);

        sHome = getPreferences(MODE_PRIVATE);
        saveTextHome = sHome.getString(HOME,"");
        homeAddress.setText(saveTextHome);

        sStreet = getPreferences(MODE_PRIVATE);
        saveTextStreet = sStreet.getString(STREET,"");
        streetAddress.setText(saveTextStreet);
    }

    public void menu_adress(View view) {
        Intent intent = new Intent(AddressActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}