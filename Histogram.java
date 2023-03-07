package packWork;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

   // mosteneste clasa ConvertGrayscale
   public class Histogram extends ConvertGrayscale {
	
   // reprezentarea histogramei nivelurilor de gri intr-un fisier folosind simbolul -
   public static void HistogramFile(int[] imgA, FileWriter fw, int val) throws IOException{
		String output = " ";
		
		for(int i = 0; i < 256; i++){
			output = "Valoare pixel gri: " + i + " Numarul de aparitii: " + imgA[i] + " " + output;
			
			// numarul de apartii ale nivelului de gri dintr-o imagine poate fi extrem de mare
			// ar dura prea mult scrierea in fisier .txt, asa ca micsorez numarul de valori scrise in acesta prin impartirea la 4000
			
			if(imgA[i]/4000 < 4000 && imgA[i]/4000 > 1000)
				output += "------------------";
			else if(imgA[i]/4000 < 1000 && imgA[i]/4000 > 100)
				output += "--------";
			else if(imgA[i]/4000 < 100 && imgA[i]/4000 > 0)
				output += "---";
			else if(imgA[i]/4000 == 0)
				output += "-";
			for(int j = 0; j < (imgA[i]/4000); j++){
				output +="-";
				}
			if(imgA[i] == val)
				output +="--(aceasta e valoarea maxima)";
			
			// scrierea in fisier a nivelului de gri impreuna cu numarul acestuia de aparitii e reprezentata printr-o -
			fw.write(output + "\n");
			output = " ";
		}
	}
	
	// functie care imi returneaza un vector de aparitii al nivelurilor de gri de la 0-255
	public static int[] createArray(int[][] m, int h, int w){
		int index = 0;
		int countVal[] = new int[256];
		
		for (int k = 0; k < 256; k++){ // numarul de aparitii al valorilor de gri e numarat
			for (int i = 0; i < h; i++){ // parcurg matricea pentru a gasi valoarea maxima din histograma
				for (int j = 0; j < w; j++){
					if (m[j][i] == k){ // numar de cate ori apare valoarea unui pixel in matrice (valorile histogramei)
						index++; 
					}
				}
			}
			countVal[k] = index; // retin numarul de aparitii pentru fiecare pixel
			index = 0; // numarul de aparitii se reseteaza cand se trece la urmatorul pixel
		}
		return countVal;
	}
	
	// functie ce returneaza valoarea de gri a maximului histogramei
	public static int maxHist(int[][] m, int h, int w, FileWriter fw) throws IOException{
		int maxVal = 0, grayVal = 0; // initializez variabilele de care am nevoie
		int countVal[] = createArray(m, h, w);
		
		for (int k = 0; k < 256; k++){ // parcurg vectorul pentru a gasi valoare de gri a maximului histogramei
			if (countVal[k] > maxVal){ // compar nr de aparitii al valorilor de gri ale histogramei
				maxVal = countVal[k]; // salvez valoarea maxima gasita
				grayVal = k; // salvez valoarea pixelului ce a apaut de cele mai multe ori in histograma
			}
		}	
		HistogramFile(countVal, fw, maxVal); // se creeaza fisierul care va contine histograma
		
		return grayVal; // returnez valoarea de gri a maximei histogramei
	} 
	
	// metoda principala pentru Gray Level Histogram
	public static void GrayImage(BufferedImage img, BufferedImage grayScaleImg, FileWriter fw, int val) throws IOException{
		int[][] imgM = convertColor(img, img.getHeight(), img.getWidth()); // matrice cu valorile de gri ale imaginii 
		
		// imagine niveluri gri
		int maxVal = maxHist(imgM, img.getHeight(), img.getWidth(), fw); // aflu valoarea maxima de gri
		if(maxVal > 250) // valorile sunt cuprinse intre 0-255, daca am adauga 255+5 am depasi acest interval admisibil
			maxVal = 250;
		
		for (int i = 0; i < img.getHeight(); i++){
			for (int j = 0; j < img.getWidth(); j++){
				if(val == 0){
					if(imgM[j][i] < maxVal - 5 || imgM[j][i] > maxVal + 5 ) // verific daca valoarea nu face parte din intervalul +/-5 al nivelului maxim 
						imgM[j][i] = 255; // pixelul ia valoarea de 255 (alb)
				}
				
				grayScaleImg.setRGB(j, i, new Color(imgM[j][i], imgM[j][i], imgM[j][i]).getRGB()); // imaginea rezultata de Gray Scale Image (0) /Black White Image (1)
			}
		}
	} 
	
	public void inheritance(String[] args){
		System.out.print("Clasa Histogram mosteneste clasa ConvertGrayscale care mosteneste clasa GrayLevel care mosteneste Clasa Interface");
	}

}
