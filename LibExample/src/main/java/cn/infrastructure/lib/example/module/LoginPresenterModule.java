package cn.infrastructure.lib.example.module;

import cn.infrastructure.lib.example.contract.LoginContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by linpei on 2016/8/14.
 */
@Module
public class LoginPresenterModule {

    private final LoginContract.View mView;

    public LoginPresenterModule(LoginContract.View view) {
        mView = view;
    }

    @Provides
    LoginContract.View provideLoginContractView(){
        return mView;
    }

}
