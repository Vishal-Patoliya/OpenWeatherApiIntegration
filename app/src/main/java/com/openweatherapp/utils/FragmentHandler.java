package com.openweatherapp.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.openweatherapp.utils.Common;

/**
 * @author vishal
 * Use for manage Fragments ither Adding or removing.
 */
public class FragmentHandler {

    public enum ANIMATION_TYPE {
        SLIDE_UP_TO_DOWN, SLIDE_DOWN_TO_UP, SLIDE_LEFT_TO_RIGHT, SLIDE_RIGHT_TO_LEFT, NONE
    }

    public FragmentManager getmanager()
    {
        return this.getmanager();
    }
    /**
     * Add Fragment
     *
     * @param fragment
     * @param fragmentToTarget
     * @param bundle
     * @param isAddToBackStack
     * @param tag
     * @param requestcode
     * @param animationType
     */

    public void addFragment(Activity activity, FragmentManager fragmentManager, int frameId, Fragment fragment, Fragment fragmentToTarget, Bundle bundle, boolean isAddToBackStack, String tag, int requestcode, ANIMATION_TYPE animationType) {

        //If Already fragment is open so not call again

        if (isOpenFragment(activity, tag)) {
            android.support.v4.app.FragmentTransaction fragTrans = fragmentManager.beginTransaction();
                        //Pass data between fragment
            if (bundle != null) {
                fragment.setArguments(bundle);
            }

            //Set Target fragment
            if (fragmentToTarget != null) {
                fragment.setTargetFragment(fragmentToTarget, requestcode);
            }

            //If you need to add back stack so put here
            if (isAddToBackStack) {
                fragTrans.addToBackStack(tag);
            }

            fragTrans.add(frameId, fragment, tag);
            fragTrans.commit();
        }
    }

    /**
     * Replace Fragment
     *
     * @param fragment
     * @param fragmentToTarget
     * @param bundle
     * @param isAddToBackStack
     * @param tag
     * @param requestcode      for get value from previous fragment
     * @param animationType
     */
    public void replaceFragment(Activity activity, FragmentManager fragmentManager, int frameId, Fragment fragment, Fragment fragmentToTarget, Bundle bundle, boolean isAddToBackStack, String tag, int requestcode, ANIMATION_TYPE animationType) {

        //If Already fragment is open so not call again
        if (isOpenFragment(activity, tag)) {
            android.support.v4.app.FragmentTransaction fragTrans = fragmentManager.beginTransaction();
            //Pass data between fragment
            if (bundle != null) {
                fragment.setArguments(bundle);
            }

            //Set Target fragment
            if (fragmentToTarget != null) {
                fragment.setTargetFragment(fragmentToTarget, requestcode);
            }

            //If you need to add back stack so put here
            if (isAddToBackStack) {
                fragTrans.addToBackStack(tag);
            }

            fragTrans.replace(frameId, fragment, tag);
            fragTrans.commit();
        }
    }

    //Check fragment is already opened or not
    public boolean isOpenFragment(Activity activity, String fragmentName) {
        try
        {
            Common.getInstance().hideSoftKeyboard(activity);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        try {
            if (!activity.getFragmentManager().findFragmentByTag(fragmentName).isVisible()) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }
}