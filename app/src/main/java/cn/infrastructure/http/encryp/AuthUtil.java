package cn.infrastructure.http.encryp;

import android.text.TextUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import cn.infrastructure.http.CommonConst;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * 鉴权工具类
 * @author Frank
 * @date: 2017/3/28 0028
 */

public class AuthUtil {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static BitSet URI_UNRESERVED_CHARACTERS = new BitSet();

    private static BitSet CONTENT_UNRESERVED_CHARACTERS = new BitSet();

    private static String[] PERCENT_ENCODED_STRINGS = new String[256];

    static {
        for (int i = 'a'; i <= 'z'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
            CONTENT_UNRESERVED_CHARACTERS.set(i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
            CONTENT_UNRESERVED_CHARACTERS.set(i);
        }
        for (int i = '0'; i <= '9'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
            CONTENT_UNRESERVED_CHARACTERS.set(i);
        }

        URI_UNRESERVED_CHARACTERS.set('-');
        URI_UNRESERVED_CHARACTERS.set('.');
        URI_UNRESERVED_CHARACTERS.set('_');
        URI_UNRESERVED_CHARACTERS.set('~');

        CONTENT_UNRESERVED_CHARACTERS.set('-');
        CONTENT_UNRESERVED_CHARACTERS.set('_');
        CONTENT_UNRESERVED_CHARACTERS.set('.');
        CONTENT_UNRESERVED_CHARACTERS.set('*');

        for (int i = 0; i < PERCENT_ENCODED_STRINGS.length; ++i) {
            PERCENT_ENCODED_STRINGS[i] = String.format("%%%02X", i);
        }
    }

    /***
     * 获得authAstring
     * accessKeyId：服务器生成，建议bce-auth-v1/{accessKeyId}放入请求头 {@link HearderType#BASE_AUTH}
     * @return
     */
    public static String getBaseAuth(Request request) {
        return request.header(HearderType.BASE_AUTH);
    }

    /***
     *
     * 生成认证字符串：
     * 认证字符串 = bce-auth-v1/{accessKeyId}/{timestamp}/{expirationPeriodInSeconds}/{signedHeaders}/{signature}
     * accessKeyId：服务器生成，建议bce-auth-v1/{accessKeyId}放入请求头 {@link HearderType#BASE_AUTH}
     * timestamp：签名生效UTC时间，格式为yyyy-mm-ddThh:mm:ssZ，例如：2015-04-27T08:23:49Z，默认值为当前时间。
     * expirationPeriodInSeconds：签名有效期限，从timestamp所指定的时间开始计算，时间为秒，默认值为1800秒（30）分钟。
     * signedHeaders：签名算法中涉及到的HTTP头域列表。HTTP头域名字一律要求小写且头域名字之间用分号（;）分隔，如host;range;x-bce-date。列表按照字典序排列。当signedHeaders为空时表示取默认值。
     *
     * @return
     */
    public static String getAuthStr(Request request) {
        StringBuilder authSb = new StringBuilder();

        String baseAuth = getBaseAuth(request);

        String timestamp = getUTC();

        long expireInSeconds = getExpireInSeconds();

        String signedHeaders = getSignedHeaders(request);

        authSb.append(baseAuth)
                .append("/")
                .append(timestamp)
                .append("/")
                .append(expireInSeconds);

        /**
         * 获得SigningKey
         */
        String signingKey = getSigningKey(request,authSb.toString());

        String signature = getSignature(request,signingKey);

        authSb.append("/")
                .append(signedHeaders)
                .append("/")
                .append(signature);

        return authSb.toString();
    }

    /***
     * 签名有效期限
     * 从timestamp所指定的时间开始计算，时间为秒，默认值为1800秒（30）分钟。
     * @return
     */
    public static long getExpireInSeconds() {
        return CommonConst.EXPIRE_IN_SECONDS;
    }

    /***
     * 获得格林时间
     * 签名生效UTC时间，格式为yyyy-mm-ddThh:mm:ssZ，例如：2015-04-27T08:23:49Z，默认值为当前时间。
     * @return
     */
    public static String getUTC() {
        Calendar cd = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.US);

        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT

        return sdf.format(cd.getTime());
    }


