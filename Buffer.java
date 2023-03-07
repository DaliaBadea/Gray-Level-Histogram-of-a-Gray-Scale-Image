package packWork;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.awt.Graphics2D;

public class Buffer {
	private boolean available = false;
	
	String imgPath; // calea imaginii
	private int height = 1, width = 1; // inaltimea, latimea imaginii
	public BufferedImage imageSfert = null; // folosesc pentru a citi fiecare sfert din informatie
	
	public Buffer(String p){ // aici folosesc constructor
		imgPath = p;
	}
	
	// citirea informatiilor din imaginea sursa o face Producer
	public synchronized void readImg(){
		
		File pathF = new File(this.imgPath);
		try{ // pregatesc imaginea ca sa citesc un sfert din aceasta
			imageSfert = ImageIO.read(pathF);
			
			// iau inaltimea si latimea din imagine
			height = imageSfert.getHeight();
			width = imageSfert.getWidth();		
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		// la fel ca la laborator
		while (!available) {
            try {
                wait(); // Producer asteapta sa poata pune o valoare
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		available = false;
        notifyAll();
	}
	
	// prelucrez informatii din imagine sursa primite de la Producer de catre Consumer
	public synchronized BufferedImage writeImg(int section) throws IOException{
		
		// initializare vector in care pot retine secventele
		BufferedImage imgs[] = new BufferedImage[4];
		
		while (available) { // am folosit modele din laborator, fiecare sectiune va fi scrisa, sunt in total 4 sectiuni
            try {
                wait(); // Consumer asteapta preluarea valorii
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		available = true;
        notifyAll();
        
        // imaginea se imparte in doua parti egale pentru a obtine secventele
        int subImg_W = width / 2;
		int subImg_H = height / 2;
		int current_img = 0;
        
		BufferedImage imgSection = new BufferedImage(subImg_W, subImg_H, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < 2; i++){
			for (int j = 0; j < 2; j++){
				
				// se creeaza spatiu pentru aceste 4 secvente
				imgs[current_img] = new BufferedImage(subImg_W, subImg_H, imageSfert.getType());
				Graphics2D img_creator = imgs[current_img].createGraphics();
				
				// coordonate imagine originala
				int src_first_x = subImg_W * j;
		        int src_first_y = subImg_H * i;
		        
		        // coordonatele secventelor obtinute prin sectionarea imaginii
		        int dst_corner_x = subImg_W * j + subImg_W;
		        int dst_corner_y = subImg_H * i + subImg_H;
		        
		        // crearea secventelor necesare impartirii imaginii
		        img_creator.drawImage(imageSfert, 0, 0, subImg_W, subImg_H, src_first_x, src_first_y, dst_corner_x, dst_corner_y, null);
		        current_img++;
		        
		        imgSection = imgs[section];
			}
		}
        return imgSection;
	}
	
	//Getter ce returneaza inaltimea
	public int getHeight(){
		return height;
	}
	
	//setter care modifica inaltimea
	public void setHeight(int heightNew) {
		height = heightNew;
	}
	
	//Getter ce returneaza latimea
	public int getWidth(){
		return width;
	}
}
