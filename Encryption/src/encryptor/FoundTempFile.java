package encryptor;

import java.io.File;

public class FoundTempFile {
	
	static File file;
	static String fileSrc;
	

	public   File getFile() {
		return file;
	}
	public  void setFile(File file) {
		FoundTempFile.file = file;
	}
	public  String getFileSrc() {
		return fileSrc;
	}
	public  void setFileSrc(String fileSrc) {
		FoundTempFile.fileSrc = fileSrc;
	}
	
	

}