    /**
     * 128位md5签名base64
     * @param requestBody
     * @return
     */
    public static String get128MD5ToBase64(RequestBody requestBody) {
        Buffer buffer = new Buffer();

        try {
            requestBody.writeTo(buffer);
            return buffer.md5().base64();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获得请求头并且排序
     * @param request
     * @return
     * @throws Exception
     */
    public static SortedMap<String, String> getSortHeaderParams(Request request)
            throws Exception {
        SortedMap<String, String> headParameters = new TreeMap<String, String>();

        Headers headers = request.headers();

        /**
         * 获得请求头的键
         */
        Set<String> names = headers.names();

        /**
         * 遍历请求头并排序
         */
        for(String key:names){
            if(HearderType.BASE_AUTH.equals(key)||HearderType.SECRET_ACCESS_KEY.equals(key)){
                continue;
            }
            if(TextUtils.isEmpty(headers.get(key))){
                headParameters.put(key,"");
            }else{
                headParameters.put(key,headers.get(key));
            }
        }

        return headParameters;
    }

    /**
     * 获得SignedHeaders
     * signedHeaders：签名算法中涉及到的HTTP头域列表。HTTP头域名字一律要求小写且头域名字之间用分号（;）分隔，如host;range;x-bce-date。列表按照字典序排列。当signedHeaders为空时表示取默认值。
     * @param request
     * @return
     */
    public static String getSignedHeaders(Request request) {

        LinkedList<String> headerStrings = new LinkedList<String>();

        StringBuilder sbSignedHeader = new StringBuilder();

        Headers headers = request.headers();

        /**
         * 获得请求头的键
         */
        Set<String> names = headers.names();

        /**
         * 遍历请求头并排序
         */
        for(String key:names){
            if(TextUtils.isEmpty(key)||HearderType.BASE_AUTH.equals(key)||HearderType.SECRET_ACCESS_KEY.equals(key)){
                continue;
            }
            headerStrings.add(uriEncode(key.toLowerCase()));
        }
        Collections.sort(headerStrings);


        /**
         * 遍历请求头列表并且拼接
         */
        for (int i = 0; i < headerStrings.size(); i++) {

            sbSignedHeader.append(headerStrings.get(i));

            if (i != headerStrings.size() - 1) {
                sbSignedHeader.append(";");
            }
        }

        return sbSignedHeader.toString();
    }

    /***
     * 生成CanonicalRequest
     * CanonicalRequest = HTTP Method + "\n" + CanonicalURI + "\n" + CanonicalQueryString + "\n" + CanonicalHeaders
     * @return
     */
    public static String getCanonicalRequest(Request request) {
        StringBuilder sbCanonicalRequest = new StringBuilder();

        String httpMethod = getHttpMethod(request);

        String canonicalURI = getCanonicalURI(request);

        String canonicalQueryStr = getCanonicalQueryString(request);

        String canonicalHeaders = getCanonicalHeaders(request);

        sbCanonicalRequest.append(httpMethod)
                .append("\n")
                .append(canonicalURI)
                .append("\n")
                .append(canonicalQueryStr)
                .append("\n")
                .append(canonicalHeaders);

        return sbCanonicalRequest.toString();
    }

    /**
     * 获得 HTTP Method
     * HTTP Method：指HTTP协议中定义的GET、PUT、POST等请求，必须使用全大写的形式。
     * 常用所涉及的HTTP Method有五种：
     * GET
     * POST
     * PUT
     * DELETE
     * HEAD
     * @param request
     * @return
     */
    public static String getHttpMethod(Request request){
        return request.method().toUpperCase(Locale.US);
    }

    /**
     * 获得CanonicalURI
     * CanonicalURI :是对URL中的绝对路径进行编码后的结果。要求绝对路径必须以“/”开头，不以“/”开头的需要补充上，空路径为“/”，即CanonicalURI = UriEncodeExceptSlash(Path)。
     * 如：若URL为https://bos.cn-n1.baidubce.com/example/测试，则其URL Path为/example/测试，将之规范化得到CanonicalURI ＝ /example/%E6%B5%8B%E8%AF%95。
     * @param request
     * @return
     */
    public static String getCanonicalURI(Request request) {
        URI uri = request.url().uri();
        String uriPath = uri.getPath();
        if (uriPath == null) {
            return "/";
        } else if (uriPath.startsWith("/")) {
            return normalizePath(uriPath);
        } else {
            return "/" + normalizePath(uriPath);
        }
    }


    /**
     * UriEncodeExceptSlash()与UriEncode()
     * 类似，区别是斜杠（/）不做编码。一个简单的实现方式是先调用UriEncode()，然后把结果中所有的`%2F`都替换为`/`
     *
     * @param path
     * @return
     */
    public static String normalizePath(String path) {
        return uriEncode(path).replace("%2F", "/");
    }

    /**
     * RFC
     * 3986规定，"URI非保留字符"包括以下字符：字母（A-Z，a-z）、数字（0-9）、连字号（-）、点号（.）、下划线（_)、波浪线（~）
     * ，算法实现如下： 1. 将字符串转换成UTF-8编码的字节流 2. 保留所有“URI非保留字符”原样不变 3. 对其余字节做一次RFC
     * 3986中规定的百分号编码（Percent-encoding），即一个“%”后面跟着两个表示该字节值的十六进制字母，字母一律采用大写形式。
     *
     * @param value
     * @return
     */
    public static String uriEncode(String value) {
        try {
            StringBuilder builder = new StringBuilder();
            for (byte b : value.getBytes(DEFAULT_ENCODING)) {
                if (URI_UNRESERVED_CHARACTERS.get(b & 0xFF)) {
                    builder.append((char) b);
                } else {
                    builder.append(PERCENT_ENCODED_STRINGS[b & 0xFF]);
                }
            }
            return builder.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获得CanonicalQueryString
     * CanonicalQueryString：对于URL中的Query String（Query String即URL中“？”后面的“key1 = valve1 & key2 = valve2 ”字符串）进行编码后的结果。
     * 编码方法为：
     * 将Query String根据&拆开成若干项，每一项是key=value或者只有key的形式。
     * 对拆开后的每一项进行如下处理：
     * 对于key是authorization，直接忽略。
     * 对于只有key的项，转换为UriEncode(key) + "="的形式。
     * 对于key=value的项，转换为 UriEncode(key) + "=" + UriEncode(value) 的形式。这里value可以是空字符串。
     * 将上面转换后的所有字符串按照字典顺序排序。
     * 将排序后的字符串按顺序用 & 符号链接起来。
     * 相关举例：
     * 若URL为https://bos.cn-n1.baidubce.com/example?text&text1=测试&text10=test，在这个例子中Query String是 text&text1=测试&text10=test。
     * 根据 & 拆开成 text 、text1=测试 和 text10=test 三项。
     * 对每一项进行处理：
     * text => UriEncode("text") + "=" => text=
     * text1=测试 => UriEncode("text1") + "=" + UriEncode("测试") => text1=%E6%B5%8B%E8%AF%95
     * text10=test => UriEncode("text10") + "=" + UriEncode("test")=> text10=test
     * 对 text= 、 text1=%E6%B5%8B%E8%AF%95 和 text10=test 按照字典序进行排序。它们有共同前缀text，但是=的ASCII码比所有数字的ASCII码都要大，因此 text1=%E6%B5%8B%E8%AF%95 和 text10=test 排在 text= 的前面。同样，text10=test 要排在 text1=%E6%B5%8B%E8%AF%95 之前。最终结果是 text10=test 、text1=%E6%B5%8B%E8%AF%95 、text= 。
     * 把排序好的三项 text10=test 、 text1=%E6%B5%8B%E8%AF%95 、 text= 用&连接起来得到 text10=test&text1=%E6%B5%8B%E8%AF%95&text=。
     * 说明：
     * 在这个特殊构造的例子里，我们展示了如何处理只有key的项，非英文的value，以及数字和=进行排序。在实际的BCE API中，因为参数起名是规范的，基本不会遇到这样的排序。正常的排序结果和只按照 key进行排序是完全一致的。算法中有这个约束主要是出于定义严密性的考虑。*
     * @param request
     * @return
     */
    public static String getCanonicalQueryString(Request request) {
        String url = request.url().toString();
        String urlStr = "";

        if (url.contains("?")) {
            urlStr = url.substring(url.indexOf("?") + 1, url.length());
        }

        if (urlStr.equals("")) {
            return "";
        }

        String[] parametersStrings = urlStr.split("&");

        SortedMap<Object, Object> uriEncodeParameters = new TreeMap<Object, Object>();

        if (parametersStrings == null || parametersStrings.length == 0) {
            return "";
        }

        for (int i = 0; i < parametersStrings.length; i++) {

            String[] strings = parametersStrings[i].split("=");

            if (strings[0] != null && !strings[0].equals("")) {

                if (strings.length > 1) {
                    uriEncodeParameters.put(uriEncode(strings[0]),
                            uriEncode(strings[1]));
                } else {
                    uriEncodeParameters.put(uriEncode(strings[0]),
                            uriEncode(""));
                }

            }
        }

        StringBuilder sbCanonicalQuery = new StringBuilder();

        /**
         * 所有参与传参的参数按照accsii排序（升序）
         */
        Set es = uriEncodeParameters.entrySet();

        Iterator it = es.iterator();

        boolean isDeleteLast = false;

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();

            String k = (String) entry.getKey();

            Object v = entry.getValue();

            if (null != v) {
                sbCanonicalQuery.append(k + "=" + v + "&");

                isDeleteLast =true;
            }
        }

        if(isDeleteLast){
            sbCanonicalQuery.deleteCharAt(sbCanonicalQuery.length() - 1);
        }

        return sbCanonicalQuery.toString();
    }

    /**
     * 获得CanonicalHeaders
     * CanonicalHeaders：对HTTP请求中的Header部分进行选择性编码的结果。
     * 您可以自行决定哪些Header 需要编码。百度云API的唯一要求是Host域必须被编码。大多数情况下，我们推荐您对以下Header进行编码：
     * Host
     * Content-Length
     * Content-Type
     * Content-MD5
     * 所有以 x-bce- 开头的Header
     * 如果这些Header没有全部出现在您的HTTP请求里面，那么没有出现的部分无需进行编码。
     * 如果您按照我们的推荐范围进行编码，那么认证字符串中的 {signedHeaders} 可以直接留空，无需填写。
     * 您也可以自行选择自己想要编码的Header。如果您选择了不在推荐范围内的Header进行编码，或者您的HTTP请求包含了推荐范围内的Header但是您选择不对它进行编码，那么您必须在认证字符串中填写 {signedHeaders} 。填写方法为，把所有在这一阶段进行了编码的Header名字转换成全小写之后按照字典序排列，然后用分号（;）连接。
     * 选择哪些Header进行编码不会影响API的功能，但是如果选择太少则可能遭到中间人攻击。
     * 对于每个要编码的Header进行如下处理：
     * 将Header的名字变成全小写。
     * 将Header的值去掉开头和结尾的空白字符。
     * 经过上一步之后值为空字符串的Header忽略，其余的转换为 UriEncode(name) + ":" + UriEncode(value) 的形式。
     * 把上面转换后的所有字符串按照字典序进行排序。
     * 将排序后的字符串按顺序用\n符号连接起来得到最终的CanonicalQueryHeaders。
     * 注意：很多发送HTTP请求的第三方库，会添加或者删除你指定的header（例如：某些库会删除content-length:0这个header），如果签名错误，请检查你您真实发出的http请求的header，看看是否与签名时的header一样。
     * @param request
     * @return
     */
    public static String getCanonicalHeaders(Request request) {

        StringBuilder sbCanonicalHeaders = new StringBuilder();

        Headers headers = request.headers();

        /**
         * 获得请求头的键
         */
        Set<String> names = headers.names();

        LinkedList<String> headerStrings = new LinkedList<String>();

        /**
         * 遍历请求头并排序
         */
        for(String key:names){
            if(TextUtils.isEmpty(key)||HearderType.BASE_AUTH.equals(key)||HearderType.SECRET_ACCESS_KEY.equals(key)){
                continue;
            }
            String value = headers.get(key);
            if (value == null) {
                value = "";
            }
            headerStrings.add(uriEncode(key.toLowerCase()) + ':'
                    + uriEncode(value.trim()));
        }

        Collections.sort(headerStrings);

        /**
         * 拼接数据
         */
        for (int i = 0; i < headerStrings.size(); i++) {

            sbCanonicalHeaders.append(headerStrings.get(i));

            if (i != headerStrings.size() - 1) {
                sbCanonicalHeaders.append("\n");
            }

        }

        return sbCanonicalHeaders.toString();
    }


    /***
     * 获得Signature
     * Signature = HMAC-SHA256-HEX(SigningKey, CanonicalRequest)
     * @param request
     * @param signingKey
     * @return
     */
    public static String getSignature(Request request,String signingKey) {
        String signKey = request.header(HearderType.SECRET_ACCESS_KEY);
        try {
            signKey = encodeHmacSHA256(signingKey.getBytes(),
                    getCanonicalRequest(request).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signKey;
    }

    /***
     * 获得SigningKey
     * SigningKey = HMAC-SHA256-HEX(sk, authStringPrefix)，其中：
     * sk为用户的Secret Access Key，可以通过在控制台中进行查询，关于SK的获取方法，请参看获取AK/SK。
     * authStringPrefix代表认证字符串的前缀部分，即：bce-auth-v1/{accessKeyId}/{timestamp}/{expirationPeriodInSeconds}。
     * @param request
     * @param authStringPrefix
     * @return
     */
    public static String getSigningKey(Request request,String authStringPrefix) {
        String signKey = request.header(HearderType.SECRET_ACCESS_KEY);
        try {
            signKey = encodeHmacSHA256(signKey.getBytes(),
                    authStringPrefix.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signKey;
    }

    /**
     * HmacSHA1摘要算法 对于给定生成的不同密钥，得到的摘要消息会不同，所以在实际应用中，要保存我们的密钥
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String encodeHmacSHA256(byte[] key,byte[] data)
            throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");// 还原密钥

        Mac mac = Mac.getInstance(secretKey.getAlgorithm());// 实例化Mac

        mac.init(secretKey);// 初始化mac

        byte[] digest = mac.doFinal(data);// 执行消息摘要

        return Hex.toHexString(digest);// 转为十六进制的字符串
    }


}
