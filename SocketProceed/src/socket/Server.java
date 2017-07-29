package socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Server {
	ServerSocket serversocket;
    Socket socket;
    
    public static void main(String[] args) {
		new Server();
			
	}
    public Server() {
		try {
			serversocket = new ServerSocket(8890);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true){
			try {
				socket = serversocket.accept();
				new SeverThread(socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
    class SeverThread{
    	private Socket socket;
    	InputThread in;
        OutputThread out;
        private OutputThreadMap map;
		public SeverThread(Socket socket){
    		this.socket=socket;
    		map=OutputThreadMap.getInstance();
    	}

		public void run() {
			out = new OutputThread(socket, map);//  
            // 先实例化写消息线程,（把对应用户的写线程存入map缓存器中）  
            in = new InputThread(socket, out, map);// 再实例化读消息线程  
            out.setStart(true);  
            in.setStart(true);  
            in.start();  
            out.start();  
			
		}

		
    	
    }
	


