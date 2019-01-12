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
    private int looper = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mAllDataSource = new ArrayList<>();
        mAllDataSource.add(DataUtil.getRandomStudent());

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
                Student oldStudent = (Student) mAllDataSource.get(0);
                Student modifyStudent = new Student();
                modifyStudent.mNumber = oldStudent.mNumber;
                modifyStudent.mName = DataUtil.getStudentName();
                modifyStudent.mAge = DataUtil.getStudentAge();
                modifyStudent.mSex = DataUtil.getStudentSex();
                modifyStudent.mClass = DataUtil.getStudentClass();
                mAllDataSource.set(0, modifyStudent);
                // mAllDataSource.add(DataUtil.getRandomStudent());
                mMultipleViewAdapter.setAllDataSource(mAllDataSource);
                looper--;
                if (looper > 0) {
                    test();
                }
            }
        }, 2000);
    }


}
