package packWork;
import packWork.Interface;

// mosteneste clasa Interface
public class GrayLevel extends Interface {
	// calculez aici nivelul de gri pentru fiecare pixel
	public static double getGrayLevel(int p){
		
		// se obtin componentele R, G, B
		int red = (p >> 16) & 0x000000FF; // componenta red e o valoare cuprinsa intre 0-255 din RGB 
		int green = (p >> 8) & 0x000000FF;  // componenta green
		int blue = p & 0x000000FF; // componenta blue
		
		return 0.299*red + 0.587*green + 0.114*blue; // cu aceasta formula returnez nivelul de gri al pixelului
		
	}
	
	//luata din interface prin mostenire
	public void inheritance(String[] args){
		System.out.println("Clasa GrayLevel mosteneste Clasa Interface");
	}
	
	
}
