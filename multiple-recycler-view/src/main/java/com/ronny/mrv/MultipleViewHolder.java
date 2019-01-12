package com.ronny.mrv;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 */
public final class MultipleViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mItemChildViewCaches;

    /* package */ MultipleViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemChildViewCaches = new SparseArray<>();
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View itemChildViewCache = mItemChildViewCaches.get(viewId);
        if (itemChildViewCache == null) {
            itemChildViewCache = itemView.findViewById(viewId);
            mItemChildViewCaches.put(viewId, itemChildViewCache);
        }
        // noinspection unchecked
        return (T) itemChildViewCache;
    }
}
