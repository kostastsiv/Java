package img.proc;

public class RGBPixel {
	
	private short red;
	private short green;
	private short blue;
	
	/* Constructors */
	public RGBPixel(short red, short green, short blue) {	/* FULLY FUNCTIONAL */
		this.setRed(red);
		this.setGreen(green);
		this.setBlue(blue);
	}
	public RGBPixel(RGBPixel pixel) {	/* FULLY FUNCTIONAL */
		this.setRed(pixel.getRed());
		this.setGreen(pixel.getGreen());
		this.setBlue(pixel.getBlue());
	}
	public RGBPixel(YUVPixel pixel) {	/* SLIGHT DIFFERENCE FROM ORIGINAL */
		short C = (short)(pixel.getY() - 16);
		short D = (short)(pixel.getU() - 128);
		short E = (short)(pixel.getV() - 128);
		
		this.setRed(clip((298*C + 409*E + 128) >> 8));
		this.setGreen(clip((298*C - 100*D - 208*E + 128) >> 8));
		this.setBlue(clip((298*C + 516*D + 128) >> 8));
	}
	
	/* Getters */
	public short getRed() {
		return this.red;
	}
	public short getGreen() {
		return this.green;
	}
	public short getBlue() {
		return this.blue;
	}
	public int getRGB() {	/* FULLY FUNCTIONAL */
		int RGB = 0x00000000;
		
		RGB = (RGB | this.getRed())<<8;
		RGB = (RGB | this.getGreen())<<8;
		RGB = (RGB | this.getBlue());
		
		return RGB;
	}
	
	public String toString() {
		String retStr = new String();
		
		retStr = retStr.concat("(" + Integer.toString((int)this.getRed()) + ", " + Integer.toString((int)this.getGreen()) + ", " + Integer.toString((int)this.getBlue()) + ")");
		return retStr;
	}
	
	
	/* Setters */
	public void setRed(short red) {
		this.red = red;
	}
	public void setGreen(short green) {
		this.green = green;
	}
	public void setBlue(short blue) {
		this.blue = blue;
	}
	public void setRGB(int value) {	/* FULLY FUNCTIONAL */
		int getColor = 0x000000FF;
		
		this.setBlue((short)(value & getColor));
		value = value >>8;
		this.setGreen((short)(value & getColor));
		value = value >> 8;
		this.setRed((short)(value & getColor));
	}
	public final void setRGB(short red, short green, short blue) {
		this.setRed(red);
		this.setGreen(green);
		this.setBlue(blue);
	}
	
	private short clip(int result) {
		if (result < 0) {
			return 0;
		}
		if (result > 255) {
			return 255;
		}
		return (short)result;
	}
}
