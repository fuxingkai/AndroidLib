package frank.basis.http.exception;

/**
 * 添加网络加密自定义异常类
 * 
 * @author Frank
 * @version 1.0 Create by 2016.3.25
 */
public class EncrypException extends Exception {

	public EncrypException() {
		super();
	}

	public EncrypException(String msg) {
		super(msg);
	}

	public EncrypException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public EncrypException(Throwable cause) {
		super(cause);
	}

}