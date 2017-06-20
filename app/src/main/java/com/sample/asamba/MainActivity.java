package com.sample.asamba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.library.asamba.smb.Asamba;
import com.sample.asamba.adapter.SmbFileAdapter;
import com.sample.asamba.task.SmbAsyncTask;

import jcifs.smb.SmbFile;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;

    private SmbFileAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initASamba();

        initData();
        initView();


        new SmbAsyncTask(this) {
            @Override
            protected void after(SmbFile[] files) {
                mAdapter.updateData(files);
            }
        }.execute();
    }

    private void initASamba() {



    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new SmbFileAdapter(new SmbFile[]{});
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
    }


}
