package kolibri.example.kolibri;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import kolibri.example.kolibri.Models.CityModels;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.kalibri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CitiesActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private List<String> citiesButten = new ArrayList<>();
    private List<String> citiesButtenEng = new ArrayList<>();
    ListView citiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        citiesList = (ListView) findViewById(R.id.list_city);
        myRef = FirebaseDatabase.getInstance().getReference();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("CityNames");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    CityModels value = ds.getValue(CityModels.class);
                    citiesButten.add(value.getRus());
                    citiesButtenEng.add(value.getEng());
                    Log.d( "dima", "Smolensk: " + value.getEng());
                }
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = citiesButten.get(position);
                String cityEng = citiesButtenEng.get(position);
                Intent intent = new Intent();
                intent.putExtra("pos",city);
                intent.putExtra("posEng",cityEng);
                setResult(RESULT_OK,intent);
                finish();
                Log.d( "Gorod","ItemClick: position = " + city + ", id = " + id);
            }
        });
    }
    private void updateUI() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,citiesButten);
        citiesList.setAdapter(adapter);
    }
}
