package edu.ptu.customview.scrollText;


import edu.ptu.customview.scrollText.adapter.OnItemSelectedListener;

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
