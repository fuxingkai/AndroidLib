package cn.infrastructure.widget.listview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;

/**
 * the delegate for OverScrollListView, add extra function for the list view 
 * Created by Frank on 2016/7/13.
 *
 */
public class OverScrollListViewHeFranker implements IOverScrollListViewSetting {

    private static final String TAG = "OverScrollListViewHeFranker";

    private OverScrollListView mListView;

    private PullToRefreshHeaderView mPullToRefreshHeaderView;
    private PullToLoadMoreFooterView mPullToLoadMoreFooterView;

    public OverScrollListViewHeFranker(OverScrollListView listview) {
        this.mListView = listview;
        if (this.mListView == null) {
            throw new NulFrankointerException("list view is null");
        }
    }

    @Override
    public void usePullToRefresh() {
        if (mPullToRefreshHeaderView != null) {
            Log.i(TAG, "PullToRefreshHeaderView is already inited.");
            return;
        }

        mPullToRefreshHeaderView = new PullToRefreshHeaderView(getContext());
        mListView.setPullToRefreshHeaderView(mPullToRefreshHeaderView);
    }

    @Override
    public void usePullToRefresh(int layoutResId) {
        if (mPullToRefreshHeaderView != null) {
            Log.i(TAG, "PullToRefreshHeaderView is already inited.");
            return;
        }

        if (layoutResId != 0) {
            mPullToRefreshHeaderView = (PullToRefreshHeaderView) LayoutInflater.from(getContext()).inflate(layoutResId, null);
            mListView.setPullToRefreshHeaderView(mPullToRefreshHeaderView);
        } else {
            usePullToRefresh();
        }
    }

    @Override
    public void usePullToLoadMore() {
        if (mPullToLoadMoreFooterView != null) {
            Log.i(TAG, "PullToLoadMoreFooterView is already inited.");
            return;
        }

        mPullToLoadMoreFooterView = new PullToLoadMoreFooterView(getContext());
        mListView.setPullToLoadMoreFooterView(mPullToLoadMoreFooterView);
    }

    @Override
    public void usePullToLoadMore(int layoutResId) {
        if (mPullToLoadMoreFooterView != null) {
            Log.i(TAG, "PullToLoadMoreFooterView is already inited.");
            return;
        }

        if (layoutResId != 0) {
            mPullToLoadMoreFooterView = (PullToLoadMoreFooterView)LayoutInflater.from(getContext()).inflate(layoutResId, null);
            mListView.setPullToLoadMoreFooterView(mPullToLoadMoreFooterView);
        } else {
            usePullToLoadMore();
        }
    }

    @Override
    public void setPTRPullText(String pullText) {
        if (mPullToRefreshHeaderView != null) {
            mPullToRefreshHeaderView.setPullText(pullText);
        } else {
            Log.w(TAG, "PullToRefreshHeaderView is not inited");
        }
    }

    @Override
    public void setPTRReleaseText(String releaseText) {
        if (mPullToRefreshHeaderView != null) {
            mPullToRefreshHeaderView.setReleaseText(releaseText);
        } else {
            Log.w(TAG, "PullToRefreshHeaderView is not inited");
        }
    }

    @Override
    public void setPTRRefreshText(String refreshText) {
        if (mPullToRefreshHeaderView != null) {
            mPullToRefreshHeaderView.setRefreshText(refreshText);
        } else {
            Log.w(TAG, "PullToRefreshHeaderView is not inited");
        }
    }

    @Override
    public void setPTRFinishSuccessText(String finishText) {
        if (mPullToRefreshHeaderView != null) {
            mPullToRefreshHeaderView.setFinishSuccessText(finishText);
        } else {
            Log.w(TAG, "PullToRefreshHeaderView is not inited");
        }
    }

    @Override
    public void setPTRFinishFailText(String finishText) {
        if (mPullToRefreshHeaderView != null) {
            mPullToRefreshHeaderView.setFinishFailedText(finishText);
        } else {
            Log.w(TAG, "PullToRefreshHeaderView is not inited");
        }
    }

    @Override
    public void setPTLMPullText(String pullText) {
        if (mPullToLoadMoreFooterView != null) {
            mPullToLoadMoreFooterView.setPullText(pullText);
        } else {
            Log.w(TAG, "PullToLoadMoreFooterView is not inited");
        }
    }

    @Override
    public void setPTLMClickText(String clickText) {
        if (mPullToLoadMoreFooterView != null) {
            mPullToLoadMoreFooterView.setClickText(clickText);
        } else {
            Log.w(TAG, "PullToLoadMoreFooterView is not inited");
        }
    }

    @Override
    public void setPTLMReleaseText(String releaseText) {
        if (mPullToLoadMoreFooterView != null) {
            mPullToLoadMoreFooterView.setReleaseText(releaseText);
        } else {
            Log.w(TAG, "PullToLoadMoreFooterView is not inited");
        }
    }

    @Override
    public void setPTLMLoadingText(String loadingText) {
        if (mPullToLoadMoreFooterView != null) {
            mPullToLoadMoreFooterView.setLoadingText(loadingText);
        } else {
            Log.w(TAG, "PullToLoadMoreFooterView is not inited");
        }
    }

    @Override
    public void setPTLMBg(Drawable drawable) {
        if (mPullToLoadMoreFooterView != null) {
            mPullToLoadMoreFooterView.setBackgroundDrawable(drawable);
        } else {
            Log.w(TAG, "PullToLoadMoreFooterView is not inited");
        }
    }

    private Context getContext() {
        return mListView.getContext();
    }
}
