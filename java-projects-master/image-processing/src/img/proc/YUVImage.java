package img.proc;
import java.io.*;
import java.util.Scanner;

public class YUVImage {
	
	private YUVPixel[][] yuvImg;
	private int yuvHeight;
	private int yuvWidth;
	
	public YUVImage(int width, int height) {
		this.yuvImg = new YUVPixel[height][width];
		this.yuvHeight = height;
		this.yuvWidth = width;
		
		for (int i = 0; i < this.yuvHeight; i++) {
			for (int j = 0; j < this.yuvWidth; j++) {
				this.yuvImg[i][j] = new YUVPixel((short)16, (short)128, (short)128);
			}
		}
	}
	public YUVImage(YUVImage copyImg) {
		this.yuvImg = copyImg.getYuvImg();
		this.yuvHeight = copyImg.getYuvHeight();
		this.yuvWidth = copyImg.getYuvWidth();
	}
	public YUVImage(RGBImage RGBImg) {
		this.yuvWidth = RGBImg.getWidth();
		this.yuvHeight = RGBImg.getHeight();
		this.yuvImg = new YUVPixel[this.getYuvHeight()][this.getYuvWidth()];
		
		for (int i = 0; i < this.getYuvHeight(); i++) {
			for (int j = 0; j < this.getYuvWidth(); j++) {
				this.yuvImg[i][j] = new YUVPixel((short)(((66*RGBImg.getPixel(i, j).getRed() + 129*RGBImg.getPixel(i, j).getGreen() + 25*RGBImg.getPixel(i, j).getBlue() + 128)>>8) + 16), 
												(short)(((-38*RGBImg.getPixel(i, j).getRed() - 74*RGBImg.getPixel(i, j).getGreen() + 112*RGBImg.getPixel(i, j).getBlue() + 128)>>8) + 128), 
												(short)(((112*RGBImg.getPixel(i, j).getRed() - 94*RGBImg.getPixel(i, j).getGreen() - 18*RGBImg.getPixel(i, j).getBlue() + 128)>>8) + 128));
			}
		}
	}
	public YUVImage(File file) throws UnsupportedFileFormatException, FileNotFoundException {
		
		Scanner input = null;
		String formatLine;
		int dimsRead = 0;
		int i = 0;
		int j = 0;
		short y, u, v;
		
		try {
			if (file.exists()) {
				if (file.isDirectory()) {
					System.out.println("[ERROR] " + file.getName() + " is a directory!");
				}
				if (file.isFile() && file.canRead()) {
					input = new Scanner(file);
					formatLine = input.nextLine();
					if (!formatLine.equals("YUV3")) {
						throw new UnsupportedFileFormatException();
					}
					else {
						while(input.hasNextInt()) {
							if (dimsRead == 0) {
								dimsRead = 1;
								this.setYuvWidth(input.nextInt());
								this.setYuvHeight(input.nextInt());
								this.yuvImg = new YUVPixel[this.getYuvHeight()][this.getYuvWidth()];
							}
							y = (short)input.nextInt();
							u = (short)input.nextInt();
							v = (short)input.nextInt();
							this.getYuvImg()[i][j] = new YUVPixel(y, u, v);
							j++;
							if (j >= this.getYuvWidth()) {
								j = 0;
								i++;
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}
	
	public YUVPixel[][] getYuvImg() {
		return this.yuvImg;
	}
	public int getYuvHeight() {
		return this.yuvHeight;
	}
	public int getYuvWidth() {
		return this.yuvWidth;
	}
	
	public void setYuvWidth(int width) {
		this.yuvWidth = width;
	}
	public void setYuvHeight(int height) {
		this.yuvHeight = height;
	}
	public void setYUVImage(YUVImage image) {
		this.yuvImg = image.getYuvImg();
	}
	
	public String toString() {
		
		StringBuffer retString = new StringBuffer("YUV3\n");
		
		retString.append(Integer.toString(this.getYuvWidth()) + " " + Integer.toString(this.getYuvHeight()) + "\n");
		for (int i = 0; i < this.getYuvHeight(); i++) {
			for (int j = 0; j < this.getYuvWidth(); j++) {
				retString.append(Integer.toString(this.getYuvImg()[i][j].getY()) + " " + Integer.toString(this.getYuvImg()[i][j].getU()) + " " + Integer.toString(this.getYuvImg()[i][j].getV()) + "\n");
			}
		}
		return retString.toString();
	}
	
	public void toFile(File outFile) {
		PrintWriter pw = null;
		if (outFile.isDirectory()) {
			System.out.println("Cannot write to a directory!");
		}
		else if (outFile.exists() && outFile.isFile() && outFile.canWrite()) {
			try {
				pw = new PrintWriter(outFile);
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
			File newFile = new File(outFile.getPath());
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
	
	public void equalize() {
		Histogram histogram = new Histogram(this);
		
		for(int i = 0; i < this.getYuvHeight(); i++) {
			for (int j = 0; j < this.getYuvWidth(); j++) {
				short luminocity = this.getYuvImg()[i][j].getY();
				this.getYuvImg()[i][j] = new YUVPixel(histogram.getEqualizedLuminocity(luminocity), this.getYuvImg()[i][j].getU(), this.getYuvImg()[i][j].getV());
			}
		}
	}
}
