package cn.infrastructure.lib.example.api;

import java.util.Map;

import cn.infrastructure.http.entity.Response;
import cn.infrastructure.lib.example.data.source.entity.OperInfoResp;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

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
    @POST(API_PLACEHOLDER + "/common/doPhoneLogin")
    Observable<Response<OperInfoResp>> doPhoneLogin(@HeaderMap Map<String, String> headers, @Body RequestBody requestBody);
}
