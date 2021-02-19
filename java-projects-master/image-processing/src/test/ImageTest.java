package test;
import java.io.*;

import img.proc.*;

public class ImageTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RGBPixel pixel = new RGBPixel((short)255, (short)255, (short)255);
		YUVPixel yPixel = new YUVPixel(pixel);
		
		System.out.println("(" + yPixel.getY() + " " + yPixel.getU() + " " + yPixel.getV() + ")");
		RGBPixel pixel2 = new RGBPixel(yPixel);
		System.out.println(pixel2.toString());
		
		try{
			YUVImage yuvImg = new YUVImage(new File("/home/gkoffas/nice_galaxy.yuv"));
			Histogram hgram = new Histogram(yuvImg);
			System.out.println(hgram.toString());
			yuvImg.equalize();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (UnsupportedFileFormatException ex) {
			ex.printStackTrace();
		}
	}
}
