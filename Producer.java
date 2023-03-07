package packWork;
public class Producer extends ThreadClass {
	private Buffer buffer;
	
	// aici folosesc constructor
	public Producer(Buffer b){
		super(true);
		buffer = b;
	}
	
	@Override
	public void ThreadStart(){
		
		// citesc datele despre imaginea mea si le voi afisa mereu cand s-a citit cate un sfert din aceasta
		for (int i = 1; i <= 4; i++) {
            buffer.readImg();
            System.out.println("Sectiunea " + i);
        }
	}
}

