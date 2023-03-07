package packWork;
public abstract class ThreadClass extends Thread {
	private boolean consum;
	
	public abstract void ThreadStart() throws InterruptedException;
	
	// aici folosesc constructor
	public ThreadClass(boolean c){
		super();
		consum = c;
	}
	
	// La fel ca la laborator, voi afisa cand thread-ul a pornit si cand s-a terminat executia lui
	public void run(){
		System.out.println("(ThreadClass) Thread-ul a inceput executia");
		try{
			this.ThreadStart();
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		System.out.println("(ThreadClass) Thread-ul a terminat executia");
	}
}
