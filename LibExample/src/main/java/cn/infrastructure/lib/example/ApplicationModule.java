package cn.infrastructure.lib.example;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by linpei on 2016/8/12.
 */
@Module
public class ApplicationModule {

    private final Context mContext;

    ApplicationModule(Context context){
        mContext=context;
    }

    @Provides
    Context provideContext(){
        return mContext;
    }

}
