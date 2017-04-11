package frank.basis.http.encryp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

/**
 * 字符串处理类
 * 
 * @author Frank
 * @version 1.0 Create by 2016.1.21
 */
public final class Strings {


  /**
   * 把UTF-8字节码转换成字符
   * @param paramArrayOfByte
   * @return
   */
  public static String fromUTF8ByteArray(byte[] paramArrayOfByte) {
    int i = 0;
    int j = 0;
    while (i < paramArrayOfByte.length)
    {
      j++;
      if ((paramArrayOfByte[i] & 0xF0) == 240)
      {
        j++;
        i += 4;
        continue;
      }
      if ((paramArrayOfByte[i] & 0xE0) == 224)
      {
        i += 3;
        continue;
      }
      if ((paramArrayOfByte[i] & 0xC0) == 192)
      {
        i += 2;
        continue;
      }
      i++;
    }
    char[] arrayOfChar = new char[j];
    i = 0;
    j = 0;
    while (i < paramArrayOfByte.length)
    {
      int k;
      if ((paramArrayOfByte[i] & 0xF0) == 240)
      {
        int m = (paramArrayOfByte[i] & 0x3) << 18 | (paramArrayOfByte[(i + 1)] & 0x3F) << 12 | (paramArrayOfByte[(i + 2)] & 0x3F) << 6 | paramArrayOfByte[(i + 3)] & 0x3F;
        int n = m - 65536;
        int i1 = (char)(0xD800 | n >> 10);
        int i2 = (char)(0xDC00 | n & 0x3FF);
        arrayOfChar[(j++)] = (char) i1;
        k = i2;
        i += 4;
      }
      else if ((paramArrayOfByte[i] & 0xE0) == 224)
      {
        k = (char)((paramArrayOfByte[i] & 0xF) << 12 | (paramArrayOfByte[(i + 1)] & 0x3F) << 6 | paramArrayOfByte[(i + 2)] & 0x3F);
        i += 3;
      }
      else if ((paramArrayOfByte[i] & 0xD0) == 208)
      {
        k = (char)((paramArrayOfByte[i] & 0x1F) << 6 | paramArrayOfByte[(i + 1)] & 0x3F);
        i += 2;
      }
      else if ((paramArrayOfByte[i] & 0xC0) == 192)
      {
        k = (char)((paramArrayOfByte[i] & 0x1F) << 6 | paramArrayOfByte[(i + 1)] & 0x3F);
        i += 2;
      }
      else
      {
        k = (char)(paramArrayOfByte[i] & 0xFF);
        i++;
      }
			arrayOfChar[(j++)] = (char) k;
    }
    return new String(arrayOfChar);
  }

  /**
   * 把字符串转换成UTF-8字节码
   * @param paramString
   * @return
   */
  public static byte[] toUTF8ByteArray(String paramString) {
    return toUTF8ByteArray(paramString.toCharArray());
  }

