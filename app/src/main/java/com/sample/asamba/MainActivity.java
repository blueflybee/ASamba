package com.sample.asamba;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.library.asamba.callbacks.DeleteCallBack;
import com.library.asamba.callbacks.DownloadCallBack;
import com.library.asamba.callbacks.FilesCallBack;
import com.library.asamba.smb.Asamba;
import com.library.asamba.utils.ToastUtils;
import com.sample.asamba.adapter.SmbFileAdapter;
import com.sample.asamba.utils.DialogUtil;

import java.io.File;

import jcifs.smb.SmbFile;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private SmbFileAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;


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
                showListDialog(smbFile, file);
                return false;
            }
        });
    }

    private void download(SmbFile smbFile, File file) {
        Asamba.with(getContext())
                .from(smbFile.getPath())
                .to(file.getAbsolutePath())
                .get(new DownloadCallBack() {
                    @Override
                    public void onSuccess(String message, String des) {
                        mProgressBar.setVisibility(View.GONE);
                        mProgressBar.setProgress(0);
                        ToastUtils.showToast(getContext(), message + des);
                    }

                    @Override
                    public void onFail(String message) {
                        mProgressBar.setVisibility(View.GONE);
                        ToastUtils.showToast(getContext(), message);
                    }

                    @Override
                    public void onProgress(int progress) {
                        if (!mProgressBar.isShown()) {
                            mProgressBar.setVisibility(View.VISIBLE);
                        }
                        mProgressBar.setProgress(progress);
                    }
                });
    }

    private void showListDialog(final SmbFile smbFile, final File file) {
        String[] items = getResources().getStringArray(R.array.dialog_item);
        DialogUtil.showListDialog(getContext(), "请选择", items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                
                switch (which) {
                    case 0:

                        break;
                    case 1:
                        download(smbFile, file);
                        break;
                    case 2:
                        break;
                    case 3:
                        delete(smbFile);
                        break;

                }

            }
        });
    }

    private void delete(SmbFile smbFile) {
        Asamba.with(getContext())
                .target(smbFile.getPath())
                .delete(new DeleteCallBack() {

                    @Override
                    public void onSuccess(SmbFile[] smbFiles, String message) {
                        ToastUtils.showToast(getContext(), message);
                        mAdapter.updateData(smbFiles);
                    }

                    @Override
                    public void onFail(String message) {
                        ToastUtils.showToast(getContext(), message);
                    }
                });
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Asamba.with(getContext()).back().files(new FilesCallBack() {
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
