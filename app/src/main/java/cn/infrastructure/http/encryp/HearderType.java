package cn.infrastructure.http.encryp;

/**
 * 鉴权默认请求头名称列表
 * @author Frank
 * @date: 2017/3/28 0028
 */

public class HearderType {
    public final static String BASE_AUTH = "baseAuth";
    public final static String SECRET_ACCESS_KEY = "secretAccesskey";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_MD5 = "Content-Md5";
    public static final String HOST = "Host";
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";
    public static final String CONTENT_TYPE_CONTENT = "application/x-www-form-urlencoded; charset=UTF-8";
    public static final String CONTENT_TYPE_CONTENT_JSON = "application/json; charset=UTF-8";
    public static final String CONTENT_TYPE_CONTENT_FROM_DATA = "multipart/form-data";

}
