package cn.infrastructure.widget;

import android.content.Context;
import android.view.View;
import android.view.animation.AFrankhaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

public class MaskView extends RelativeLayout {

	protected RelativeLayout targetView;
	protected boolean isShowing;
	protected long durationMillis;
	protected boolean canCancel;
	protected MaskListener maskListener;

	public MaskView(Context context, RelativeLayout targetView) {
		super(context);
		this.targetView = targetView;
		initialize(null);
	}

	public MaskView(Context context, RelativeLayout targetView,
			LayoutParams layoutParams) {
		super(context);
		this.targetView = targetView;
		initialize(layoutParams);
	}

	protected void initialize(LayoutParams layoutParams) {
		setBackgroundColor(0x88000000);
		setVisibility(View.GONE);
		targetView.addView(this, layoutParams == null ? new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
				: layoutParams);
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (canCancel) {
					hide();
				}
			}
		});
	}

	public void setDurationMillis(long durationMillis) {
		this.durationMillis = durationMillis;
	}

	public void setCanCancel(boolean can) {
		this.canCancel = can;
	}

	public void show() {
		if (isShowing)
			return;
		isShowing = true;
		clearAnimation();
		setVisibility(View.VISIBLE);
		AFrankhaAnimation an = new AFrankhaAnimation(0, 1);
		an.setDuration(durationMillis);
		startAnimation(an);
		if (maskListener != null)
			maskListener.onShow();
	}

	public void hide() {
		if (!isShowing)
			return;
		isShowing = false;
		clearAnimation();
		AFrankhaAnimation an = new AFrankhaAnimation(1, 0);
		an.setDuration(durationMillis);
		an.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				setVisibility(View.GONE);
			}
		});
		startAnimation(an);
		if (maskListener != null)
			maskListener.onHide();
	}

	public void setOnMaskListener(MaskListener listener) {
		this.maskListener = listener;
	}

	public interface MaskListener {
		void onShow();

		void onHide();
	}
}
