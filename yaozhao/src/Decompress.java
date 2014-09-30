import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

import org.apache.commons.codec.binary.Base64;


/**
 * 解压缩及base64解码工具类
 * @author hgl
 *
 */
public class Decompress {
	
	/**
	 * GZip解压缩
	 * @param data
	 * @return
	 */
	public static byte[] unGZip(byte[] data) {
		byte[] b = null;
	    try {
		    ByteArrayInputStream bis = new ByteArrayInputStream(data);
		    GZIPInputStream gzip = new GZIPInputStream(bis);
		    byte[] buf = new byte[1024];
		    int num = -1;
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    while ((num = gzip.read(buf, 0, buf.length)) != -1) {
		    	baos.write(buf, 0, num);
		    }
		    b = baos.toByteArray();
		    baos.flush();
		    baos.close();
		    gzip.close();
		    bis.close();
	  } catch (Exception e) {
		  System.out.println("解压缩失败:" + e.getMessage());
	  }
	  return b;
	}
	
	/**
	 * 解压缩
	 * @param data 待解压缩字符串
	 * @return
	 */
	public static String uncompress(String data) {
		try {
			//1、base64解码
			byte[] base64DeCode = Base64.decodeBase64(data.getBytes("utf-8"));
			//2、Gzip解压缩
			if(base64DeCode != null) {
				byte[] ret = unGZip(base64DeCode);
				if(ret != null) {
					return new String(ret, "utf-8");
				} else {
					return null;
				}
			} else {
				return null;
			}
			
		} catch (UnsupportedEncodingException e) {
			 System.out.println("base64解码失败:" + e.getMessage());
			return null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "H4sIAAAAAAAEAOy9B2AcSZYlJi9tynt/SvVK1+B0oQiAYBMk2JBAEOzBiM3mkuwdaUcjKasqgcplVmVdZhZAzO2dvPfee++999577733ujudTif33/8/XGZkAWz2zkrayZ4hgKrIHz9+fB8/Inb37u3f//RB1sz/8z/1j6L/zc4v5j/9tjz8+ODhzvZn/08AAAD//xclBXUeAAAA";
		System.out.println(uncompress(str));
		if("1234567ash哈哈dfghjkl;'890-=".equals(uncompress(str))) {
			System.out.println("解压缩成功");
		} else {
			System.out.println("解压缩失败");
		}
	}

}
