package uta.mav.appoint;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncrDecr {
  private static String IV = "AAAAAAAAAAAAAAAA";
  private static String encryptionKey = "0123456789abcdef";
//  public static void main(String [] args) {
//    try {
//      
//      System.out.println("==Java==");
//      System.out.println("plain:   " + plaintext);
//
//      byte[] cipher = encrypt(plaintext, encryptionKey);
//
//      System.out.print("cipher:  ");
//      for (int i=0; i<cipher.length; i++)
//        System.out.print(new Integer(cipher[i])+" ");
//      System.out.println("");
//
//      String decrypted = decrypt(cipher, encryptionKey);
//      
//      System.out.println("decrypt: " + decrypted);
//
//    } catch (Exception e) {
//      e.printStackTrace();
//    } 
//  }
  
  private static String addPadding(String plainText){
	  int paddingLength = (16-(plainText.getBytes().length)%16);
	  StringBuffer buff = new StringBuffer(plainText);
	  for(int i=0;i<paddingLength;i++){
		  buff.append('\0');
	  }
	  return buff.toString();
  }
  
  private static String removePadding(String decryptedText){
	  return decryptedText.replaceAll("\0", "");
  }

  public static String encrypt(String plainText) throws Exception {
	if((plainText.getBytes().length)%16!=0){
		plainText = addPadding(plainText);
	}
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return new String(cipher.doFinal(plainText.getBytes("UTF-8")),"ISO-8859-1");
  }

  public static String decrypt(String cipherTextString) throws Exception{
	byte[] cipherText = cipherTextString.getBytes("ISO-8859-1");
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return removePadding(new String(cipher.doFinal(cipherText),"UTF-8"));
  }
}