

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import SaleSec.SaleService;

/**
 * @author hgl<br>
 * @version 1.0
 * Apr 15, 2014 2:23:41 PM<br>
 */
public class Dealer {
	
	
	/**
	 * 药招接口获取签名
	 * @param userName 药招接口用户名
	 * @param password 药招接口密码
	 * @param sha1 药招接口sha值
	 * @return
	 */
	public static String getSignature(String userName, String password, String sha1) {
		return SaleService.GetSignature(userName, password, sha1);
	}
	
	/**
	 * 药品采购用户登录
	 * @param userName
	 * @param password
	 * @param signature
	 * @param loginAddress
	 * @return
	 */
	public static String login(String userName, String password, String sha1, String loginAddress) {
		//组织入参数据：用户名、用户密码、签名
		Object[] opArgs = new Object[] {userName, password, getSignature(userName, password, sha1)};
		//动态调用Webservcie  获得serviceResponse
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(loginAddress);
		//存储serviceResponse
		Object[] ret = null;
	    try {
	    	ret = client.invoke("APILogin", opArgs);
		} catch (Exception e) {
			throw new RuntimeException("调用接口服务失败，" + e.getMessage());
		}
		if(ret != null && ret.length != 0) {
//			System.out.println("一共有几个参数：" + ret.length);
//			for(int i = 0; i < ret.length; i++) {
//				System.out.println("返回的第" + (i + 1) + "个值: " +"\n"+ (String)ret[i]);
//			}	
			String result = (String)ret[0];
			if(result != null && !"".equals(result)) {
				if(result.startsWith("OK:")) {
					//登录成功
					return result.substring(3);
				} else if(result.startsWith("0")) {
					//登录失败
					throw new RuntimeException("登录失败：" + result.substring(1));
				} else {
					//非约定值的登录失败
					throw new RuntimeException("登录失败：" + result);
				}
			
			}else {
				//没有返回值，抛出异常
				throw new RuntimeException("登录失败，接口返回值为空");
			}
			
		} else {
			//没有返回值，抛出异常
			throw new RuntimeException("接口返回值为空");
		}
	}
	
	/**
	 * 加密
	 * @param pToEncrypt
	 * @param sKey
	 * @return
	 */
	public static String encrypt(String pToEncrypt, String sKey) {
		try {
			return SaleService.encrypt(pToEncrypt, sKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public static void main(String[] args) {
		//每日限制20次登陆，每次登陆的有效期多久
//		String dcppLoginSn = "5521032b-3d2a-4343-a40f-cc1a5d5c09e4";
		System.out.println(dcppLoginSn);//0f6869d3-abd4-4b1a-a873-e81d7ac9e699
		String uid = dcppLoginSn;
		String all = encrypt("all","|`WZB#*Y");//
//		String time = encrypt("20120101000000","|`WKR#*!");
		System.out.println(all);//XOJt25J9O0I=
//		System.out.println(time);//aMYGndEb2NLBsTqmjsqBjQ==
		System.out.println(dcppLoginSn);//b4IOiXy+4CCmSSm9WPnmLvAs4mv44BykriALQkVOOp5xZRj7WLb0IQ==
		//组织入参数据：用户名、用户密码、签名
		Object[] opArgs = new Object[] {all, uid};
		//动态调用Webservcie  获得serviceResponse
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		//存储serviceResponse
		Object[] ret = null;
	    try {
	    	ret = client.invoke("get_drugsaleinfo", opArgs);
	    	System.out.println(SaleService.UnCompressStringDecryptJ((String)ret[0], "|`WZB#*Y"));
	    	System.out.println(ret[0]);
		} catch (Exception e) {
			throw new RuntimeException("调用接口服务失败，" + e.getMessage());
		}
		//注意当查询不到数据时的接口返回
		if(ret != null && ret.length != 0) {
			System.out.println("一共有几个参数：" + ret.length);
			
		} else {
			//没有返回值，抛出异常
			throw new RuntimeException("获取中标基本药物目录失败，接口返回值为空");
		}
	}
	
	

}
