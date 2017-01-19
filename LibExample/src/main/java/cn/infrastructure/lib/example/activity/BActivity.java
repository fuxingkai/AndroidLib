package cn.infrastructure.lib.example.activity;

import cn.infrastructure.lib.example.R;

/**
 * Created by Frank on 2016/9/6.
 */
public class BActivity extends BaseAppActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        loadingDlg.show();
    }
}
