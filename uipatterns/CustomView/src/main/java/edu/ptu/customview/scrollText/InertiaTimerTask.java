package edu.ptu.customview.scrollText;

import java.util.TimerTask;
/**
 * 可以用 DecelerateInterpolator() 替换
 * @author anshu.wang
 * @time  2017-11-16 14:08:14
 * @version 1.0
 */
final class InertiaTimerTask extends TimerTask
{

	float runningVelocity;
	final float velocityY;
	final WheelView loopView;

	InertiaTimerTask(WheelView loopview, float velocityY)
	{
		super();
		loopView = loopview;
		this.velocityY = velocityY;
		runningVelocity = Integer.MAX_VALUE;
	}

	@Override
	public final void run()
	{
		if (runningVelocity == Integer.MAX_VALUE)
		{
			if (Math.abs(velocityY) > 2000F)
			{
				if (velocityY > 0.0F)
				{
					runningVelocity = 2000F;
				} else
				{
					runningVelocity = -2000F;
				}
			} else
			{
				runningVelocity = velocityY;
			}
		}
		if (Math.abs(runningVelocity) >= 0.0F && Math.abs(runningVelocity) <= 20F)
		{
			loopView.cancelFuture();
			loopView.handler.sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL);
			return;
		}
		int i = (int) ((runningVelocity * 10F) / 1000F);
		loopView.totalScrollY = loopView.totalScrollY - i;
		if (!loopView.isLoop)
		{
			float itemHeight = loopView.itemHeight;
			float top = (-loopView.initPosition) * itemHeight;
			float bottom = (loopView.getItemsCount() - 1 - loopView.initPosition) * itemHeight;
			if (loopView.totalScrollY - itemHeight * 0.3 < top)
			{
				top = loopView.totalScrollY + i;
			} else if (loopView.totalScrollY + itemHeight * 0.3 > bottom)
			{
				bottom = loopView.totalScrollY + i;
			}

			if (loopView.totalScrollY <= top)
			{
				runningVelocity = 40F;
				loopView.totalScrollY = (int) top;
			} else if (loopView.totalScrollY >= bottom)
			{
				loopView.totalScrollY = (int) bottom;
				runningVelocity = -40F;
			}
		}
		if (runningVelocity < 0.0F)
		{
			runningVelocity = runningVelocity + 20F;
		} else
		{
			runningVelocity = runningVelocity - 20F;
		}
		loopView.handler.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
	}

}
