package com.epicodus.parallelmusic.util;

/**
 * Created by Guest on 4/6/18.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
    void onItemSelected();
    void onItemClear();
}
