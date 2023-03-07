package packWork;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Consumer extends ThreadClass{
	private Buffer buffer;
	
	public Consumer(Buffer b){
		super(false);
		buffer = b;
	}
	
	@Override
	public void ThreadStart() throws InterruptedException{
		for(int i = 0; i < 4; i++){ 
			// sectionarea imaginii in 4 secvente
			BufferedImage imgSection = new BufferedImage(buffer.getWidth(), buffer.getHeight(), BufferedImage.TYPE_INT_ARGB);
			try {
				imgSection = buffer.writeImg(i); 
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			File f = new File("sectiune" + String.valueOf(i + 1) + ".bmp");
			try {
				ImageIO.write(imgSection, "bmp", f); // se scrie fiecare secventa
			} catch (IOException e) {
				e.printStackTrace();
			}
			sleep(1000);// precum la laborator
		}
	}
}
