package cn.infrastructure.lib.example.api;

import cn.infrastructure.entity.Response;
import cn.infrastructure.lib.example.data.source.entity.OperInfoResp;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

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
    Observable<Response<OperInfoResp>> doPhoneLogin(@Body RequestBody requestBody);
}
