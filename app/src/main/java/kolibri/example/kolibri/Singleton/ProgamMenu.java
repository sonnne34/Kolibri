package kolibri.example.kolibri.Singleton;

import android.app.Application;
import android.util.Log;

import kolibri.example.kolibri.Interface.EventListenerR;
import kolibri.example.kolibri.Main2Activity.Ticket;
import kolibri.example.kolibri.MenuListActivity.MenuTicket;

import java.util.ArrayList;
import java.util.List;

public class ProgamMenu extends Application {

    List<EventListenerR> listeners = new ArrayList <>();
    private static ProgamMenu progamMenu;
    private List<MenuTicket> menuFile = new ArrayList <>();
    private List<Ticket> restaranFile = new ArrayList <>();
    public static synchronized ProgamMenu getProgamMenu(){
        if(progamMenu == null){
            progamMenu = new ProgamMenu();
        }
        return progamMenu;
    }
    private ProgamMenu(){
    }

    public  MenuTicket  proverkaNaNalichie (MenuTicket position){
        MenuTicket nn = null;
        for(MenuTicket i : menuFile){
            if(position.getName().equals(i.getName())){
                nn = i;
            }
        }
        return nn;
    }
    public void addMenuInfo (MenuTicket menuInfo){
        MenuTicket ff =  ProgamMenu.getProgamMenu().proverkaNaNalichie(menuInfo);
       if(ff == null){
           menuFile.add(menuInfo);
       }else{
           ff.setName(menuInfo.getName());
           ff.setCountDialog(menuInfo.getCountDialog());
       }
    }

   public List<MenuTicket> getMenuInfo(){
        return menuFile;
   }

    public void addRestaranFile (Ticket restInfo){
        restaranFile.add(restInfo);
    }

    public List<Ticket> getRestaranFile(){
        return restaranFile;
    }

    public void showMenuFile (){
        Log.d("Program","NameDish = " + menuFile);
    }

    public void showRestFile (){
        Log.d("Program","RestFile " + restaranFile);
    }

    public boolean checkingThelist(MenuTicket gg){
        for(MenuTicket i : menuFile){
            if(gg.equals(i)){
                return true;
            }
        }
        return false;
    }

    public long count(){
        long sumItems = 0;
        for(MenuTicket i : menuFile){
            sumItems =  sumItems + (i.getCost() * i.getCountDialog());
        }
        return sumItems ;
    }

    public void subscribe(EventListenerR listener) {
        listeners.add(listener);
        Log.d("Test","Listener = " + listener);
    }

    public void unsubscribe( EventListenerR listener) {
        listeners.remove(listener);
    }

    public void notifyTwo() {
        for (EventListenerR listener : listeners) {
            listener.updateRR();
            Log.d("Test","notifiTwo = " + listener );
        }
    }
}
