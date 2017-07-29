package socket;

import java.io.*;
import java.net.*;

public class OutputThread {
	private Socket socket;
	private ObjectOutputStream oos;
	private boolean isStart=true;
	private TranObject object;
	private OutputThreadMap map;

	public OutputThread(Socket socket,OutputThreadMap map){
		
		try {
			this.socket=socket;
			this.map=map;
			oos=new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setStart(boolean isStart){
		this.isStart=isStart;
	}
	public void setMessage(TranObject object){
		this.object=object;
		synchronized (this) {
			notify();
		}
	}
	public void start(){
		try{
			while(isStart){
				synchronized (this) {
					wait();
				}
				if(object!=null){
					oos.close();
				}
				if(socket!=null)
					socket.close();
			}
			
		}catch (InterruptedException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
}
