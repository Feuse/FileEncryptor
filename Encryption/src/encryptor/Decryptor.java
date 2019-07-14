package encryptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Sopiro (https://goo.gl/1WFWq1)
 *
 */
public class Decryptor {
	private static Decryptor decrypter = new Decryptor();

	private static boolean deleteOriginal;

	private Decryptor() {
	}

	public static Decryptor getDecrypter(boolean originalFileDeleted) {
		deleteOriginal = originalFileDeleted;

		return decrypter;
	}

	public void decrypt(File src, File dst) {
		if (!dst.exists())
			dst.mkdir();
		if (!dst.isDirectory())
			return;

		try {
			if (!src.isDirectory()) {
				copyDecrypted(src, dst);
				
			} else {
				File[] files = src.listFiles();

				System.out.println("Decryting...");

				for (File f : files) {
					copyDecrypted(f, dst);
					if (deleteOriginal)
						f.delete();
				}

				System.out.println(files.length + " files are decrytped");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyDecrypted(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;

		try {
			is = new FileInputStream(source);

			byte[] buffer = new byte[1024];

			byte[] name = new byte[is.read() * 2];
			is.read(name);
			String fileName = bytesToString(name);

			os = new FileOutputStream(dest.getPath().concat("/").concat(fileName));
//			String src = source.getAbsolutePath();
//			File enc = EncFile.getEncFile(src);
			int length;

			// gets the data back?
			while ((length = is.read(buffer)) > 0) {
				decryptBytes(buffer);
				os.write(buffer, 0, length);
			}

		} finally {
			is.close();
			os.close();
		}
	}

	// the algorithm that reverses the name from enc to original name
	public String bytesToString(byte[] data) {
		StringBuilder res = new StringBuilder();

		for (int i = 0; i < data.length / 2; i++) {
			char c = (char) ((data[i * 2] << 8) | data[i * 2 + 1]);
			res.append(c);
		}

		return res.toString();
	}

	private void decryptBytes(byte[] data) // Decryption Algorithm is written into here
	{
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) ~data[i];
		}
	}

	public void copy(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;

		try {
			dest = new File(dest.getPath().concat("/").concat(source.getName()));

			is = new FileInputStream(source);
			os = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			int tl = 0;

			while ((length = is.read(buffer)) > 0) {
				tl += length;
				os.write(buffer, 0, length);
			}

			System.out.println(tl + " bytes");
		} finally {
			is.close();
			os.close();
		}
	}

	public void privateKeyToString() throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeySpecException {
	String privateKeyContent = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("privateKey.txt").toURI())));
    String publicKeyContent = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("C:\\\\Users\\\\Feuse\\\\Desktop\\\\enc\\\\publicKey.txt").toURI())));

    privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
    publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;

    KeyFactory kf = KeyFactory.getInstance("RSA");

    PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
    PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

    X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
    RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

    System.out.println(privKey);
    System.out.println(pubKey);
	}
}
