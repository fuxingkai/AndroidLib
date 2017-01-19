package cn.infrastructure.lib.example.component;

import cn.infrastructure.lib.example.activity.MainActivity;
import cn.infrastructure.lib.example.module.LoginPresenterModule;
import dagger.Component;

/**
 * Created by linpei on 2016/8/14.
 */
@Component(modules = LoginPresenterModule.class)
public interface LoginComponent {

    void inject(MainActivity mainActivity);

}
