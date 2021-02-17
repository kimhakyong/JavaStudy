package net.jackbauer.cipher.seed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import net.jackbauer.cipher.seed.cbc.KISA_SEED_CBC;
import net.jackbauer.cipher.seed.ecb.KISA_SEED_ECB;

public class SeedTest {
	private static String CHARSET = "utf-8";
	private static String MODE = "ecb";		// ecb, cbc
	private static final String PBUserKey = "kics2019Hwang!@#";
	private static String DEFAULT_IV = "1234567890123456";

	private static byte pbUserKey[] = PBUserKey.getBytes();
	private static byte bszIV[] = DEFAULT_IV.getBytes();

	public static void main(String[] args) {
		String filePath = "D:\\Dev\\Eclipse\\2019-03\\workspace\\study\\src\\main\\java\\net\\jackbauer\\cipher\\서시.txt";

		String plainText = fileReadNIO(filePath);

		byte[] encryptData = encrypt(plainText);
		System.out.println("encrypt : " + new String(encryptData));

		plainText = decrypt(encryptData);
		System.out.println("decrypt : " + plainText);
	}

	public static byte[] encrypt(String str) {
		byte[] enc = null;

		try {
			if (MODE.equals("ecb"))
				enc = KISA_SEED_ECB.SEED_ECB_Encrypt(pbUserKey, str.getBytes(CHARSET), 0, str.getBytes(CHARSET).length);
			else if (MODE.equals("cbc"))
				enc = KISA_SEED_CBC.SEED_CBC_Encrypt(pbUserKey, bszIV, str.getBytes(CHARSET), 0, str.getBytes(CHARSET).length);
			else
				throw new RuntimeException("Invalid Mode");
		} catch (Exception ex) {
			throw makeRuntimeException(ex);
		}

		Encoder encoder = Base64.getEncoder();
		byte[] encArray = encoder.encode(enc);

		return encArray;
	}

	public static String decrypt(byte[] str) {
		Decoder decoder = Base64.getDecoder();
		byte[] enc = decoder.decode(str);

		String result = "";
		byte[] dec = null;

		try {
			if (MODE.equals("ecb"))
				dec = KISA_SEED_ECB.SEED_ECB_Decrypt(pbUserKey, enc, 0, enc.length);
			else if (MODE.equals("cbc"))
				dec = KISA_SEED_CBC.SEED_CBC_Decrypt(pbUserKey, bszIV, enc, 0, enc.length);
			else
				throw new RuntimeException("Invalid Mode");

			result = new String(dec, CHARSET);
		} catch (Exception ex) {
			throw makeRuntimeException(ex);
		}

		return result;
	}

	public static String fileRead(String filePath) {
		String line = "", fullLine = "";

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			while ((line = reader.readLine()) != null) {
				fullLine += line + "\n";
			}
	
			System.out.println(fullLine);
		} catch (Exception ex) {
			throw makeRuntimeException(ex);
		}

		return fullLine;
	}

	public static String fileReadNIO(String filePath) {
		Path path = Paths.get(filePath);

		try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) Files.size(path));

			channel.read(byteBuffer);
			byteBuffer.flip();

			return Charset.forName(CHARSET).decode(byteBuffer).toString();
		} catch (Exception ex) {
			throw makeRuntimeException(ex);
		}
	}
	
	private static RuntimeException makeRuntimeException(Exception ex) {
		return new RuntimeException(ex.getClass().getSimpleName() + ": " + ex.getMessage());
	}
}
