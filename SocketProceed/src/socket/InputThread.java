package socket;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class InputThread extends Thread{
	private Socket socket;
	private OutputThread out;
	private OutputThreadMap map;
	private ObjectInputStream oos;
	private boolean isStart=true;
	private FileOutputStream fos;
	private DataInputStream dis;

	public InputThread(Socket socket,OutputThread out, OutputThreadMap map){
		this.socket=socket;
		this.out=out;
		this.map=map;
		try {
			oos=new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setStart(boolean isStart){
		this.isStart=isStart;
	}
	public void run(){
		try{
			while(isStart){
				readMessage();
			}
			if(oos!=null)
					oos.close();
			if(socket!=null)
				socket.close();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void readMessage() throws IOException,ClassNotFoundException{
		Object readObject=oos.readObject();
		if(readObject!=null && readObject instanceof TranObject){
			TranObject reader =(TranObject) readObject;
			switch(reader.getType()){
			case FILE:
				String fileName = null;
				try {
						fileName = dis.readUTF();
						fos = new FileOutputStream(new File(fileName));
						start();
				} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				byte[] sendBytes = new byte[1024];
				System.out.println("开始接收文件"+fileName);
			    while(true){
		                int read = 0;
		                try {
							read = dis.read(sendBytes);
							 if(read == -1)
				                    break;
				                fos.write(sendBytes, 0, read);
				                fos.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		               
		         }
		         System.out.println("接收文件成功");
			case MESSAGE:
				int id=reader.getUser();
				OutputThread out=map.getById(id);
			}
		}
	}
			
	
}
