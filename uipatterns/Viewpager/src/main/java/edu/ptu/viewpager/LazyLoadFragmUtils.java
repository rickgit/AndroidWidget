package edu.ptu.viewpager;

/**
 * Created by anshu.wang on 2017/7/26.
 */

public class LazyLoadFragmUtils {
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    public LazyLoadFragment lazyLoadFragment;

    private LazyLoadFragmUtils(LazyLoadFragment lazyLoadFragment) {
        this.lazyLoadFragment=lazyLoadFragment;
    }

    public static LazyLoadFragmUtils getInstance(LazyLoadFragment lazyLoadFragment) {
        return new LazyLoadFragmUtils(lazyLoadFragment);
    }

    public void onActivityCreated() {
        isViewInitiated = true;
        prepareFetchData();
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    private boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            lazyLoadFragment.fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    public static interface LazyLoadFragment {
        public void fetchData();
    }
}
