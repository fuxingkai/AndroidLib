package frank.basis.http;

import java.io.IOException;

import frank.basis.http.encryp.AuthUtil;
import frank.basis.http.encryp.HearderType;
import frank.basis.http.factory.AuthFactory;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络鉴权拦截器
 * @author Frank
 * Create date on 2017/3/28 0028
 */

public class HttpAuthInterceptor implements Interceptor{


    /**
     * 拦截请求并进行处理
     * 
     * <pre>{@code
     * 最终生成认证字符串：
     * 认证字符串 = bce-auth-v1/{accessKeyId}/{timestamp}/{expirationPeriodInSeconds}/{signedHeaders}/{signature}
     * accessKeyId：服务器生成
     * timestamp：签名生效UTC时间，格式为yyyy-mm-ddThh:mm:ssZ，例如：2015-04-27T08:23:49Z，默认值为当前时间。
     * expirationPeriodInSeconds：签名有效期限，从timestamp所指定的时间开始计算，时间为秒，默认值为1800秒（30）分钟。
     * signedHeaders：签名算法中涉及到的HTTP头域列表。HTTP头域名字一律要求小写且头域名字之间用分号（;）分隔，如host;range;x-bce-date。列表按照字典序排列。当signedHeaders为空时表示取默认值。
     *
     * 生成Signature：
     * Signature = HMAC-SHA256-HEX(SigningKey, CanonicalRequest)
     *
     * 生成SigningKey：
     * SigningKey = HMAC-SHA256-HEX(sk, authStringPrefix)，
     * sk为用户的Secret Access Key,即后台返回来的一个key。
     * authStringPrefix代表认证字符串的前缀部分，即：bce-auth-v1/{accessKeyId}/{timestamp}/{expirationPeriodInSeconds}。
     *
     * 生成CanonicalRequest：
     * CanonicalRequest = HTTP Method + "\n" + CanonicalURI + "\n" + CanonicalQueryString + "\n" + CanonicalHeaders
     * HTTP Method：指HTTP协议中定义的GET、PUT、POST等请求，必须使用全大写的形式，
     * 常用有五种：GET，POST，PUT，DELETE，HEAD
     * CanonicalURI：是对URL中的绝对路径进行编码后的结果。要求绝对路径必须以“/”开头，不以“/”开头的需要补充上，空路径为“/”，即CanonicalURI = UriEncodeExceptSlash(Path)。
     * 如：若URL为https://bos.cn-n1.baidubce.com/example/测试，则其URL Path为/example/测试，将之规范化得到CanonicalURI ＝ /example/%E6%B5%8B%E8%AF%95。
     * CanonicalQueryString：对于URL中的Query String（Query String即URL中“？”后面的“key1 = valve1 & key2 = valve2 ”字符串）进行编码后的结果。
     * 编码方法为：
     * 1.将Query String根据&拆开成若干项，每一项是key=value或者只有key的形式。
     * 2.对拆开后的每一项进行如下处理：
     *   对于key是authorization，直接忽略。
     *   对于只有key的项，转换为UriEncode(key) + "="的形式。
     *   对于key=value的项，转换为 UriEncode(key) + "=" + UriEncode(value) 的形式。这里value可以是空字符串。
     * 3.将上面转换后的所有字符串按照字典顺序排序。
     * 4.将排序后的字符串按顺序用 & 符号链接起来。
     * 如：若URL为https://bos.cn-n1.baidubce.com/example?text&text1=测试&text10=test，在这个例子中Query String是 text&text1=测试&text10=test。
     * 1.根据 & 拆开成 text 、text1=测试 和 text10=test 三项。
     * 2.对每一项进行处理：
     *     text => UriEncode("text") + "=" => text=
     *     text1=测试 => UriEncode("text1") + "=" + UriEncode("测试") => text1=%E6%B5%8B%E8%AF%95
     *     text10=test => UriEncode("text10") + "=" + UriEncode("test")=> text10=test
     * 3.对 text= 、 text1=%E6%B5%8B%E8%AF%95 和 text10=test 按照字典序进行排序。它们有共同前缀text，但是=的ASCII码比所有数字的ASCII码都要大，因此 text1=%E6%B5%8B%E8%AF%95 和 text10=test 排在 text= 的前面。同样，text10=test 要排在 text1=%E6%B5%8B%E8%AF%95 之前。最终结果是 text10=test 、text1=%E6%B5%8B%E8%AF%95 、text= 。
     * 4.把排序好的三项 text10=test 、 text1=%E6%B5%8B%E8%AF%95 、 text= 用&连接起来得到 text10=test&text1=%E6%B5%8B%E8%AF%95&text=。
     * 说明：
     * 在这个特殊构造的例子里，我们展示了如何处理只有key的项，非英文的value，以及数字和=进行排序。在实际的BCE API中，因为参数起名是规范的，基本不会遇到这样的排序。正常的排序结果和只按照 key进行排序是完全一致的。算法中有这个约束主要是出于定义严密性的考虑。*
     * CanonicalHeaders：对HTTP请求中的Header部分进行选择性编码的结果。
     * 您可以自行决定哪些Header 需要编码。百度云API的唯一要求是Host域必须被编码。大多数情况下，
     * 我们推荐您对以下Header进行编码：Host,Content-Length,Content-Type,Content-MD5,所有以 x-bce- 开头的Header
     *
     * 如果这些Header没有全部出现在您的HTTP请求里面，那么没有出现的部分无需进行编码。
     * 如果您按照我们的推荐范围进行编码，那么认证字符串中的 {signedHeaders} 可以直接留空，无需填写。
     * 您也可以自行选择自己想要编码的Header。如果您选择了不在推荐范围内的Header进行编码，或者您的HTTP请求包含了推荐范围内的Header但是您选择不对它进行编码，那么您必须在认证字符串中填写 {signedHeaders} 。填写方法为，把所有在这一阶段进行了编码的Header名字转换成全小写之后按照字典序排列，然后用分号（;）连接。
     * 选择哪些Header进行编码不会影响API的功能，但是如果选择太少则可能遭到中间人攻击。
     * 对于每个要编码的Header进行如下处理：
     *    将Header的名字变成全小写。
     *    将Header的值去掉开头和结尾的空白字符。
     *    经过上一步之后值为空字符串的Header忽略，其余的转换为 UriEncode(name) + ":" + UriEncode(value) 的形式。
     *    把上面转换后的所有字符串按照字典序进行排序。
     *    将排序后的字符串按顺序用\n符号连接起来得到最终的CanonicalQueryHeaders。
     *    注意：很多发送HTTP请求的第三方库，会添加或者删除你指定的header（例如：某些库会删除content-length:0这个header），如果签名错误，请检查你您真实发出的http请求的header，看看是否与签名时的header一样。
     * 这里不举例
     * }</pre>
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {

        /**
         * 获得请求
         */
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        RequestBody body = request.body();

