package cn.infrastructure.http.retrofit2.adapter.rxjava;

import retrofit2.Response;

/**
 * Exception for an unexpected, non-2xx HTTP response.
 */
public final class HttpException extends Exception {
    private int code;
    private String message;
    private transient Response<?> response;

    public HttpException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpException(Response<?> response) {
        super("HTTP " + response.code() + " " + response.message());
        this.code = response.code();
        this.message = response.message();
        this.response = response;
    }

    /**
     * HTTP status code.
     */
    public int code() {
        return code;
    }

    /**
     * HTTP status message.
     */
    public String message() {
        return message;
    }

    /**
     * The full HTTP response. This may be null if the exception was serialized.
     */
    public Response<?> response() {
        return response;
    }
}
