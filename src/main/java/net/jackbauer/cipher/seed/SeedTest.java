package net.jackbauer.cipher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import net.jackbauer.cipher.cbc.KISA_SEED_CBC;
import net.jackbauer.cipher.ecb.KISA_SEED_ECB;

public class Sample {
	private static String CHARSET = "utf-8";
	private static final String PBUserKey = "kics2019Hwang!@#";
	private static String DEFAULT_IV = "1234567890123456";

	private static byte pbUserKey[] = PBUserKey.getBytes();
	private static byte bszIV[] = DEFAULT_IV.getBytes();

	public static void main(String[] args) {
		String filePath = "D:\\Dev\\Eclipse\\2019-03\\workspace\\study\\src\\main\\java\\net\\jackbauer\\cipher\\서시.txt";

		try {
			String plainText = fileReadNIO(filePath);

			byte[] encryptData = encrypt(plainText);
			System.out.println("encrypt : " + new String(encryptData, "utf-8"));

			plainText = decrypt(encryptData);
			System.out.println("decrypt : " + plainText);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] encrypt(String str) {
		byte[] enc = null;
		try {
			enc = KISA_SEED_ECB.SEED_ECB_Encrypt(pbUserKey, str.getBytes(CHARSET), 0, str.getBytes(CHARSET).length);
			// enc = KISA_SEED_CBC.SEED_CBC_Encrypt(pbUserKey, bszIV, str.getBytes(CHARSET), 0,
			// str.getBytes(CHARSET).length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
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
			dec = KISA_SEED_ECB.SEED_ECB_Decrypt(pbUserKey, enc, 0, enc.length);
			// dec = KISA_SEED_CBC.SEED_CBC_Decrypt(pbUserKey, bszIV, enc, 0, enc.length);
			result = new String(dec, CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String fileRead(String filePath) throws Exception {
		String line = "", fullLine = "";

		FileReader fr = new FileReader(filePath);
		BufferedReader reader = new BufferedReader(fr);
		while ((line = reader.readLine()) != null) {
			fullLine += line + "\n";
		}

		System.out.println(fullLine);
		
		reader.close();

		return fullLine;
	}

	public static String fileReadNIO(String filePath) throws Exception {
		Path path = Paths.get(filePath);

		if (!Files.exists(path))
			return "";

		try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) Files.size(path));

			channel.read(byteBuffer);
			byteBuffer.flip();
			
			return Charset.forName(CHARSET).decode(byteBuffer).toString();
		} catch (IOException e) {
			throw e;
		}
	}
}
