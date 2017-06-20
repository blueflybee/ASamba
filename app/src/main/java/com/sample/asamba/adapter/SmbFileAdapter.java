package com.sample.asamba.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.asamba.R;

import java.util.ArrayList;
import java.util.List;

import jcifs.smb.SmbFile;

/**
 * <pre>
 *     author : blueflybee
 *     e-mail : wusj_2017@163.com
 *     time   : 2017/03/07
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class SmbFileAdapter extends RecyclerView.Adapter<SmbFileAdapter.ViewHolder> {

    private SmbFile[] mSmbFiles;

    public SmbFileAdapter(SmbFile[] files) {
        this.mSmbFiles = files;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_adapter, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        holder.mTv.setText(mSmbFiles[position].getPath());
    }

    @Override
    public int getItemCount() {
        return mSmbFiles == null ? 0 : mSmbFiles.length;
    }


   class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.tv_file);
        }
    }


    public void updateData(SmbFile[] files) {
        this.mSmbFiles = (files == null ? new SmbFile[]{} : files);
        notifyDataSetChanged();
    }
}
