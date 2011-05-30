package transmission;
import java.security.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigInteger;
import java.net.SocketAddress;



public class StaticHelper {
	static String appId = null;
	static SecureRandom secureRandom = new SecureRandom();
	static MessageDigest md;
	
	static Pattern addressPattern = null;
	
	

	public static String getAppId(){
		if (StaticHelper.appId == null){
			StaticHelper.appId = new BigInteger(130, StaticHelper.secureRandom).toString(32);
			StaticHelper.appId =StaticHelper.padd(StaticHelper.appId, 26);
		}
		return StaticHelper.appId;
	}
	
	public static String padd(String strin, int length){
		while(strin.length() < length ){
			  strin = "0"+strin;
			}		
		return strin;
	}
	

	public static <Typ> String getClassnameHash(Class<Typ> inclass){
		byte[] classname = inclass.getName().getBytes();
		if (StaticHelper.md == null){
			try{
				StaticHelper.md = MessageDigest.getInstance("MD5");
			}catch(NoSuchAlgorithmException e){
				//FIXME
			}
		}
		byte[] digested = md.digest(classname);
		BigInteger bigInt = new BigInteger(1,digested);
		String hashtext = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		StaticHelper.padd(hashtext, 32);
		return hashtext;
	}
	
	
	public static void main(String[] args){
		System.out.println(StaticHelper.getAppId());
		System.out.println(StaticHelper.getAppId());
	
		System.out.println(StaticHelper.getClassnameHash(String.class));
		Class<?> test = String.class;
	}
	
	public static String getAddress(SocketAddress s){
		if(StaticHelper.addressPattern == null){
			StaticHelper.addressPattern = Pattern.compile("^/?([0-9\\.]*).*");
		}
		Matcher matcher = StaticHelper.addressPattern.matcher(s.toString());
		matcher.matches();
		return matcher.group(1);

	}
	
}
