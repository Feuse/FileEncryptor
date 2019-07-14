package encryptor;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;


public class AsymmetricCryptography {
	private Cipher cipher;
	static public AsymmetricCryptography asymmetricCryptography = new AsymmetricCryptography();
	
	

	public static AsymmetricCryptography getAsymmetricCryptography() {
		return asymmetricCryptography;
	}

	private AsymmetricCryptography(){
		try {
			this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void enc(File tempFile, String fileSrc, PublicKey pubk) throws IOException, GeneralSecurityException {
		encryptFile(getFileInBytes(tempFile), 
				new File(fileSrc),pubk);
		
	}
	
	public void dec(File tempFile, String fileSrc, PrivateKey prvk) {
		try {
			decryptFile(getFileInBytes(tempFile),
					new File(fileSrc), prvk);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// https://docs.oracle.com/javase/8/docs/api/java/security/spec/PKCS8EncodedKeySpec.html
	public PrivateKey getPrivate(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	// https://docs.oracle.com/javase/8/docs/api/java/security/spec/X509EncodedKeySpec.html
	public PublicKey getPublic(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}
	

	public void encryptFile(byte[] input, File output, PublicKey publicKey) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		writeToFile(output, this.cipher.doFinal(input));
	}

	public void decryptFile(byte[] input, File output, PrivateKey privateKey)  {
		try {
		this.cipher.init(Cipher.DECRYPT_MODE, privateKey);
		writeToFile(output, this.cipher.doFinal(input));
		}catch(BadPaddingException | IllegalBlockSizeException | IOException | InvalidKeyException e) {
			e.printStackTrace();
		}
		
	}

	private void writeToFile(File output, byte[] toWrite)
			throws IllegalBlockSizeException, BadPaddingException, IOException {
		FileOutputStream fos = new FileOutputStream(output);
		fos.write(toWrite);
		fos.flush();
		fos.close();
	}

	public String encryptText(String msg, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		this.cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return Base64.encodeBase64String(cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8)));

	}

	public String decryptText(String msg, PublicKey publicKey)
			 {
		try {
		this.cipher.init(Cipher.DECRYPT_MODE, publicKey);
		String str = new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
		return str;
		}catch(Exception e) {
			 e.printStackTrace();
		}
		return msg;
	}

	public byte[] getFileInBytes(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		byte[] fbytes = new byte[(int) f.length()];
		fis.read(fbytes);
		fis.close();
		return fbytes;
	}

	
}