
import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter {
	
	@Override
	//Tests if a specified file should be included in a file list.
	public boolean accept(File directory, String fileName) {
		fileName = fileName.toLowerCase();
		fileName = fileName.split("results")[0];
		if (fileName.endsWith(".csv")){
            return true;
        }
        return false;
	}
}