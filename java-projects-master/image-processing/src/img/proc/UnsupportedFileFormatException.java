package img.proc;

@SuppressWarnings("serial")
public class UnsupportedFileFormatException extends Exception {
	
	public UnsupportedFileFormatException() {}
	
	public UnsupportedFileFormatException(String msg) {
		System.out.println(msg);
	}
	
}
