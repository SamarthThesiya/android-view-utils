package customView.fragmentManager;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import interfaces.FragmentStakeCommunicator;
import interfaces.OnFragmentChangeListener;


public class VuFragmentManager {

    FragmentManager fragmentManager;

    int frameId;

    FragmentStack fragmentStack;

    public VuFragmentManager(AppCompatActivity activity, int frameId, FragmentManager fragmentManager) {

        if (null == fragmentManager) {
            this.fragmentManager = activity.getSupportFragmentManager();
        }

        this.frameId = frameId;
    }

    public VuFragmentManager(AppCompatActivity activity, int frameId) {
        this.fragmentManager = activity.getSupportFragmentManager();
        this.frameId = frameId;
    }

    public FragmentStack getFragmentStack(int fragmentStackCount, FragmentStakeCommunicator fragmentStakeCommunicator, boolean callGetFragmentEveryTime) {
        fragmentStack = new FragmentStack(fragmentStackCount, fragmentStakeCommunicator, callGetFragmentEveryTime);
        return fragmentStack;
    }

    public FragmentStack getFragmentStack(int fragmentStackCount, FragmentStakeCommunicator fragmentStakeCommunicator) {
        fragmentStack = new FragmentStack(fragmentStackCount, fragmentStakeCommunicator, false);
        return fragmentStack;
    }

    public Fragment getCurrentFragment() {
        return (Fragment) fragmentManager.findFragmentById(frameId);
    }

    public void populateFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameId, fragment, tag);
        if (null != fragmentStack && !fragmentStack.isFirstFragment()) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }


    public class FragmentStack {

        Fragment[]                fragmentList;
        FragmentStakeCommunicator fragmentStakeCommunicator;
        int                       currentFragmentIndex = -1;
        boolean                   callGetFragmentEveryTime;
        private OnFragmentChangeListener onFragmentChangeListener;

        public FragmentStack(int fragmentStackCount, FragmentStakeCommunicator fragmentStakeCommunicator, boolean callGetFragmentEveryTime) {
            this.fragmentStakeCommunicator = fragmentStakeCommunicator;
            this.callGetFragmentEveryTime  = callGetFragmentEveryTime;
            fragmentList                   = new Fragment[fragmentStackCount];
        }

        private void updateFragmentStack(int index) {
            Fragment fragment = null;
            if (callGetFragmentEveryTime || fragmentList[index] == null) {
                fragment = fragmentStakeCommunicator.getFragmentFromByIndexNumber(index);

                if (null == fragment) {
                    return;
                }

                fragmentList[index] = fragment;

            } else {
                fragment = fragmentList[index];
            }

            populateFragment(fragment, fragment.getClass().toString());
        }

        public int getCurrentFragmentIndex() {
            return currentFragmentIndex;
        }

        public boolean isFirstFragment() {
            return currentFragmentIndex == 0;
        }

        public boolean isLastFragment() {
            return currentFragmentIndex == fragmentList.length - 1;
        }


        public void populateNextFragment() {
            if (isLastFragment()) {
                fragmentStakeCommunicator.fragmentStackOverflowed();
                return;
            }
            updateFragmentStack(++currentFragmentIndex);
            if (null != onFragmentChangeListener) {
                onFragmentChangeListener.fragmentChanged(currentFragmentIndex);
            }
        }

        public void popBackStack() {

            Activity baseActivity = (Activity) fragmentStakeCommunicator;
            if (isFirstFragment()) {
                boolean _continue = fragmentStakeCommunicator.fragmentStackUnderFlow();
                if (!_continue) {
                    return;
                }
            }
            baseActivity.onBackPressed();
            currentFragmentIndex--;
            if (null != onFragmentChangeListener) {
                onFragmentChangeListener.fragmentChanged(currentFragmentIndex);
            }
        }

        public void setBackButton(Button backButton) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popBackStack();
                }
            });
        }

        public void setNextButton(Button nextButton) {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    populateNextFragment();
                }
            });
        }

        public Fragment getFragment(int index) {
            return fragmentList[index];
        }

        public Fragment[] getFragments() {
            return fragmentList;
        }

        public void setOnFragmentChangeListener(OnFragmentChangeListener onFragmentChangeListener) {
            this.onFragmentChangeListener = onFragmentChangeListener;
        }
    }

}
