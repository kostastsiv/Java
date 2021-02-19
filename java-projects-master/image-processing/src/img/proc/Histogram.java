package img.proc;

import java.io.*;
import java.util.Arrays;
import java.util.IntSummaryStatistics;

public class Histogram {
	
	private static final int MAX_LUMIN_POSSIBLE = 235;
	private int[] yValues;
	private double[] pmfValues;
	private double[] cdfValues;
	private int[] finValues;
	
	
	public Histogram(YUVImage img) {
		this.yValues = new int[Histogram.MAX_LUMIN_POSSIBLE + 1];
		this.pmfValues = new double[Histogram.MAX_LUMIN_POSSIBLE + 1];
		this.cdfValues = new double[Histogram.MAX_LUMIN_POSSIBLE + 1];
		this.finValues = new int[Histogram.MAX_LUMIN_POSSIBLE + 1];
		
		/* Setting initial table */
		int k = 0;
		while (k <= Histogram.MAX_LUMIN_POSSIBLE) {
			for(int i = 0; i < img.getYuvHeight(); i++) {
				for (int j = 0; j < img.getYuvWidth(); j++) {
					if(img.getYuvImg()[i][j].getY() == (short)k) {
						this.yValues[k]++;
					}
				}
			}
			k++;
		}
		
		/* Setting PMF table */
		for(k = 0; k <= Histogram.MAX_LUMIN_POSSIBLE; k++) {
			this.pmfValues[k] = (double)(this.yValues[k])/(double)(img.getYuvHeight()*img.getYuvWidth());
		}
		
		this.cdfValues[0] = this.pmfValues[0];
		/* Setting CDF table */
		for (k = 1; k <=Histogram.MAX_LUMIN_POSSIBLE; k++) {
			this.cdfValues[k] = this.cdfValues[k-1] + this.pmfValues[k];
		}
	}
	
	public String toString() {
		int scaleFactor = 80;
		int instances;
		StringBuffer retStr = new StringBuffer("");
		
		IntSummaryStatistics stat = Arrays.stream(this.yValues).summaryStatistics();
		int max = stat.getMax();
		System.out.println(max);
		
		
		for (int k = 0; k <= Histogram.MAX_LUMIN_POSSIBLE; k++) {
			retStr.append(Integer.toString(k) + ": ");
			instances = (int)Math.ceil(((double)this.yValues[k]/(double)max)*scaleFactor);
			for (int i = 0; i < instances; i++) {
				retStr.append('*');
			}
			retStr.append('\n');
		}
		return retStr.toString();
	}
	
	public void toFile(File file) throws IOException {
		PrintWriter pw = null;
		
		if (file.isDirectory()) {
			System.out.println("[ERROR] Cannot write in a directory!");
		}
		else if (file.exists() && file.isFile() && file.canWrite()) {
			try {
				pw = new PrintWriter(file);
				pw.print("");
				pw.println(this.toString());
			} catch (SecurityException ex) {
				ex.printStackTrace();
			} catch (Exception ex) {
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
	
	public void equalize() {
		/* Find Max luminocity */
		for (int k = 0; k <=Histogram.MAX_LUMIN_POSSIBLE; k++) {
			this.finValues[k] = (int)Math.floor(this.cdfValues[k] * (double)Histogram.MAX_LUMIN_POSSIBLE);
		}
	}
	
	public short getEqualizedLuminocity(int luminocity) {
		this.equalize();
		return (short)finValues[luminocity];
	}
}
