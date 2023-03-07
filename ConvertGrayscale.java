package packWork;
import java.awt.image.BufferedImage;

import packWork.GrayLevel;

// mosteneste clasa GrayLevel
public class ConvertGrayscale extends GrayLevel {
	public static int[][] convertColor(BufferedImage img, int height, int width){
		int[][] pixels = new int[width][height]; // creez o matrice care are dimensiunile imaginii mele
		
		// se converteste fiecare pixel de culoare intr-unul gri
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				pixels[j][i] = (int) Math.round(getGrayLevel(img.getRGB(j, i))); // aici obtin matricea cu valori de gri a imaginii
			}
		}
		return pixels; // si aici returnez aceasta matrice
	}
	
	public void inheritance(String[] args){
		System.out.println("Clasa ConvertGrayscale mosteneste clasa GrayLevel si ea mosteneste Clasa Interface");
	}
    
}
