package com.okeytime.okeycid.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.okeytime.okeycid.MainActivity;
import com.okeytime.okeycid.R;

import java.util.List;

/**
 * 相邻CID
 * Created by xi on 2015/10/9.
 */
public class NeighboringFragment extends MBaseFragment {

    ListView mListView;
    TextView mTextView;
    NeighboringAdapter mNeighboringAdapter;
    TelephonyManager mTelephonyManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_neighboring, container, false);
        mListView = (ListView) rootView.findViewById(R.id.section_list);
        mTextView = (TextView) rootView.findViewById(R.id.text);
        mNeighboringAdapter = new NeighboringAdapter(getActivity(),null);
        mListView.setAdapter(mNeighboringAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mTelephonyManager = ((MainActivity) getActivity()).mTelephonyManager;
        getData();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 获取数据
     */
    public void getData() {
        setListNeighboringCellInfo();

    }

    /**
     * 邻区信息
     */
    private void setListNeighboringCellInfo() {
        List<NeighboringCellInfo> list = mTelephonyManager.getNeighboringCellInfo();

        if (list.isEmpty()){
            mTextView.setVisibility(View.VISIBLE);
        }else{
            mTextView.setVisibility(View.GONE);
            mNeighboringAdapter.setData(list);
        }
    }

    @Override
    public void toRefresh() {
        getData();
    }


    class NeighboringAdapter extends BaseAdapter {
        public List<NeighboringCellInfo> datas;
        Context context;

        NeighboringAdapter(Context context,List<NeighboringCellInfo> datas){
            this.datas = datas;
            this.context = context;
        }

        public void setData(List<NeighboringCellInfo> datas){
            this.datas = datas;
            this.notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            if (datas ==null) return 0;
            return datas.size();
        }

        @Override
        public NeighboringCellInfo getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_celllist, null);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.item_tv_content);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            NeighboringCellInfo nc = getItem(position);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                viewHolder.textView.setText("(" + position + ")_CID:" + nc.getCid() + "|| _LAC:" + nc.getLac() + "|| _PSC:" + nc.getPsc() + "|| _RSSI:" + nc.getRssi() + "|| _NETWORKTYPE:" + nc.getNetworkType());
            }

            return convertView;
        }
        class ViewHolder{
            TextView textView;
        }
    }
}
