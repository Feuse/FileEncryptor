package encryptor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearch {

	private String fileName;
	private List<String> result = new ArrayList<>();

	public File searchDirectory(File directory, String fileName) {
		
		setFileName(fileName);
		File f = null;
		if (directory.isDirectory()) {
			f= search(directory);
		} else {
			System.out.println(directory.getAbsoluteFile() + " is not a directory!");
		}
		return f;

	}

	private File search(File file) {
		File temp2 = null;
		if (file.isDirectory()) {
			System.out.println("Searching directory ... " + file.getAbsoluteFile());
			// do you have permission to read this directory?
			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						search(temp);
					} else {
						String tempName = temp.getName(); //temp short name.
						int i = tempName.lastIndexOf('.') + 1;
						int last = tempName.length();
						String name = tempName.substring(i, last); //get extension name , i is the '.' and last is the last index 
						if (tempName.equals(temp.getName())) {
							temp2 = temp;
							result.add(temp.getAbsoluteFile().toString());
						}
					}
				}
			} else {
				System.out.println(file.getAbsoluteFile() + "Permission Denied");
			}
		}
		return temp2;
	}

	public String getFileName() {
		return fileName;
	}

	//while setting change original name to temp short name.
	public void setFileName(String fileName) {
		String tempName = fileName; //temp short name.
		int i = tempName.lastIndexOf('.') + 1;
		int last = tempName.length();
		String name = tempName.substring(i, last);
		name = fileName;
	}

	public List<String> getResult() {
		return result;
	}
}
