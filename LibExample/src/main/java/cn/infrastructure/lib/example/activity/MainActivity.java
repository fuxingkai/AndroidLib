package cn.infrastructure.lib.example.activity;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.infrastructure.base.BaseActivity;
import cn.infrastructure.http.MediaTypeConst;
import cn.infrastructure.http.PRequestBody;
import cn.infrastructure.http.RetrofitClient;
import cn.infrastructure.http.encryp.HearderType;
import cn.infrastructure.http.entity.Request;
import cn.infrastructure.http.listener.UlPListener;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2016/8/5.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_btn_login)
    Button btnLogin;
    @BindView(R.id.main_tv_copyTest)
    TextView tvTest;
    private int mTouchX;
    private int mTouchY;
    private final static int DEFAULT_SELECTION_LEN = 5;
    private RetrofitClient client = null;

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected void init() {
        client = AppApplication.instance.getRetrofitClient();
        postDefault();
        uploadeFile();
    }

    /**
     * 文件上传
     */
    private void uploadeFile(){
//        File file=new File("/storage/emulated/legacy/20161102.log");
        File file=new File("/storage/emulated/legacy/1486630393256.jpg");
//        File file=new File("/storage/emulated/legacy/1.log");
        RequestBody requestBody=RequestBody.create(MediaType.parse("image/jpeg"),file);
        MultipartBody.Part part= MultipartBody.Part.createFormData("file_name", file.getName(), new PRequestBody(requestBody,
                new UlPListener() {
                    @Override
                    public void onProgress(long currentBytesCount, long totalBytesCount) {
                        QLog.d("dddd"+currentBytesCount);
                        QLog.d("dddd"+totalBytesCount);
                        QLog.d("dddd"+currentBytesCount/totalBytesCount);
                    }
                }));
        RequestBody uid= RequestBody.create(MediaType.parse("text/plain"), "4811420");
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "cfed6cc8caad0d79ea56d917376dc4df");

        Map<String,String> headers = new HashMap<String,String>();
        headers.put(HearderType.BASE_AUTH,"bce-auth-v1/1214242fff");
        headers.put(HearderType.SECRET_ACCESS_KEY,"2343f");
        client.getService(APIService.class)
                .uploadImage(headers,uid,key,part)
                .compose(client.<HashMap>applySchedulers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<HashMap>bindToLifecycle())
                .subscribe(new Observer<HashMap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("cache","onSubscribe");
                    }

                    @Override
                    public void onNext(HashMap value) {
                        Log.i("cache","onNext");
                        Log.i("cache",JsonUtils.toJson(value));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("cache","onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("cache","onComplete");
                    }
                });
    }

    /**
     * post请求测试
     */
    private void postDefault() {
        LoginReq loginReq = new LoginReq();
        loginReq.operPwd = "aaa";//密码
        loginReq.operId = "dsfsdfs";//用户
        Request<LoginReq> request = new Request<LoginReq>(loginReq);

        Map<String,String> headers = new HashMap<String,String>();
        headers.put(HearderType.BASE_AUTH,"bce-auth-v1/1214242fff");
        headers.put(HearderType.SECRET_ACCESS_KEY,"2343f");
        client.getService(APIService.class)
                .doPhoneLogin(headers,(RequestBody.create(MediaTypeConst.MEDIATYPE_JSON, JsonUtils.toJson(request))))
                .compose(client.<OperInfoResp>applySchedulers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<OperInfoResp>bindToLifecycle())
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
