package cn.infrastructure.http.encryp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 十六进制处理类
 * 
 * @author Frank
 * @version 1.0 Create by 2016.1.21
 */
public class Hex {
	private static final Encoder encoder = new HexEncoder();

	/**
	 * 把字节转换成十六进制字符串
	 * @param paramArrayOfByte
	 * @return
	 */
	public static String toHexString(byte[] paramArrayOfByte) {
		return toHexString(paramArrayOfByte, 0, paramArrayOfByte.length);
	}

	/**
	 * 把字节转换成十六进制字符串
	 * @param paramArrayOfByte
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public static String toHexString(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2) {
		byte[] arrayOfByte = encode(paramArrayOfByte, paramInt1, paramInt2);
		return Strings.fromByteArray(arrayOfByte);
	}

	/**
	 * 对字节进行编码
	 * @param paramArrayOfByte
	 * @return
	 */
	public static byte[] encode(byte[] paramArrayOfByte) {
		return encode(paramArrayOfByte, 0, paramArrayOfByte.length);
	}

	/**
	 * 对字节进行编码
	 * @param paramArrayOfByte
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public static byte[] encode(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2) {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		try {
			encoder.encode(paramArrayOfByte, paramInt1, paramInt2,
					localByteArrayOutputStream);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localByteArrayOutputStream.toByteArray();
	}

	/**
	 * 对字节进行编码
	 * @param paramArrayOfByte
	 * @param paramOutputStream
	 * @return
	 * @throws IOException
	 */
	public static int encode(byte[] paramArrayOfByte,
			OutputStream paramOutputStream) throws IOException {
		return encoder.encode(paramArrayOfByte, 0, paramArrayOfByte.length,
				paramOutputStream);
	}

	/**
	 * 对字节进行编码
	 * @param paramArrayOfByte
	 * @param paramInt1
	 * @param paramInt2
	 * @param paramOutputStream
	 * @return
	 * @throws IOException
	 */
	public static int encode(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2, OutputStream paramOutputStream) throws IOException {
		return encoder.encode(paramArrayOfByte, paramInt1, paramInt2,
				paramOutputStream);
	}

	/**
	 * 对字节进行解码
	 * @param paramArrayOfByte
	 * @return
	 */
	public static byte[] decode(byte[] paramArrayOfByte) {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		try {
			encoder.decode(paramArrayOfByte, 0, paramArrayOfByte.length,
					localByteArrayOutputStream);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localByteArrayOutputStream.toByteArray();
	}

	/**
	 * 对字节进行解码
	 * @param paramString
	 * @return
	 */
	public static byte[] decode(String paramString) {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		try {
			encoder.decode(paramString, localByteArrayOutputStream);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localByteArrayOutputStream.toByteArray();
	}

	/**
	 * 对字节进行解码
	 * @param paramString
	 * @param paramOutputStream
	 * @return
	 * @throws IOException
	 */
	public static int decode(String paramString, OutputStream paramOutputStream)
			throws IOException {
		return encoder.decode(paramString, paramOutputStream);
	}
}