package packWork;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import packWork.Histogram;

import java.io.File;


public class Imagine extends Histogram{
	private static BufferedImage img = null, grayScaleImg = null;
	
	// Metoda incarcare imagine 
	public static void uploadImg(String pathImg){
		File f = new File(pathImg);
		try{
			img = ImageIO.read(f);
			grayScaleImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
	        System.out.println("Citire completa");
	    }catch(IOException e){
	        System.out.println("Error uploadImg: " + e); //tratarea exceptiilor
	    }
	}
	
	// Noua imagine obtinuta
	public static void newImg(String pathImg, BufferedImage grayScaleImg){
		File f = new File(pathImg);
		try{
			ImageIO.write(grayScaleImg, "bmp", f);
	        System.out.println("Scriere completa");
	    }catch(IOException e){
	        System.out.println("Error saveImg: " + e);
	    }
	}
	
	// getter care imi returneaza imaginea originala
	public static BufferedImage getImg(){
		return img;
	}
	
	// getter care imi returneaza noua imagine obtinuta
	public static BufferedImage getNewImg(){
		return grayScaleImg;
	}
	

	// Metoda folosita pentru mostenirea clasei Interface
	public void inheritance(String[] args){
		for(String a : args){
			System.out.println("Interface => GrayLevel => ConvertGrayscale => Histogram => Imagine => Start");
		}
	}

}
