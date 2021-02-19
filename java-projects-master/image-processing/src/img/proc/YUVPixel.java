package img.proc;

public class YUVPixel {
	
	private short Y;
	private short U;
	private short V;
	
	public YUVPixel(short Y, short U, short V) {
		this.Y = Y;
		this.U = U;
		this.V = V;
	}
	public YUVPixel(YUVPixel pixel) {
		this.Y = pixel.getY();
		this.U = pixel.getU();
		this.V = pixel.getV();
	}
	public YUVPixel(RGBPixel pixel) {
		this.Y = (short)(((66*pixel.getRed() + 129*pixel.getGreen() + 25*pixel.getBlue() + 128)>>8) + 16);
		this.U = (short)(((-38*pixel.getRed() - 74*pixel.getGreen() + 112*pixel.getBlue() + 128)>>8) + 128);
		this.V = (short)(((112*pixel.getRed() - 94*pixel.getGreen() - 18*pixel.getBlue() + 128)>>8) + 128);
	}
	
	public short getY() {
		return this.Y;
	}
	public short getU() {
		return this.U;
	}
	public short getV() {
		return this.V;
	}
	
	public void setY(short Y) {
		this.V = Y;
	}
	public void setU(short U) {
		this.U = U;
	}
	public void setV(short V) {
		this.V = V;
	}
	
}
