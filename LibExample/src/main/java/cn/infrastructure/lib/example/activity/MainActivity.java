package cn.infrastructure.lib.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.infrastructure.lib.example.R;
import cn.infrastructure.lib.example.contract.LoginContract;
import cn.infrastructure.rxbus.annotation.Produce;
import cn.infrastructure.rxbus.annotation.Subscribe;
import cn.infrastructure.rxbus.annotation.Tag;
import cn.infrastructure.rxbus.thread.EventThread;
import cn.infrastructure.utils.ToastUtil;

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
