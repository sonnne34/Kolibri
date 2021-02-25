package kolibri.example.kolibri.Main2Activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kalibri.R;


import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class OptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_activity);
        //размер активити
        getWindow().setLayout(WRAP_CONTENT, MATCH_PARENT);
        //положение активити - слева
        getWindow().setGravity(Gravity.LEFT);
        this.setFinishOnTouchOutside(true);
    }

    //закрыть опции по кнопке
    public void closeOption(View view) {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}

