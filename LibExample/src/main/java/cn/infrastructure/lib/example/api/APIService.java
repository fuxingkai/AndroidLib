package cn.infrastructure.lib.example.api;

import java.util.HashMap;
import java.util.Map;

import cn.infrastructure.http.entity.Response;
import cn.infrastructure.lib.example.data.source.entity.OperInfoResp;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Frank on 2017/3/27.
 */
public interface APIService{
    public static final String API_PLACEHOLDER = "phoneintf/";


    /**
     * 查询个人收藏列表
     *
     * @param requestBody
     * @return
     */
    @POST(API_PLACEHOLDER + "common/doPhoneLogin")
    Observable<Response<OperInfoResp>> doPhoneLogin(@HeaderMap Map<String, String> headers, @Body RequestBody requestBody);

    /*上传文件*/
    @Multipart
    @POST(API_PLACEHOLDER +"common/doPhoneLogin")
    Observable<Response<HashMap>> uploadImage(@HeaderMap Map<String, String> headers,@Part("uid") RequestBody uid, @Part("auth_key") RequestBody  auth_key,
                                              @Part MultipartBody.Part file);

}