        if (body != null) {
            /**
             * 视请求头是否要全部进行而决定，如果要全部鉴权，该部分代码需要注释掉，并且用addNetworkInterceptor {@link okhttp3.OkHttpClient.Builder#addNetworkInterceptor(Interceptor)}
             * 否则放开注释，只进行CONTENT_TYPE；CONTENT_LENGTH；CONTENT_MD5；HOST这四个请求头进行鉴权 {@link HearderType#CONTENT_TYPE}
             * 具体要看{@link AuthFactory#createOkHttpClient()}
             */
//            MediaType contentType = body.contentType();
//
//            if (contentType != null) {
//                requestBuilder.header(HearderType.CONTENT_TYPE, contentType.toString());
//            }
//
//            long contentLength = body.contentLength();
//
//            if (contentLength != -1) {
//                requestBuilder.header(HearderType.CONTENT_LENGTH, Long.toString(contentLength));
//                requestBuilder.removeHeader(HearderType.TRANSFER_ENCODING);
//            } else {
//                requestBuilder.header(HearderType.TRANSFER_ENCODING, "chunked");
//                requestBuilder.removeHeader(HearderType.CONTENT_LENGTH);
//            }

            requestBuilder.header(HearderType.CONTENT_MD5,AuthUtil.get128MD5ToBase64(body));
        }

        if (request.header(HearderType.HOST) == null) {
            requestBuilder.header(HearderType.HOST, hostHeader(request.url(), false));
        }

        request = requestBuilder.build();

        /**
         * 鉴权字符
         */
        String authStr = AuthUtil.getAuthStr(request);

        /**
         * 重新生成请求体
         */
        request = request
                .newBuilder()
                .removeHeader(HearderType.BASE_AUTH)
                .removeHeader(HearderType.SECRET_ACCESS_KEY)
                .addHeader(HearderType.AUTHORIZATION,authStr)
                .build();

        Response response = chain.proceed(request);

        return response;
    }

    /**
     * 获得请求头Host的值
     * @param url
     * @param includeDefaultPort
     * @return
     */
    public static String hostHeader(HttpUrl url, boolean includeDefaultPort) {
        String host = url.host().contains(":")
                ? "[" + url.host() + "]"
                : url.host();
        return includeDefaultPort || url.port() != HttpUrl.defaultPort(url.scheme())
                ? host + ":" + url.port()
                : host;
    }

}
