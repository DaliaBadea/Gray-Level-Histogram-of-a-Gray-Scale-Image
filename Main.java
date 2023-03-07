package packTest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

import packWork.Buffer;
import packWork.Consumer;
import packWork.Imagine;
import packWork.Producer;

public class Main {
	
	public static String EnterData(Scanner data, String status){// introduc calea in linia mea de comanda
		
		String path = null;
		System.out.println("Introdu calea imaginii (" + status + "):");
		
		path = data.nextLine();
		return path;
	}
	
	public static void main(String[] args) throws IOException{
		
		// Aici voi introduce informatiile imaginii
		long startTime = System.currentTimeMillis(); // aici voi masura TIMPUL DE EXECUTIE, cum e specificat in cerintele proiectului
		String pathOriginalImage = null;
		
		if(args.length == 0){
			System.out.println("Informatiile imaginii sunt scrise in consola");// se citeste din consola
			Scanner data1 = new Scanner(System.in);
			pathOriginalImage = EnterData(data1, "imaginea originala"); // se scrie calea imaginii pe care vreau s-o modific
		}
		else // se citeste din linia de comanda cu argumente
			pathOriginalImage = args[0];
			
		System.out.println("Ai introdus aceasta cale: " + pathOriginalImage);
		
		long endTime = System.currentTimeMillis(); // pana aici se citeste
		System.out.println("Citirea informatiei despre imagine a durat " + (endTime - startTime) + " milisecunde"); // afisare cat a durat executia
		
		// Acum CITESC imaginea
		Imagine.uploadImg(pathOriginalImage);
		endTime = System.currentTimeMillis();
		System.out.println("Etapa de citire a imaginii a durat " + (endTime - startTime) + " milisecunde");
		
		// MultiThreading
		startTime = System.currentTimeMillis();
		
		Buffer buffer = new Buffer(pathOriginalImage);
		buffer.imageSfert = new BufferedImage(Imagine.getImg().getWidth(), Imagine.getImg().getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Producer producer = new Producer(buffer); // aici am folosit informatii prezentate la laborator
		producer.start();
		Consumer consumer = new Consumer(buffer);
		consumer.start();
		
		//astept sa se sfarseasca thread-urile
		try {//sincronizarea
            consumer.join();
        } catch(InterruptedException e) {
        	 System.out.println("Interrupted");
        }
		endTime = System.currentTimeMillis();
		System.out.println("Multithreading + GrayLevel au durat " + (endTime - startTime) + " milisecunde");
		
		// se creeaza FISIERUL pentru a scrie in el histograma de unde vad valoarea maxima din histograma
		File F = new File("fileHistograma.txt");
		FileWriter fw = new FileWriter(F);
		
		try {// se verifica daca fisierul a fost creat deja
			if(F.createNewFile()){ System.out.println("Fisierul a fost creat");}
			
		} catch (IOException e) {
			System.out.println("File not got created");
			e.printStackTrace();
		}
		
		// execut algoritmul pentru intreaga imagine
		
		startTime = System.currentTimeMillis();
		Imagine.uploadImg(pathOriginalImage); // citirea imaginii aici
		
		Imagine.GrayImage(Imagine.getImg(), Imagine.getNewImg(), fw, 1); // Imagine black white
		Imagine.newImg("bwImage.bmp", Imagine.getNewImg()); // unde salvez imaginea alb negru 
		
		fw.write("\n------------- Histograma -------------\n");
		
		Imagine.GrayImage(Imagine.getImg(), Imagine.getNewImg(), fw, 0); // Imaginea Grayscale
		
		Imagine.newImg("ImagineaObtinuta.bmp", Imagine.getNewImg()); // unde salvez imaginea obtinuta + numele ei
		
		endTime = System.currentTimeMillis();
		System.out.println("Final " + (endTime - startTime) + " milisecunde");
		
		fw.close();
	}
}
