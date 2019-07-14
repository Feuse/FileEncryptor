package encryptor;

import java.io.File;

public class TempFile {

	static File file;
	static String fileSrc;


	 public String getFileSrc() {
		return fileSrc;
	}

	public void setFileSrc(String fileSrc) {
		TempFile.fileSrc = fileSrc;
	}

	 public File getFile() {
		return file;
	}

	public void setFile(File file) {
		TempFile.file = file;
	}

	@Override
	public String toString() {
		return "TempFile [file=" + file + ", fileSrc=" + fileSrc + "]";
	}

}
