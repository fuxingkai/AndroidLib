package cn.infrastructure.lib.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.infrastructure.http.MediaTypeConst;
import cn.infrastructure.http.RetrofitClient;
import cn.infrastructure.http.encryp.HearderType;
import cn.infrastructure.http.entity.Request;
import cn.infrastructure.lib.example.AppApplication;
import cn.infrastructure.lib.example.R;
import cn.infrastructure.lib.example.api.APIService;
import cn.infrastructure.lib.example.data.source.entity.LoginReq;
import cn.infrastructure.lib.example.data.source.entity.OperInfoResp;
import cn.infrastructure.log.QLog;
import cn.infrastructure.utils.data.JsonUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2016/8/5.
 */
public class MainActivity extends Activity {

    @BindView(R.id.main_btn_login)
    Button btnLogin;
    @BindView(R.id.main_tv_copyTest)
    TextView tvTest;
    private int mTouchX;
    private int mTouchY;
    private final static int DEFAULT_SELECTION_LEN = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postTest();
    }

    /**
     * post请求测试
     */
    private void postTest() {
        LoginReq loginReq = new LoginReq();
        loginReq.operPwd = "aaa";//密码
        loginReq.operId = "dsfsdfs";//用户
        Request<LoginReq> request = new Request<LoginReq>(loginReq);

        QLog.i("sdfs","sdfsdf");
        RetrofitClient client = AppApplication.instance.getRetrofitClient();
        Map<String,String> headers = new HashMap<String,String>();
        headers.put(HearderType.BASE_AUTH,"bce-auth-v1/1214242fff");
        headers.put(HearderType.SECRET_ACCESS_KEY,"2343f");
        client.getService(APIService.class)
                .doPhoneLogin(headers,(RequestBody.create(MediaTypeConst.MEDIATYPE_JSON, JsonUtils.toJson(request))))
                .compose(client.<OperInfoResp>applySchedulers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OperInfoResp>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("cache","onSubscribe");
                    }

                    @Override
                    public void onNext(OperInfoResp value) {
                        Log.i("cache","onNext");
                        Log.i("cache",JsonUtils.toJson(value));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("cache","onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("cache","onComplete");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.main_btn_login)
    public void doLogin() {

        TextView textView = null;
        textView.setText("");
    }
}
