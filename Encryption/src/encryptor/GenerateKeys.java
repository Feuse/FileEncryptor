package encryptor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class GenerateKeys {
	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private String prvkStr;
	static public GenerateKeys generateKeys = new GenerateKeys();

	public String getPrvkStr() {
		return prvkStr;
	}

	public void setPrvkStr(String prvkStr) {
		this.prvkStr = prvkStr;
	}
	
	

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public GenerateKeys(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
		this.keyGen = KeyPairGenerator.getInstance("RSA");
		this.keyGen.initialize(keylength);

	}

	public static GenerateKeys getGenerateKeys() {
		return generateKeys;
	}

	public void generateTheKeys() {
		GenerateKeys gk;

		try {
			gk = new GenerateKeys(1024);
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			gk.createKeys();
			// Initialize KeyPairGenerator.

			keyGen.initialize(1024);

			// Generate Key Pairs, a private key and a public key.
			KeyPair keyPair = keyGen.generateKeyPair();
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey publicKey = keyPair.getPublic();
			Base64.Encoder encoder = Base64.getEncoder();
			String prvkenc = encoder.encodeToString(privateKey.getEncoded());

			System.out.println(" Private Key on creation Is :" + prvkenc);
			Base64.Decoder dec = Base64.getDecoder();
			byte[] decodedPub = dec.decode(prvkenc);
			String str = new String(decodedPub, StandardCharsets.UTF_8);
			System.out.println(str);


			gk.writeToFile("C:\\Users\\Feuse\\Desktop\\enc\\publicKey.txt", gk.getPublicKey().getEncoded());
			gk.writeStringToFile("C:\\\\Users\\\\Feuse\\\\Desktop\\\\enc\\\\privateKey.txt", prvkenc);
			gk.writeToFile("C:\\\\Users\\\\Feuse\\\\Desktop\\\\enc\\\\privateKeyEnc.txt",
					gk.getPrivateKey().getEncoded());
			gk.setPrvkStr(prvkenc);
			gk.setPrivateKey(privateKey);
			String prvkenc1 = encoder.encodeToString(gk.getPrivateKey().getEncoded());
			System.out.println("Second privateKey is : " + prvkenc1);
			setPrivateKey(privateKey);
			setPublicKey(publicKey);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private GenerateKeys() {
	}

	public void createKeys() {

		this.pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}

	public void writeStringToFile(String path, String key) throws IOException {
		File f = new File(path);
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		writer.write(key);
		writer.flush();
		writer.close();
	}

	public String prvkEncodedToString(PrivateKey prv) {
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(prv.getEncoded());

	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

}
