package img.proc;
import java.util.*;
import java.io.*;

public class PPMImage extends RGBImage{
	
	private File PPMFile;
	
	public PPMImage(java.io.File file) throws UnsupportedFileFormatException, FileNotFoundException {	/* FULLY FUNCTIONAL */
		
		/* try to read from file */
		super(0, 0, 0);
		this.setPPMFile(file);
		Scanner input = null;
		String formatLine;
		int dimsRead = 0;
		short red, green, blue;
		int i = 0;
		int j = 0;
		
		try {
			if (file.exists()) {
				if (file.isDirectory()) {	/* if a directory */
					System.out.println("Given name is not a file!");
				}
				if (file.isFile() && file.canRead()) {	/* if an actual file, that can be read */
					input = new Scanner(file);
					formatLine = input.nextLine();
					if (!formatLine.equals("P3")) {
						throw new UnsupportedFileFormatException();
					}
					else {
						while (input.hasNextInt()) {
							/* here read height, width, colordepth */
							if (dimsRead == 0) {
								super.setWidth(input.nextInt());
								super.setHeight(input.nextInt());
								dimsRead = 1;
								super.setColorDepth(input.nextInt());
								super.setImage(new RGBPixel[super.getHeight()][super.getWidth()]);
							}
							for (j = 0; j < super.getWidth(); j++) {
								red = (short)input.nextInt();
								green = (short)input.nextInt();
								blue = (short)input.nextInt();
								super.getImage()[i][j] = new RGBPixel(red, green, blue);
							}
							i++;
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (input != null) {
				input.close();
			}
		}
	}
	
	public PPMImage (RGBImage image) {	/* FULLY FUNCTIONAL */
		super(image);
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				super.setPixel(i, j, image.getPixel(i, j));
			}
		}
	}
	
	public PPMImage (YUVImage image) {
		super(image);
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				super.setPixel(i, j, new RGBPixel(image.getYuvImg()[i][j]));
			}
		}
	}
	
	
	public File getPPMFile() {
		return this.PPMFile;
	}
	
	public void setPPMFile(File file) {
		this.PPMFile = file;
	}
	
	
	public String toString() {
		StringBuffer finalStr = new StringBuffer("P3\n");
		
		finalStr.append(Integer.toString(this.getWidth()) + " " + Integer.toString(this.getHeight()) + "\n" + Integer.toString(this.getColorDepth()) + "\n");
		
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				finalStr.append(Integer.toString((int)this.getImage()[i][j].getRed()) + " " + Integer.toString((int)this.getImage()[i][j].getGreen()) + " " + Integer.toString((int)this.getImage()[i][j].getBlue()) + " ");
			}
			finalStr.append("\n");
		}
		
		return finalStr.toString();
	}
	
	public void toFile(File file) {	/* FULLY FUNCTIONAL */
		PrintWriter pw = null;
		if (file.isDirectory()) {
			System.out.println("Cannot write to a directory!");
		}
		else if (file.exists() && file.isFile() && file.canWrite()) {
			try {
				pw = new PrintWriter(file);
				pw.print("");
				pw.println(this.toString());
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (SecurityException ex) {
				ex.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		}
		else {
			File newFile = new File(file.getPath());
			try {
				pw = new PrintWriter(newFile);
				pw.println(this.toString());
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		}
	}
}
