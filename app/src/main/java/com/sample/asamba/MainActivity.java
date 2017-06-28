package com.sample.asamba;

import android.app.Notification;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.library.asamba.callbacks.DownloadCallBack;
import com.library.asamba.callbacks.FilesCallBack;
import com.library.asamba.smb.Asamba;
import com.library.asamba.utils.ToastUtils;
import com.sample.asamba.adapter.SmbFileAdapter;

import java.io.File;

import jcifs.smb.SmbFile;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

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

//        new SmbAsyncTask(this) {
//            @Override
//            protected void after(SmbFile[] files) {
//
//            }
//        }.execute();

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

        Asamba.with(getContext())
                .username("shaojun")
                .password("123456")
                .host("192.168.1.103")
                .path("/")
                .init();
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new SmbFileAdapter(new SmbFile[]{});
        mAdapter.setOnItemClickListener(new SmbFileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, SmbFile smbFile) {
                System.out.println("smbFile = " + smbFile.getPath());
                Asamba.with(getContext()).enter(smbFile).files(new FilesCallBack() {
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
        });

        mAdapter.setOnItemLongClickListener(new SmbFileAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, SmbFile smbFile) {
                System.out.println("smbFile = " + smbFile.getPath());
                File file = new File(getContext().getCacheDir(), smbFile.getName());
                System.out.println("file.getPath() = " + file.getAbsolutePath());
                Asamba.with(getContext())
                        .get(smbFile.getPath())
                        .into(file.getAbsolutePath())
                        .download(new DownloadCallBack() {
                            @Override
                            public void onSuccess(String message, String des) {
                                mProgressBar.setProgress(0);
                                ToastUtils.showToast(getContext(), message + des);
                            }

                            @Override
                            public void onFail(String message) {
                                ToastUtils.showToast(getContext(), message);

                            }

                            @Override
                            public void onProgress(int progress) {
                                System.out.println("progress = " + progress);
                                mProgressBar.setProgress(progress);
                            }
                        });
                return false;
            }
        });
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Asamba.with(getContext()).out().files(new FilesCallBack() {
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
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);

        mProgressBar = (ProgressBar) findViewById(R.id.my_progress);

    }


    public Context getContext() {
        return this;
    }


}
