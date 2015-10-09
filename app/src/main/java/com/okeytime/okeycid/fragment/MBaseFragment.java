package com.okeytime.okeycid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by xi on 2015/10/9.
 */
public abstract class MBaseFragment extends Fragment {

   boolean isRunedAcCreated = false;
   @Override
   public void onActivityCreated(Bundle savedInstanceState) {
      isRunedAcCreated = true;
      super.onActivityCreated(savedInstanceState);
   }

   /**
    * 是否需要检查更新
    * @param isCheck
    */
   public void toRefresh(boolean isCheck){
      if(isRunedAcCreated){
         toRefresh();
      }
   }
   protected abstract void toRefresh();
}
