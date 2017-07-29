package socket;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.*;

import javax.swing.*;


public class Client extends JFrame{
	JFileChooser fileChooser = new JFileChooser();
	private Socket socket=null;
	JButton btn=new JButton("发送");
	JButton btn1=new JButton("传送文件");
	JTextArea textarea=new JTextArea(2,120);
	String s;
	String message="";
	JLabel label=new JLabel("");
	File temp;
	private FileInputStream f;
	private DataOutputStream dos;
	public static void main(String[] args) {
		Client window=new Client();
		
		window.setBounds(600,300,1600,1000);
		window.setTitle("消息发送");
		
		
		
		
	}
	public Client(){
		try {
			socket=new Socket("localhost",8890);
			temp = new File("d:/temp", "chat.txt");
			new readLineThread();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		init();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private void init() {
		setLayout(new BorderLayout());
		add(label,BorderLayout.NORTH);
		add(new JLabel("请输入您要发送的消息"),BorderLayout.WEST);
		add(textarea,BorderLayout.CENTER);
		add(btn,BorderLayout.EAST);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					transfersocket();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		add(btn1,BorderLayout.SOUTH);
		btn1.setMnemonic(KeyEvent.VK_U);
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				filetransfer();
			}
		});
		
	}
	
	protected void filetransfer() {
		// TODO Auto-generated method stub
		try {
            try {
            	fileChooser.showOpenDialog(this);
                File file = fileChooser.getSelectedFile();
                f = new FileInputStream(file);
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();
                byte[] sendBytes = new byte[1024];
                int length = 0;
                while((length = f.read(sendBytes, 0, sendBytes.length)) > 0){
                    dos.write(sendBytes, 0, length);
                    dos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(f!= null)
                    f.close();
                if(dos != null)
                    dos.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	protected void transfersocket() throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		//socket = new Socket("localhost", 18976);
		s=textarea.getText();
		textarea.setText("");
		Reader reader  = new StringReader(s);
		PrintWriter print=new PrintWriter(socket.getOutputStream());
		BufferedReader buffer=new BufferedReader(reader);
		print.println(buffer.readLine());
		print.flush();
		FileOutputStream in = new FileOutputStream(temp);
		
		//BufferedReader buffered=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//message+="对方发来消息:"+buffered.readLine()+"\n";
		message+="您回复消息："+s+"\n";
		byte bt[] = new byte[1024]; 
		bt = message.getBytes();  
		in.write(bt,0, bt.length);  
        in.close();  
		System.out.println(message);
		label.setText(message);
		print.close();
		buffer.close();
		//buffered.close();
	}
	class readLineThread extends Thread {
		private BufferedReader buffer;
		public readLineThread(){
			try {
				buffer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    }
		public void run(){
			try {
				while(true){
					String result =buffer.readLine();
					if("byeClient".equals(result)){
						break;
					}else{
						System.out.println(result);
					}
				} 
				
			}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
		}
	}
}
