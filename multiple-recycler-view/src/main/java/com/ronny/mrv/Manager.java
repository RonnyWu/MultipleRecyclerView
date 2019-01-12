package com.ronny.mrv;

import org.json.JSONObject;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public abstract class Manager<T> {

    // TODO 显示风格.
    @LayoutRes
    public abstract int getLayoutResId(@NonNull T data, int styleModel);

    public abstract void onBindViewHolder(MultipleViewHolder holder, T data, int position);

    public abstract void onPayloadViewHolder(MultipleViewHolder holder, JSONObject payload, int position);

    public abstract boolean onItemComparison(T oldData, T newData);

    public abstract boolean onContentComparison(T oldData, T newData);

    public abstract JSONObject onPayloadComparison(T oldData, T newData);
}