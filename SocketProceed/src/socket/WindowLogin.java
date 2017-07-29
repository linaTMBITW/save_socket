package socket;

import java.awt.event.*;
import java.sql.*;

import javax.swing.*;;

public class WindowLogin extends JFrame implements ActionListener{
	JTextField name,pwd;
	String sname,spwd;
	JButton log,register;
	Box baseBox,boxV1,boxV2,boxV3;
	WindowLogin(){
		init();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void init() {
		boxV1=Box.createHorizontalBox();
		boxV2=Box.createHorizontalBox();
		boxV3=Box.createHorizontalBox();
		boxV1.add(Box.createHorizontalStrut(100));
		boxV1.add (new JLabel("ÐÕÃû£º"));
		boxV1.add(Box.createHorizontalStrut(100));
		name=new JTextField(10);

		
		boxV1.add(name);
		boxV2.add(Box.createHorizontalStrut(100));
		boxV2.add (new JLabel("ÃÜÂë£º"));
		boxV2.add(Box.createHorizontalStrut(100));
		pwd=new JTextField(10);
		boxV2.add(pwd);
		log=new JButton("µÇÂ½");
		register=new JButton("×¢²á");
		log.addActionListener(this);
		register.addActionListener(this);
		boxV3.add (log);
		boxV3.add (register);
		baseBox=Box.createVerticalBox();
		baseBox.add(Box.createVerticalStrut(100));
		baseBox.add(boxV1);
		baseBox.add(Box.createVerticalStrut(100));
		baseBox.add(boxV2);
		baseBox.add(Box.createVerticalStrut(100));
		baseBox.add(boxV3);
		baseBox.add(Box.createVerticalStrut(100));
		add(baseBox);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		sname=name.getText().trim();
		spwd=name.getText().trim();

		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		ResultSet rs = null;
		Statement sql = null;
		Connection con;
		try {
				con=DriverManager.getConnection("jdbc:odbc:myData","","");
				sql=con.createStatement();
				rs=sql.executeQuery("select * from user");
				
		} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		}
			
		
		if(e.getSource()==log){
			boolean login=true;
			try {
				while(rs.next()&& login==false){
					String getname=rs.getString(1);
					String getpwd=rs.getString(2);
					if(getname.trim().equals(sname) && getpwd.trim().equals(spwd)){
						System.out.println("µÇÂ½³É¹¦");
						login=true;
						
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(login==true){
				this.dispose();
				Client window=new Client();
				window.setBounds(600,300,1600,1000);
				window.setTitle("ÏûÏ¢·¢ËÍ");
			}
		}else if(e.getSource()==register){
			try {
				sql.execute("insert int user(name,pwd)values('"+sname+"','"+spwd+"')");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
