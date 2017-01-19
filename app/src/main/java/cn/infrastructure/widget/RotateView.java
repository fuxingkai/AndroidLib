package cn.infrastructure.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Frank on 2016/7/13.
 */
public class RotateView extends View {

    private static final int DEFAULT_SIZE_DP = 20;
    private static final long DEFAULT_DELAY_DRAW_TIME = 80L;

    private int mDefaultSizePixel;

    private int mRotateAngle = 30;
    private Bitmap mRotateBitmap;
    private Matrix mMatrix;

    /* package */ long mDelayDrawTime = DEFAULT_DELAY_DRAW_TIME;

    private DelayDrawRunnable mDelayDrawRunnable;

    public RotateView(Context context) {
        super(context);
        init();
    }

    public RotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDefaultSizePixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_SIZE_DP,
                getContext().getResources().getDisplayMetrics());

        mMatrix = new Matrix();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            startRotate();
        } else {
            stopRotate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRotate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startRotate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        startRotate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (withMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //set default with and height
            if (mRotateBitmap == null || mRotateBitmap.isRecycled()) {
                super.setMeasuredDimension(mDefaultSizePixel, mDefaultSizePixel);
            } else {
                int size = Math.max(mRotateBitmap.getWidth(), mRotateBitmap.getHeight());
                super.setMeasuredDimension(size, size);
            }

            return;
        }

        if (withMode == MeasureSpec.AT_MOST) {
            //set width as default
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            super.setMeasuredDimension(mDefaultSizePixel, heightSize);

            return;
        } 

        if (heightMode == MeasureSpec.AT_MOST) {
            //set height as default
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            super.setMeasuredDimension(widthSize, mDefaultSizePixel);

            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mMatrix.preRotate(mRotateAngle, mRotateBitmap.getWidth() / 2, mRotateBitmap.getHeight() / 2);
        canvas.drawBitmap(mRotateBitmap, mMatrix, null);
    }

    private void startRotate() {

        stopRotate();

        if (mRotateBitmap == null) {
            Log.w("LoadingView", "shareBitmap is null");
            return;
        }

        if (mRotateBitmap.isRecycled()) {
            Log.w("LoadingView", "shareBitmap is recycled");
            return;
        }

        int orgBitmapWidth = mRotateBitmap.getWidth();
        int orgBitmapHeight = mRotateBitmap.getHeight();

        int drawBitmapWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int drawBitmapHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        if (drawBitmapWidth == 0 || drawBitmapHeight == 0) {
            //may be it is gone state at the moment, do nothing here
            return;
        }

        float scale;
        if (orgBitmapWidth * drawBitmapHeight > orgBitmapHeight * drawBitmapWidth) {
            scale = drawBitmapWidth * 1f / orgBitmapWidth;
        } else {
            scale = drawBitmapHeight * 1f / orgBitmapHeight;
        }

        mMatrix.setTranslate(getWidth() / 2, getHeight() / 2);
        mMatrix.setScale(scale, scale);

        mDelayDrawRunnable = new DelayDrawRunnable();
        mDelayDrawRunnable.mContinueDraw = true;

        post(mDelayDrawRunnable);
    }

    private void stopRotate() {
        if (mDelayDrawRunnable == null) {
            return;
        }

        super.removeCallbacks(mDelayDrawRunnable);

        mDelayDrawRunnable.mContinueDraw = false;
        mDelayDrawRunnable = null;
    }

    public void setImageBitmap(Bitmap bitmap) {
        mRotateBitmap = bitmap;
        if (mRotateBitmap == null) {
            throw new IllegalArgumentException("shareBitmap can not be null");
        }

        if (mRotateBitmap.isRecycled()) {
            throw new RuntimeException("shareBitmap is recycled");
        }

        if (getWidth() > 0 && getHeight() > 0) {
            //check whether the view has added to window
            startRotate();
        }
    }

    public void setImageResource(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        if (drawable == null) {
            throw new RuntimeException("the resource with id " + resId + " is not found");
        }

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            setImageBitmap(bitmapDrawable.getBitmap());
        } else {
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, w, h);
            drawable.draw(canvas);

            setImageBitmap(bitmap);
        }
    }

    public void setRotateAngle(int angle) {
        if (angle <= 0) {
            return;
        }

        angle = angle % 360;
        if (angle == 0) {
            return;
        }

        mRotateAngle = angle;

        if (getWidth() > 0 && getHeight() > 0) {
            //check whether the view has added to window
            startRotate();
        }
    }

    public void setDelayDrawTime(long timeMills) {
        if (timeMills < 0) {
            return;
        }

        mDelayDrawTime = timeMills;
    }

    /* package */ class DelayDrawRunnable implements Runnable {

        public boolean mContinueDraw = true;

        @Override
        public void run() {
            if (mContinueDraw) {
                invalidate();

                postDelayed(this, mDelayDrawTime);
            }
        }
    }

}

