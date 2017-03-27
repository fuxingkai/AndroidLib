package cn.infrastructure.lib.example.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.infrastructure.entity.Request;
import cn.infrastructure.http.MediaTypeConst;
import cn.infrastructure.http.RetrofitClient;
import cn.infrastructure.lib.example.AppApplication;
import cn.infrastructure.lib.example.R;
import cn.infrastructure.lib.example.api.APIService;
import cn.infrastructure.lib.example.contract.LoginContract;
import cn.infrastructure.lib.example.data.source.entity.LoginReq;
import cn.infrastructure.lib.example.data.source.entity.OperInfoResp;
import cn.infrastructure.log.QLog;
import cn.infrastructure.rxbus.annotation.Produce;
import cn.infrastructure.rxbus.annotation.Subscribe;
import cn.infrastructure.rxbus.annotation.Tag;
import cn.infrastructure.rxbus.thread.EventThread;
import cn.infrastructure.utils.ToastUtil;
import cn.infrastructure.utils.data.JsonUtils;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/5.
 */
public class MainActivity extends Activity implements LoginContract.View {

    private LoginContract.Presenter loginPresenter;

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
        LoginReq loginReq = new LoginReq();
        loginReq.operPwd = "aaa";//密码
        loginReq.operId = "dsfsdfs";//用户
        Request<LoginReq> request = new Request<LoginReq>(loginReq);

        QLog.i("sdfs","sdfsdf");
        RetrofitClient client = AppApplication.instance.getRetrofitClient();
        client.getService(APIService.class)
                .doPhoneLogin((RequestBody.create(MediaTypeConst.MEDIATYPE_JSON, JsonUtils.toJson(request))))
                .compose(client.<OperInfoResp>applySchedulers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OperInfoResp>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OperInfoResp operInfoResp) {

                    }
                });
    }

//    @Override
//    protected void init() {
//        QLog.init("LibExample").briefMode();
//        QLog.d("这是log测试");
//        JsonObject jsonObject=new JsonObject();
//        jsonObject.addProperty("ddddd",true);
//        jsonObject.addProperty("lsjaj","sdfeeeeee");
//        QLog.json(jsonObject.toString());


//        RxBus.get().register(this);

//        produceFoods();
//        loadingDlg.show("发送失败，短信费已用完，请联系管理员续费！");
//        String a = NumberUtils.foematInteger(0);
//        ToastUtil.showMidleToast(a + "");

//        tvTest.setFocusableInTouchMode(true);
//        tvTest.setFocusable(true);
//        tvTest.setClickable(true);
//        tvTest.setLongClickable(true);
//        tvTest.setMovementMethod(ArrowKeyMovementMethod.getInstance());
//        tvTest.setText(tvTest.getText(), TextView.BufferType.SPANNABLE);

//    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("foods")
            }
    )
    public void eatFoods(String food) {
        ToastUtil.show(food);
    }

    @Produce(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("foods")
            }
    )
    public String produceFoods() {
        return "苹果";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RxBus.get().unregister(this);
    }

    @OnClick(R.id.main_btn_login)
    public void doLogin() {

        TextView textView = null;
        textView.setText("");
    }

    @Override
    public void setupListeners() {

    }
}
