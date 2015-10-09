package com.okeytime.okeycid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.okeytime.okeycid.fragment.CELLSFragment;
import com.okeytime.okeycid.fragment.MBaseFragment;
import com.okeytime.okeycid.fragment.MainFragment;
import com.okeytime.okeycid.fragment.NeighboringFragment;
import com.okeytime.okeycid.fragment.SecondFragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    public TelephonyManager mTelephonyManager ;
    private ViewPager mViewPager;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }


    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);
    }

    private void initData(){
        mTelephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        MPhoneStateListener psl = new MPhoneStateListener();
        mTelephonyManager.listen(psl, 0);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //调到网络设置界面：
            Intent intent=new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS );
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
//                Toast.makeText(this,"已刷新数据",Toast.LENGTH_SHORT);
                toRefreshAllData();
                Snackbar.make(mViewPager, "已刷新", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            break;
        }
    }

    /**
     * 刷新所有Fragment的数据
     */
    public void toRefreshAllData(){
        for (MBaseFragment mf:
        mSectionsPagerAdapter.mFragmentLists) {
            mf.toRefresh(true);
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public ArrayList<MBaseFragment> mFragmentLists;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentLists = new ArrayList<MBaseFragment>();
            mFragmentLists.add(new MainFragment());
            mFragmentLists.add(new CELLSFragment());
            mFragmentLists.add(new NeighboringFragment());
            mFragmentLists.add(new SecondFragment());
        }

        @Override
        public MBaseFragment getItem(int position) {
            return mFragmentLists.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentLists.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CID信息";
                case 1:
                    return "小区信息";
                case 2:
                    return "相邻CID";
                case 3:
                    return "其他信息";
            }
            return null;
        }
    }

    /**
     * 更新监听
     */
    class MPhoneStateListener extends PhoneStateListener{

        @Override
        public void onCellLocationChanged(CellLocation location) {
            super.onCellLocationChanged(location);
            toRefreshAllData();
        }

        @Override
        public void onCellInfoChanged(List<CellInfo> cellInfo) {
            super.onCellInfoChanged(cellInfo);
            toRefreshAllData();
        }
    }
}
