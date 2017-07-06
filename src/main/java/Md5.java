import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Md5 {

	public static String digest(String plaintext) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(plaintext.getBytes());

		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1, digest);

		String hashtext = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		while( hashtext.length() < 32 ){
			hashtext = "0" + hashtext;
		}

		return hashtext;
	}

}