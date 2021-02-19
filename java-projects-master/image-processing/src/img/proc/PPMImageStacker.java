package img.proc;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class PPMImageStacker {
	
	private List<PPMImage> PPMImgStack;
	private PPMImage stackPPMImg;
	
	public PPMImageStacker(File dir) throws FileNotFoundException, UnsupportedFileFormatException {	/* FULLY FUNCTIONAL */
		
		if (!dir.exists()) {
			System.out.println("[ERROR] Directory " + dir.getName() + " does not exist!");
		}
		else {
			if (!dir.isDirectory()) {
				System.out.println("[ERROR] " + dir.getName() + " is not a directory!");
			}
			else {
				this.PPMImgStack = new ArrayList<PPMImage>();
				File[] filesList = dir.listFiles();
				for (File file : filesList) {
					try {
						this.PPMImgStack.add(new PPMImage(file));
						this.stackPPMImg = new PPMImage(PPMImgStack.get(0));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}
	
	public void stack() {	/* FULLY FUNCTIONAL */
		
		for (int i = 0; i < this.stackPPMImg.getHeight(); i++) {
			for (int j = 0; j < this.stackPPMImg.getWidth(); j++) {
				this.stackPPMImg.setPixel(i, j, average(i, j, PPMImgStack));
			}
		}
	}
	
	public PPMImage getStackedImage() {	/* FULLY FUNCTIONAL */
		return this.stackPPMImg;
	}
	
	
	private RGBPixel average(int x, int y, List<PPMImage> list) {
		
		int size = list.size();
		short sumRed = 0;
		short sumGreen = 0;
		short sumBlue = 0;
		
		for (int i = 0; i < size; i++) {
			sumRed += list.get(i).getPixel(x, y).getRed();
			sumGreen += list.get(i).getPixel(x, y).getGreen();
			sumBlue += list.get(i).getPixel(x, y).getBlue();
		}
		
		RGBPixel newPixel = new RGBPixel((short)(sumRed/size), (short)(sumGreen/size), (short)(sumBlue/size)); 
		
		return (newPixel);
	}
	
	
}
