package com.ronny.mrv.javaexample;

import android.os.Bundle;
import android.os.Handler;

import com.ronny.mrv.MultipleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Object> mAllDataSource;
    private MultipleViewAdapter mMultipleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mAllDataSource = new ArrayList<>();

        mAllDataSource.add(DataUtil.getRandomStudent());
        // mAllDataSource.add(DataUtil.getRandomStudent());

        mRecyclerView = findViewById(R.id.recycler_view);
        mMultipleViewAdapter = new MultipleViewAdapter();
        mMultipleViewAdapter.register(Student.class, new StudentManager());
        mMultipleViewAdapter.setAllDataSource(mAllDataSource);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMultipleViewAdapter);
        test();
    }

    private void test() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // mAllDataSource.remove(0);
                List<Object> newDataSource = new ArrayList<>();
                newDataSource.addAll(mAllDataSource);

                Student student = (Student) newDataSource.get(0);
                student.mName = DataUtil.getStudentName();
                newDataSource.set(0,student);
                newDataSource.add(DataUtil.getRandomStudent());
                mMultipleViewAdapter.setAllDataSource(newDataSource);
            }
        }, 1000);
    }


}
