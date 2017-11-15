package edu.ptu.customview.lib;


import edu.ptu.customview.lib.adapter.OnItemSelectedListener;

final class OnItemSelectedRunnable implements Runnable {
    final WheelView loopView;

    OnItemSelectedRunnable(WheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        for (OnItemSelectedListener listener : loopView.onItemSelectedListeners){

            listener.onItemSelected(loopView.getCurrentItem());
        }
    }
}
