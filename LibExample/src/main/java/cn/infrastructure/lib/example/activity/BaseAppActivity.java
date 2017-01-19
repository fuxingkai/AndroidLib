package cn.infrastructure.lib.example.activity;

import butterknife.ButterKnife;
import cn.infrastructure.base.BaseActivity;

/**
 * Created by linpei on 2016/8/24.
 */
public abstract class BaseAppActivity extends BaseActivity {

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
    }
}
