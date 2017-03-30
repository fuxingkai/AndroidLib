package cn.infrastructure.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Fragment的基类，与业务逻辑无关
 *
 * @author Frank 2016-7-1
 */
public abstract class BaseFragment extends Fragment {

    protected View mFragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutResource() != 0) {
            mFragmentView = inflater.inflate(getLayoutResource(), container, false);
        }
        bindView();
        return mFragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    protected abstract int getLayoutResource();

    protected abstract void bindView();

    protected abstract void init();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
