package net.jackbauer.cipher.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
 
public class HashTest {
    public static void main(String[] args) {
        String str = "jackbauer";
        String hashStr = createHash(str);
        System.out.println("str : " + str + ", hashStr : " + hashStr);
    }
 
    public static String createHash(String str) {
        String hashString = "";
        try {
            // MD2, MD4, MD5, SHA-1, SHA-256, SHA-512
            // MessageDigest sh = MessageDigest.getInstance("SHA-512");
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            
            System.out.println("byteData : " + Arrays.toString(byteData));
            System.out.println("byteData : " + byteData);
            System.out.println("byteData length : " + byteData.length);
            
            // 16진수 변환
            StringBuilder hexHash = new StringBuilder();
            for(byte b : byteData) {
                String hexString = String.format("%02x", b);
                hexHash.append(hexString);
            }
            
            System.out.println("hexHash : " + hexHash);
           
            // 16진수 변환
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashString = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            hashString = null;
        }
        
        return hashString;
    }
}