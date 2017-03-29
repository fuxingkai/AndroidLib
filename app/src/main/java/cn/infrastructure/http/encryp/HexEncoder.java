package cn.infrastructure.http.encryp;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 十六进制编码解码类
 * 
 * @author Frank
 * @version 1.0 Create by 2016.1.21
 */
public class HexEncoder implements Encoder {
	protected final byte[] encodingTable = { 48, 49, 50, 51, 52, 53, 54, 55,
			56, 57, 97, 98, 99, 100, 101, 102 };
	protected final byte[] decodingTable = new byte[''];

	protected void initialiseDecodingTable() {
		for (int i = 0; i < this.decodingTable.length; i++)
			this.decodingTable[i] = -1;
		for (int i = 0; i < this.encodingTable.length; i++)
			this.decodingTable[this.encodingTable[i]] = (byte) i;
		this.decodingTable[65] = this.decodingTable[97];
		this.decodingTable[66] = this.decodingTable[98];
		this.decodingTable[67] = this.decodingTable[99];
		this.decodingTable[68] = this.decodingTable[100];
		this.decodingTable[69] = this.decodingTable[101];
		this.decodingTable[70] = this.decodingTable[102];
	}

	public HexEncoder() {
		initialiseDecodingTable();
	}

	/**
	 * 编码
	 * @param paramArrayOfByte
	 * @param paramInt1
	 * @param paramInt2
	 * @param paramOutputStream
	 * @return
	 * @throws IOException
	 */
	public int encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2,
			OutputStream paramOutputStream) throws IOException {
		for (int i = paramInt1; i < paramInt1 + paramInt2; i++) {
			int j = paramArrayOfByte[i] & 0xFF;
			paramOutputStream.write(this.encodingTable[(j >>> 4)]);
			paramOutputStream.write(this.encodingTable[(j & 0xF)]);
		}
		return paramInt2 * 2;
	}

	/**
	 * 是否是忽略字符
	 * @param paramChar
	 * @return
	 */
	private static boolean ignore(char paramChar) {
		return (paramChar == '\n') || (paramChar == '\r')
				|| (paramChar == '\t') || (paramChar == ' ');
	}

	/**
	 * 解码
	 * @param paramArrayOfByte
	 * @param paramInt1
	 * @param paramInt2
	 * @param paramOutputStream
	 * @return
	 * @throws IOException
	 */
	public int decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2,
			OutputStream paramOutputStream) throws IOException {
		int k = 0;
		for (int m = paramInt1 + paramInt2; (m > paramInt1)
				&& (ignore((char) paramArrayOfByte[(m - 1)])); m--) {
			int n = paramInt1;
			while (n < m) {
				while ((n < m) && (ignore((char) paramArrayOfByte[n])))
					n++;
				int i = this.decodingTable[paramArrayOfByte[(n++)]];
				while ((n < m) && (ignore((char) paramArrayOfByte[n])))
					n++;
				int j = this.decodingTable[paramArrayOfByte[(n++)]];
				if ((i | j) < 0)
					throw new IOException(
							"invalid characters encountered in Hex data");
				paramOutputStream.write(i << 4 | j);
				k++;
			}
		}
		return k;
	}

	/**
	 * 解码
	 * @param paramString
	 * @param paramOutputStream
	 * @return
	 * @throws IOException
	 */
	public int decode(String paramString, OutputStream paramOutputStream)
			throws IOException {
		int k = 0;
		for (int m = paramString.length(); (m > 0)
				&& (ignore(paramString.charAt(m - 1))); m--) {
			int n = 0;
			while (n < m) {
				while ((n < m) && (ignore(paramString.charAt(n))))
					n++;
				int i = this.decodingTable[paramString.charAt(n++)];
				while ((n < m) && (ignore(paramString.charAt(n))))
					n++;
				int j = this.decodingTable[paramString.charAt(n++)];
				if ((i | j) < 0)
					throw new IOException(
							"invalid characters encountered in Hex string");
				paramOutputStream.write(i << 4 | j);
				k++;
			}
		}
		return k;
	}
}