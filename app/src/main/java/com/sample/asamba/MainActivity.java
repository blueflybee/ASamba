package com.sample.asamba;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.library.asamba.callbacks.FilesCallBack;
import com.library.asamba.smb.Asamba;
import com.library.asamba.utils.ToastUtils;
import com.sample.asamba.adapter.SmbFileAdapter;

import jcifs.smb.SmbFile;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;

    private SmbFileAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private Object context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initASamba();

        initData();
        initView();

        Asamba.with(this).files(new FilesCallBack() {

            @Override
            public void onSuccess(SmbFile[] smbFiles) {
                mAdapter.updateData(smbFiles);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showToast(getContext(), message);
            }
        });

    }

    private void initASamba() {
        Asamba.with(this)
                .username("shaojun")
                .password("123456")
                .host("192.168.1.102")
                .path("/")
                .init();
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


    public Context getContext() {
        return this;
    }
}
