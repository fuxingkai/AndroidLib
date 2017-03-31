package cn.infrastructure.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;

/**
 * Fragment的基类，与业务逻辑无关
 *
 * @author Frank 2016-7-1
 */
public abstract class BaseFragment extends RxFragment {

    protected View mFragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutResource() != 0) {
            mFragmentView = inflater.inflate(getLayoutResource(), container, false);
        }
        ButterKnife.bind(mFragmentView);
        return mFragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    protected abstract int getLayoutResource();

    protected abstract void init();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
