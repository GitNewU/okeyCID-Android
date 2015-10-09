package com.okeytime.okeycid.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.CellInfo;
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
 * 小区信息
 * Created by xi on 2015/10/9.
 */
public class CELLSFragment extends MBaseFragment {


    ListView mListView;
    TextView mTextView;
    TextView mTextView2;
    CellAdapter mCellAdapter;
    TelephonyManager mTelephonyManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cells, container, false);
        mListView = (ListView) rootView.findViewById(R.id.section_list);
        mTextView = (TextView) rootView.findViewById(R.id.text);
        mTextView2 = (TextView) rootView.findViewById(R.id.text2);
        mCellAdapter = new CellAdapter(getActivity(),null,mTextView2);
        mListView.setAdapter(mCellAdapter);
        return rootView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("CELLSFragment", "onActivityCreated");
        mTelephonyManager = ((MainActivity) getActivity()).mTelephonyManager;
        getData();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 获取数据
     */
    public void getData() {

        setListCellInfoLte();
    }

    /**
     * 小区信息
     */
    private void setListCellInfoLte() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mTextView.setVisibility(View.GONE);
            List<CellInfo> allCellInfo = mTelephonyManager.getAllCellInfo();
            mCellAdapter.setData(allCellInfo);
        }else{
            mTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void toRefresh() {
        getData();
    }


    class CellAdapter extends BaseAdapter {
        public List<CellInfo> datas;
        Context context;
        TextView mTv;
        CellAdapter(Context context,List<CellInfo> datas,TextView mTv){
            this.datas = datas;
            this.context = context;
            this.mTv = mTv;
        }

        public void setData(List<CellInfo> datas){
            mTv.setText("当前数目为："+datas.size());
            this.datas = datas;
            this.notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            if (datas ==null) return 0;
            return datas.size();
        }

        @Override
        public CellInfo getItem(int position) {
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
            CellInfo ci = getItem(position);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                viewHolder.textView.setText(ci.toString());
            }

            return convertView;
        }
        class ViewHolder{
            TextView textView;
        }
    }
}
