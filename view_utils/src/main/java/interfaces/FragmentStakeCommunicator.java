package interfaces;

import androidx.fragment.app.Fragment;

public interface FragmentStakeCommunicator {
    public abstract Fragment getFragmentFromByIndexNumber(int index);
    public abstract void fragmentStackOverflowed();

    /**
     * false => Want to restrict user to exit the wizard? Send false
     * true => User will exit the wizard as normal behaviour of onBackPressed()
     */
    public abstract boolean fragmentStackUnderFlow();
}
