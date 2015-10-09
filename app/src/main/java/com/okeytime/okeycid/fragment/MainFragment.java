package com.okeytime.okeycid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.okeytime.okeycid.MainActivity;
import com.okeytime.okeycid.R;
import java.util.List;

/**
 * CID信息
 * Created by xi on 2015/10/8.
 */
public class MainFragment extends MBaseFragment {
    TextView mTextView;
    TelephonyManager mTelephonyManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MainFragment", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mTextView = (TextView) rootView.findViewById(R.id.section_label);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("MainFragment", "onActivityCreated");
        mTelephonyManager = ((MainActivity) getActivity()).mTelephonyManager;
        getData();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 获取数据
     */
    public void getData() {
        setGSMInfo();
    }
    public void getData(CellLocation location) {
        mTextView.setText("");
        setGSMInfo(location);
    }



    public void getData(List<CellInfo> cellInfo) {
        mTextView.setText("");
        setGSMInfo();
    }



    /**
     * 获取基站信息
     */
    private void setGSMInfo() {
        String operator = mTelephonyManager.getNetworkOperator();
        /**通过operator获取 MCC 和MNC */
        int mnc = Integer.parseInt(operator.substring(3));
        switch (mnc){
            case 0:
            case 1:
                setCellLocation(1, null);
                break;
            case 2:
                setCellLocation(2, null);
                break;
        }
        Log.d("CID", "[operator]:" + operator);
    }

    private void setGSMInfo(CellLocation location) {
        String operator = mTelephonyManager.getNetworkOperator();
        /**通过operator获取 MCC 和MNC */
        int mnc = Integer.parseInt(operator.substring(3));
        switch (mnc){
            case 0:
            case 1:
                setCellLocation(1,location);
                break;
            case 2:
                setCellLocation(2,location);
                break;
        }
        Log.d("CID", "[operator]:" + operator);
    }



    /**
     * 换行
     */
    private void priLine() {
        mTextView.append("\n\n");
    }



    /**
     * 获取LAC 和 CID
     *  LAC，Location Area Code，位置区域码；
     *  CID，Cell Identity，基站编号；
     * @param type
     */
    private void setCellLocation(int type, CellLocation cl) {
        int lac = 0;
        int cellId = 0;
        switch (type){
            case 1:
                GsmCellLocation location;
                if(cl == null){
                    location = (GsmCellLocation) mTelephonyManager.getCellLocation();
                }else{
                    location = (GsmCellLocation)cl;
                }
                lac = location.getLac();
                cellId = location.getCid();
                break;
            case 2:
                CdmaCellLocation location1;
                if(cl == null){
                    location1 = (CdmaCellLocation) mTelephonyManager.getCellLocation();
                }else{
                    location1 = (CdmaCellLocation)cl;
                }
                lac = location1.getNetworkId();
                cellId = location1.getBaseStationId();
                break;
        }
        mTextView.append("【LAC_位置区域码】:\n" + lac + "\n");
        mTextView.append("【CELLID_位置区域码】:\n" + cellId + "\n");
    }

    @Override
    public void toRefresh() {
        mTextView.setText("");
        getData();
    }
}
