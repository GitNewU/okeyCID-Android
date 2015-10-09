package com.okeytime.okeycid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.okeytime.okeycid.MainActivity;
import com.okeytime.okeycid.R;

/**
 * 其他信息
 * Created by xi on 2015/10/8.
 */
public class SecondFragment extends MBaseFragment{

    TextView textView;
    private TelephonyManager mTelephonyManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        textView = (TextView) rootView.findViewById(R.id.section_label);
        return rootView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("SecondFragment", "onActivityCreated");
        mTelephonyManager = ((MainActivity) getActivity()).mTelephonyManager;
        getData();
        super.onActivityCreated(savedInstanceState);
    }
    /**
     * 获取数据
     */
    private void getData() {
        setDeviceId();
        setPhoneNum();
        setNetType();
        setPhoneType();
        setGSMInfo();
    }

    /**
     * 获取基站信息
     */
    private void setGSMInfo() {
        String operator = mTelephonyManager.getNetworkOperator();
        /**通过operator获取 MCC 和MNC */
        int mcc = Integer.parseInt(operator.substring(0, 3));
        int mnc = Integer.parseInt(operator.substring(3));
        textView.append("【 MCC _ 移动国家代码 】\n");
        textView.append(mcc + "（中国的为460）\n\n");
        textView.append("【 MNC _ 移动网络号码 】\n");
        textView.append(mnc + "（移动为0，联通为1，电信为2）\n\n");

    }

    /**
     * 手机串号:GSM手机的 IMEI 和 CDMA手机的 MEID.
     */
    private void setDeviceId() {

        String deviceID = mTelephonyManager.getDeviceId();
        if(deviceID == null||deviceID.isEmpty()){
            textView .append("【当前手机设备识别码】\n_ 获取失败\n");
        }else{
            textView .append("【当前手机设备识别码】\n_ "+deviceID+"\n\n");
        }
    }
    /**
     * 手机号(有些手机号无法获取，是因为运营商在SIM中没有写入手机号)
     */
    private void setPhoneNum() {
        String tel = mTelephonyManager.getLine1Number();
        if( tel==null||tel.isEmpty() ){
            textView .append("【当前手机号码】\n_ 获取失败\n\n");
        }else{
            textView .append("【当前手机号码】\n" + tel + "\n\n");
        }
    }

    /**
     * 手机类型
     */
    private void setPhoneType() {
        int phoneType = mTelephonyManager.getPhoneType();
        textView .append("【当前手机类型】\n");
        switch (phoneType){
            case TelephonyManager.PHONE_TYPE_NONE :
                textView .append("_ 无信号\n\n");
                break;
            case TelephonyManager.PHONE_TYPE_GSM:
                textView .append("_ GSM信号\n\n");
                break;
            case TelephonyManager.PHONE_TYPE_CDMA :
                textView .append("_ CDMA信号\n\n");
                break;
        }
    }

    /**
     * 当前的网络类型
     */
    private void setNetType() {
        int netWorkType = mTelephonyManager.getNetworkType();
        textView .append("【当前使用网络类型】\n");
        switch (netWorkType){
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                textView .append("_ 网络类型未知\n\n");
                break;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                textView .append("_ GPRS网络\n\n");
                break;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                textView .append("_ EDGE网络\n\n");
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                textView .append("_ UMTS网络\n\n");
                break;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                textView .append("_ HSDPA网络\n\n");
                break;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                textView .append("_ HSUPA网络\n\n");
                break;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                textView .append("_ HSPA网络\n\n");
                break;
            case TelephonyManager.NETWORK_TYPE_CDMA   :
                textView .append("_ CDMA网络\n\n");
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_0 :
                textView .append("_ EVDO网络（revision 0）\n\n");
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                textView .append("_ EVDO网络（revision A）\n\n");
                break;
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                textView .append(netWorkType+"\n\n");
                break;
            default:
                textView .append("_ 1xRTT网络\n\n");
                break;
        }
    }

    @Override
    public void toRefresh() {
        textView.setText("");
        getData();
    }
}
