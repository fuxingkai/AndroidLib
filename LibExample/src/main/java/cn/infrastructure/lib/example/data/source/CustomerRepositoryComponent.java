package cn.infrastructure.lib.example.data.source;

import javax.inject.Singleton;

import cn.infrastructure.lib.example.ApplicationModule;
import dagger.Component;

/**
 * Created by linpei on 2016/8/12.
 */
//@Singleton
//@Component(modules = {ApplicationModule.class})
public interface CustomerRepositoryComponent {

    CustomerRepository getCustomerRepository();

}
