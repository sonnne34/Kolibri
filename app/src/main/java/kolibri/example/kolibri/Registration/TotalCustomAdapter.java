package kolibri.example.kolibri.Registration;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kolibri.example.kolibri.MenuListActivity.MenuTicket;
import com.example.kalibri.R;

import java.util.ArrayList;
import java.util.List;

public class TotalCustomAdapter  extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private List<MenuTicket> TotalCustomAdapterList = new ArrayList<>();
    TotalActivity totalActivity;

    public TotalCustomAdapter(TotalActivity totalActivity,  List<MenuTicket> purchasesTotal){

        TotalCustomAdapterList = purchasesTotal;

        totalActivity = totalActivity;
    }
    @Override
    public int getItemCount() {
        return TotalCustomAdapterList.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder total;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_registration_total, parent ,false);
        total = new TotalCustomAdapter.TotalViewHolder(view);

        return total;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder total, final int position) {

        TotalCustomAdapter.TotalViewHolder totalItemHolder = (TotalCustomAdapter.TotalViewHolder) total;
        totalItemHolder.name_dish_total.setText(TotalCustomAdapterList.get(position).getName());
        totalItemHolder.number_total.setText((Long.toString( TotalCustomAdapterList.get(position).getCountDialog())));
        long sim = TotalCustomAdapterList.get(position).getCost();
        long slon = TotalCustomAdapterList.get(position).getCountDialog();
        long summ = sim * slon;
        totalItemHolder.cost_total.setText(Long.toString(summ));
    }

    class TotalViewHolder extends RecyclerView.ViewHolder{

        TextView cost_total;
        TextView number_total;
        TextView name_dish_total;

        public TotalViewHolder (View itemView) {
            super(itemView);

            cost_total = (TextView) itemView.findViewById(R.id.cost_total); //итоговоя стоимость блюда (кол-во*цена)
            number_total = (TextView) itemView.findViewById(R.id.number_total); //кол-во штук
            name_dish_total = (TextView) itemView.findViewById(R.id.name_dish_total);
        }
    }
}
