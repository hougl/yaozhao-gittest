import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;


/**
 * @author hgl
 *
 */
public class AsciiTest {
	
	public static  String string2Ascii(String value) {
		StringBuffer sb = new StringBuffer();
		char[] array = value.toCharArray();
		for(char a : array) {
			sb.append((int) a);
			sb.append(",");
		}
		return sb.toString();
	}

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(string2Ascii("我爱你中国"));
		// TODO Auto-generated method stub
		//默认编码utf-8 3
		System.out.println("中".getBytes().length);
		//2个字节
		System.out.println("中".getBytes("GBK").length);
		//4个字节
		System.out.println("中".getBytes("unicode").length);
		//1个字节
		System.out.println("中".getBytes("US-ASCII").length);
		System.out.println("中".getBytes("US-ASCII")[0]);
		System.out.println((int)'a');
		System.out.println(new Base64().encodeBase64String("我爱你".getBytes("UTF-8")));
	}

}
