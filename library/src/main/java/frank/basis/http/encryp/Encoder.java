package frank.basis.http.encryp;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 编码类
 * 
 * @author Frank
 * @version 1.0 Create by 2016.1.21
 */
public abstract interface Encoder
{
  /**
   * 编码
   * @param paramArrayOfByte
   * @param paramInt1
   * @param paramInt2
   * @param paramOutputStream
   * @return
   * @throws IOException
   */
  public abstract int encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, OutputStream paramOutputStream)
    throws IOException;

  /**
   * 解码
   * @param paramArrayOfByte
   * @param paramInt1
   * @param paramInt2
   * @param paramOutputStream
   * @return
   * @throws IOException
   */
  public abstract int decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, OutputStream paramOutputStream)
    throws IOException;


  /**
   * 解码
   * @param paramString
   * @param paramOutputStream
   * @return
   * @throws IOException
   */
  public abstract int decode(String paramString, OutputStream paramOutputStream)
    throws IOException;
}