  /**
   * 把字节数组转换成UTF-8字节码
   * @param paramArrayOfChar
   * @return
   */
  public static byte[] toUTF8ByteArray(char[] paramArrayOfChar)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      toUTF8ByteArray(paramArrayOfChar, localByteArrayOutputStream);
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("cannot encode string to byte array!");
    }
    return localByteArrayOutputStream.toByteArray();
  }

  /**
   * 把字节数组转换成UTF-8字节输出流
   * @param paramArrayOfChar
   * @param paramOutputStream
   * @throws IOException
   */
  public static void toUTF8ByteArray(char[] paramArrayOfChar, OutputStream paramOutputStream)
    throws IOException
  {
    char[] arrayOfChar = paramArrayOfChar;
    for (int i = 0; i < arrayOfChar.length; i++)
    {
      int j = arrayOfChar[i];
      if (j < 128)
      {
        paramOutputStream.write(j);
      }
      else if (j < 2048)
      {
        paramOutputStream.write(0xC0 | j >> 6);
        paramOutputStream.write(0x80 | j & 0x3F);
      }
      else if ((j >= 55296) && (j <= 57343))
      {
        if (i + 1 >= arrayOfChar.length)
          throw new IllegalStateException("invalid UTF-16 codepoint");
        int k = j;
        i++;
        j = arrayOfChar[i];
        int m = j;
        if (k > 56319)
          throw new IllegalStateException("invalid UTF-16 codepoint");
        int n = ((k & 0x3FF) << 10 | m & 0x3FF) + 65536;
        paramOutputStream.write(0xF0 | n >> 18);
        paramOutputStream.write(0x80 | n >> 12 & 0x3F);
        paramOutputStream.write(0x80 | n >> 6 & 0x3F);
        paramOutputStream.write(0x80 | n & 0x3F);
      }
      else
      {
        paramOutputStream.write(0xE0 | j >> 12);
        paramOutputStream.write(0x80 | j >> 6 & 0x3F);
        paramOutputStream.write(0x80 | j & 0x3F);
      }
    }
  }

  /**
   * 转换成大写
   * @param paramString
   * @return
   */
  public static String toUpperCase(String paramString)
  {
    int i = 0;
    char[] arrayOfChar = paramString.toCharArray();
    for (int j = 0; j != arrayOfChar.length; j++)
    {
      int k = arrayOfChar[j];
      if ((97 > k) || (122 < k))
        continue;
      i = 1;
      arrayOfChar[j] = (char)(k - 97 + 65);
    }
    if (i != 0)
      return new String(arrayOfChar);
    return paramString;
  }

  /**
   * 转换成小写
   * @param paramString
   * @return
   */
  public static String toLowerCase(String paramString)
  {
    int i = 0;
    char[] arrayOfChar = paramString.toCharArray();
    for (int j = 0; j != arrayOfChar.length; j++)
    {
      int k = arrayOfChar[j];
      if ((65 > k) || (90 < k))
        continue;
      i = 1;
      arrayOfChar[j] = (char)(k - 65 + 97);
    }
    if (i != 0)
      return new String(arrayOfChar);
    return paramString;
  }

  /**
   * 转换成字节数组
   * @param paramArrayOfChar
   * @return
   */
  public static byte[] toByteArray(char[] paramArrayOfChar)
  {
    byte[] arrayOfByte = new byte[paramArrayOfChar.length];
    for (int i = 0; i != arrayOfByte.length; i++)
      arrayOfByte[i] = (byte)paramArrayOfChar[i];
    return arrayOfByte;
  }

  /**
   * 转换成字节数组
   * @param paramString
   * @return
   */
  public static byte[] toByteArray(String paramString)
  {
    byte[] arrayOfByte = new byte[paramString.length()];
    for (int i = 0; i != arrayOfByte.length; i++)
    {
      int j = paramString.charAt(i);
      arrayOfByte[i] = (byte)j;
    }
    return arrayOfByte;
  }

  /**
   * 转换成字符串
   * @param paramArrayOfByte
   * @return
   */
  public static String fromByteArray(byte[] paramArrayOfByte)
  {
    return new String(asCharArray(paramArrayOfByte));
  }

  /**
   * 转换成字符数组
   * @param paramArrayOfByte
   * @return
   */
  public static char[] asCharArray(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar = new char[paramArrayOfByte.length];
    for (int i = 0; i != arrayOfChar.length; i++)
      arrayOfChar[i] = (char)(paramArrayOfByte[i] & 0xFF);
    return arrayOfChar;
  }

  /**
   * 分割成字符串数组
   * @param paramString
   * @param paramChar
   * @return
   */
  public static String[] split(String paramString, char paramChar)
  {
    Vector localVector = new Vector();
    int i = 1;
    while (i != 0)
    {
      int j = paramString.indexOf(paramChar);
      if (j > 0)
      {
        String str = paramString.substring(0, j);
        localVector.addElement(str);
        paramString = paramString.substring(j + 1);
      }
      else
      {
        i = 0;
        localVector.addElement(paramString);
      }
    }
    String[] arrayOfString = new String[localVector.size()];
    for (int k = 0; k != arrayOfString.length; k++)
      arrayOfString[k] = ((String)localVector.elementAt(k));
    return arrayOfString;
  }
}