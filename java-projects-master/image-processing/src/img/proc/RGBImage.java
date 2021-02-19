package img.proc;

public class RGBImage implements Image {
	
	private RGBPixel[][] image;
	private int colordepth;
	private int height;
	private int width;
	public static final int MAX_COLORDEPTH = 255;
	
	public RGBImage(int width, int height, int colordepth) {
		this.setColorDepth(colordepth);
		this.image = new RGBPixel[height][width];
		this.setHeight(height);
		this.setWidth(width);
	}
	public RGBImage(RGBImage copyImg) {
		this.setImage(copyImg.getImage());
		this.setColorDepth(copyImg.getColorDepth());
		this.setHeight(copyImg.getHeight());
		this.setWidth(copyImg.getWidth());
	}
	public RGBImage(YUVImage YUVImg) {
		this.image = new RGBPixel[YUVImg.getYuvHeight()][YUVImg.getYuvWidth()];
		this.setWidth(YUVImg.getYuvWidth());
		this.setHeight(YUVImg.getYuvHeight());
		this.setColorDepth(255);
		
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				this.getImage()[i][j] = new RGBPixel(YUVImg.getYuvImg()[i][j]);
			}
		}
	}
	
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public RGBPixel[][] getImage() {
		return this.image;
	}
	public int getColorDepth() {
		return this.colordepth;
	}
	public RGBPixel getPixel(int x, int y) {
		return this.image[x][y];
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setImage(RGBPixel[][] image) {
		this.image = image;
	}
	public void setPixel(int x, int y, RGBPixel pixel) {
		this.image[x][y] = pixel;
	}
	public void setColorDepth(int cDepth) {
		this.colordepth = cDepth;
	}
	
	private short average(short array[]) {
		int sum = 0;
		for (short element:array) {
			sum += element;
		}
		return ((short)(sum / array.length));
	}
	
	public void grayscale() {	/* FULLY FUNCTIONAL */
		
		int tempRed, tempGreen, tempBlue;
		
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				tempRed = this.image[i][j].getRed();
				tempGreen = this.image[i][j].getGreen();
				tempBlue = this.image[i][j].getBlue();
				this.image[i][j].setRed((short)(tempRed*0.3 + tempGreen*0.59 + tempBlue*0.11));
				this.image[i][j].setGreen((short)(tempRed*0.3 + tempGreen*0.59 + tempBlue*0.11));
				this.image[i][j].setBlue((short)(tempRed*0.3 + tempGreen*0.59 + tempBlue*0.11));
			}
		}
	}
	
	public void doublesize() {	/* FULLY FUNCTIONAL */
		RGBImage newImage = new RGBImage(2*this.width, 2*this.height, this.colordepth);
		
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				newImage.image[2*i][2*j] = this.image[i][j];
				newImage.image[2*i+1][2*j] = this.image[i][j];
				newImage.image[2*i][2*j+1] = this.image[i][j];
				newImage.image[2*i+1][2*j+1] = this.image[i][j];
			}
		}
		this.setImage(newImage.getImage());
		this.setHeight(newImage.getHeight());
		this.setWidth(newImage.getWidth());
	}
	
	public void halfsize() {	/* FULLY FUNCTIONAL */
		RGBImage newImage = new RGBImage(this.getWidth()/2, this.getHeight()/2, this.getColorDepth());
		
		for (int i = 0; i < newImage.getHeight(); i++) {
			for(int j = 0; j < newImage.getWidth(); j++) {
				newImage.getImage()[i][j] = new RGBPixel((short)0, (short)0, (short)0);
			}
		}
		
		for (int i = 0; i < newImage.height; i++) {
			for(int j = 0; j < newImage.width; j++) {
				short redInput[] = {this.image[2*i][2*j].getRed(), this.image[2*i+1][2*j].getRed(), this.image[2*i][2*j+1].getRed(), this.image[2*i+1][2*j+1].getRed()};
				newImage.image[i][j].setRed(average(redInput));
				
				short greenInput[] = {this.image[2*i][2*j].getGreen(), this.image[2*i+1][2*j].getGreen(), this.image[2*i][2*j+1].getGreen(), this.image[2*i+1][2*j+1].getGreen()};
				newImage.image[i][j].setGreen(average(greenInput));
				
				short blueInput[] = {this.image[2*i][2*j].getBlue(), this.image[2*i+1][2*j].getBlue(), this.image[2*i][2*j+1].getBlue(), this.image[2*i+1][2*j+1].getBlue()};
				newImage.image[i][j].setBlue(average(blueInput));
			}
		}
		this.setImage(newImage.getImage());
		this.setHeight(newImage.getHeight());
		this.setWidth(newImage.getWidth());
	}
	
	public void rotateClockwise() {	/* FULLY FUNCTIONAL */
		RGBImage newImg = new RGBImage(this.getHeight(), this.getWidth(), this.getColorDepth());
		for (int i = 1; i <= this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				newImg.getImage()[j][this.getHeight() - i] = this.getImage()[i-1][j];
			}
		}
		this.setImage(newImg.getImage());
		this.setHeight(newImg.getHeight());
		this.setWidth(newImg.getWidth());
	}
	
}
