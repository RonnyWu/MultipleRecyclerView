package com.ronny.mrv.javaexample;

import android.util.ArrayMap;
import android.util.Log;
import android.widget.TextView;

import com.ronny.mrv.Manager;
import com.ronny.mrv.MultipleViewHolder;

import androidx.annotation.NonNull;

/**
 * TODO
 * <p>
 * Create By 吴荣 at 2019/1/12 18:01
 */
public class StudentManager extends Manager<Student> {

    @Override
    public int getLayoutResId(@NonNull Student data, int styleModel) {
        int mNumber = Integer.parseInt(data.mNumber);
        int remainder = mNumber % 3;
        switch (remainder) {
            case 0:
                return R.layout.multiple_item_view_student_1;
            case 1:
                return R.layout.multiple_item_view_student_2;
            case 2:
            default:
                return R.layout.multiple_item_view_student_3;
        }
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder holder, Student data, int position) {
        TextView mNumber = holder.getView(R.id.text_view_id);
        TextView mName = holder.getView(R.id.text_view_name);
        TextView mAge = holder.getView(R.id.text_view_age);
        TextView mSex = holder.getView(R.id.text_view_sex);
        TextView mClass = holder.getView(R.id.text_view_class);

        mNumber.setText(data.mNumber);
        mName.setText(data.mName);
        mAge.setText(data.mAge);
        mSex.setText(data.mSex);
        mClass.setText(data.mClass);
    }

    @Override
    public void onPayloadViewHolder(MultipleViewHolder holder, ArrayMap<String, Object> payload, int position) {
        Log.e("AAAAA", "payload getter >>>> " + payload.toString());
        if (null != payload.get("name")) {
            TextView mName = holder.getView(R.id.text_view_name);
            mName.setText((CharSequence) payload.get("name"));
        }
        if (null != payload.get("age")) {
            TextView mAge = holder.getView(R.id.text_view_age);
            mAge.setText((CharSequence) payload.get("age"));
        }
        if (null != payload.get("sex")) {
            TextView mSex = holder.getView(R.id.text_view_sex);
            mSex.setText((CharSequence) payload.get("sex"));
        }
        if (null != payload.get("class")) {
            TextView mClass = holder.getView(R.id.text_view_class);
            mClass.setText((CharSequence) payload.get("class"));
        }
    }

    @Override
    public boolean onItemComparison(Student oldData, Student newData) {
        return oldData.mNumber.equals(newData.mNumber);
    }

    @Override
    public boolean onContentComparison(Student oldData, Student newData) {

        boolean b = oldData.mName.equals(newData.mName)
                && oldData.mAge.equals(newData.mAge)
                && oldData.mSex.equals(newData.mSex)
                && oldData.mClass.equals(newData.mClass);
        Log.e("AAAAA", "content >>>> " + oldData.mName + " | " + newData.mName + " >>>> " + b);
        return b;
    }

    @Override
    public ArrayMap<String, Object> onPayloadComparison(Student oldData, Student newData) {
        ArrayMap<String, Object> payload = new ArrayMap<>();
        if (!oldData.mName.equals(newData.mName)) {
            payload.put("name", newData.mName);
        }
        if (!oldData.mAge.equals(newData.mAge)) {
            payload.put("age", newData.mAge);
        }
        if (!oldData.mSex.equals(newData.mSex)) {
            payload.put("sex", newData.mSex);
        }
        if (!oldData.mClass.equals(newData.mClass)) {
            payload.put("class", newData.mClass);
        }
        Log.e("AAAAA", "payload setter >>>> " + payload.toString());
        return payload;
    }
}
