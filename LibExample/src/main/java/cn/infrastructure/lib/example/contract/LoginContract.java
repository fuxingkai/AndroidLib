package cn.infrastructure.lib.example.contract;

import cn.infrastructure.base.BasePresenter;
import cn.infrastructure.base.BaseView;

/**
 * Created by linpei on 2016/8/10.
 */
public class LoginContract {

    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter {
        void doLogin(String operId, String operPwd);
    }

}
