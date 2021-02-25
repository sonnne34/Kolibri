package kolibri.example.kolibri.BasketItems;

public interface ItemTouchHelperAdapter {
    //необходимые методы для того, чтобы уведомить об обновлении данных
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
