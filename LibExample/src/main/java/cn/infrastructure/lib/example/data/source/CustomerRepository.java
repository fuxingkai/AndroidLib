package cn.infrastructure.lib.example.data.source;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.infrastructure.lib.example.data.source.local.CustomerLocalDataSource;
import cn.infrastructure.lib.example.data.source.remote.CustomerRemoteDataSource;

/**
 * Created by linpei on 2016/8/12.
 */
@Singleton
public class CustomerRepository {

    private final CustomerRemoteDataSource mCustomerRemoteDataSource;
    private final CustomerLocalDataSource mCustomerLocalDataSource;

    @Inject
    CustomerRepository(@Remote CustomerRemoteDataSource customerRemoteDataSource, @Local CustomerLocalDataSource customerLocalDataSource) {
        mCustomerLocalDataSource = customerLocalDataSource;
        mCustomerRemoteDataSource = customerRemoteDataSource;
    }

}
