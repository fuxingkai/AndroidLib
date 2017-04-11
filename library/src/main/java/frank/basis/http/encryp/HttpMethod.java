package frank.basis.http.encryp;

public enum HttpMethod {
	POST("POST"), GET("GET"), PUT("PUT"), DELETE("DELETE"),HEAD("HEAD"),PATCH("PATCH");

	private String method;

	private HttpMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return this.method;
	}
}
