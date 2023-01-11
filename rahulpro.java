import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

class Rahulproject{
	public static void main(String[] args){
		WindowManagin window1 = new WindowManagin("Welcome to Bank DataBase");
		window1.PrimaryWindow();
		window1.EndProcess();

	}
}


class ControlBoard{
	
	static String sql_db = "rahul";
	static String sql_user = "root";
	static String sql_password = "";


	static String driver_class = "com.mysql.cj.jdbc.Driver";
	static String sql_url = "jdbc:mysql://localhost:3306/" + sql_db;

	

	void RegistrationMySQL(String name, String father_name, String mother_name, String nid, String age, String gender, String phone){
		try{  
			Class.forName(driver_class);
			Connection con = DriverManager.getConnection(sql_url, sql_user, sql_password);
			

			Statement stmt = con.createStatement();

			int cid_generated = CID_generate();

			String query = "INSERT INTO `user-info`(`cid`, `name`, `father_name`, `mother_name`, `gender`, `age`, `nid`, `phone`) VALUES ('" + cid_generated + "', '" + name + "', '" + father_name + "', '" + mother_name + "', '" + nid + "', '" + age + "', '" + gender + "', '" + phone + "')";

			stmt.execute(query);

			query = "INSERT INTO `user-account`(`cid`, `amount`) VALUES ('" + cid_generated + "','0')";

			stmt.execute(query);

			con.close();
		}catch(Exception really){
			System.out.printf("lkj");
		}
	}


	void DepositMySQL(String cid, String amount){
		try{  
			Class.forName(driver_class);
			Connection con = DriverManager.getConnection(sql_url, sql_user, sql_password);
			

			Statement stmt = con.createStatement();
			
			
			String query = "UPDATE `user-account` SET `amount`=`amount`+'" +amount + "' WHERE cid = '" + cid + "'";
	

			stmt.execute(query);

			query = "INSERT INTO `deposit-history`(`cid`, `amount`) VALUES ('"+ cid +"', '"+ amount +"')";
			stmt.execute(query);

			con.close();
		}catch(Exception really){
			System.out.printf("lkj");
		}
	}

	void WithdrawMySQL(String cid, String amount){
		try{  
			Class.forName(driver_class);
			Connection con = DriverManager.getConnection(sql_url, sql_user, sql_password);

			Statement stmt = con.createStatement();
			
			String query = "UPDATE `user-account` SET `amount`=`amount`-'" +amount + "' WHERE cid = '" + cid + "'";

			stmt.execute(query);
			query = "INSERT INTO `withdraw-history`(`cid`, `amount`) VALUES ('"+ cid +"', '"+ amount +"')";
			stmt.execute(query);

			con.close();
		}catch(Exception really){
			System.out.printf("lkj");
		}
	}


	String BalanceMySQL(String cid){
		String balance = "";
		try{  
			Class.forName(driver_class);
			Connection con = DriverManager.getConnection(sql_url, sql_user, sql_password);
			

			Statement stmt = con.createStatement();			
			String query = "SELECT * FROM `user-account` WHERE cid = '" + cid + "'";

			ResultSet rs = stmt.executeQuery(query);            
            while (rs.next()) {
                balance = rs.getString("amount");
            }

			con.close();
		}catch(Exception really){
			System.out.printf("lkj");
		}
		return balance;
	}


	boolean loginCheck(String cid, String password){
		int user_count = 0;
		try{  
			Class.forName(driver_class);
			Connection con = DriverManager.getConnection(sql_url, sql_user, sql_password);
			

			Statement stmt = con.createStatement();			
			String query = "SELECT * FROM `user-account` WHERE cid = '" + cid + "' and password = '" + password + "'";

			ResultSet rs = stmt.executeQuery(query);            
			
            while (rs.next()) {
                user_count++;
            }

			con.close();
		}catch(Exception really){
			System.out.printf("lkj");
		}

		if(user_count == 1){
        	return true;
        }else{
        	return false;
        }
	}

	int CID_generate(){
		int min = 99999;  
		int max = 9999999;  
		double a = Math.random()*(max-min+1)+min;   
		int b = (int)(Math.random()*(max-min+1)+min);  
		return b;
	}
}


class WindowManagin{
	JFrame frame;
	JPanel header;
	JPanel middle;
	JPanel footer;
	JLabel header_text;

	WindowManagin(String header_text){
		frame = new JFrame();
		frame.setTitle("Bank Management System");
		frame.setLayout(null);
		frame.setBounds(100, 100, 550, 480);
		


		header = new JPanel();
		header.setLayout(null);
		header.setBounds(0, 0, 550, 100);
		// header.setBackground(new Color(244, 0, 0));

		middle = new JPanel();
		middle.setLayout(null);
		middle.setBounds(0, 100, 550, 250);
		// middle.setBackground(new Color(0, 244, 0));


		footer = new JPanel();
		footer.setLayout(null);
		footer.setBounds(0, 350, 550, 100);
		// footer.setBackground(new Color(0, 0, 244));


		this.header_text = new JLabel(header_text);
		this.header_text.setFont(new Font("Calibri", Font.BOLD, 29));
		this.header_text.setBounds(100, 20, 350, 80);



		header.add(this.header_text);

		frame.add(header);
		frame.add(middle);
		frame.add(footer);
	}

	void PrimaryWindow(){
		JButton sign_in;
		JButton sign_up;

		sign_in = new JButton("SIGN IN");
		sign_in.setLayout(null);
		sign_in.setBounds(100, 100, 100, 50);
		sign_in.setBackground(new Color(80, 190, 10));
		sign_in.setForeground(new Color(240, 240, 240));

		sign_up = new JButton("SIGN UP");
		sign_up.setLayout(null);
		sign_up.setBounds(350, 100, 100, 50);
		sign_up.setBackground(new Color(190, 80, 10));
		sign_up.setForeground(new Color(240, 240, 240));

		middle.add(sign_in);
		middle.add(sign_up);

		sign_in.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				frame.dispose();
                WindowManagin newWindow = new WindowManagin("Login");
                newWindow.LoginWindow();
                newWindow.EndProcess();
            }
        });

        sign_up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				frame.dispose();
                WindowManagin newWindow = new WindowManagin("Login");
                newWindow.RegistrationWindow();
                newWindow.EndProcess();
            }
        });
	}

	void LoginWindow(){
		
		JLabel username_placeholder;
		JLabel password_placeholder;
		JTextField username;
		JTextField password;

		JButton login;
	

		username_placeholder = new JLabel("Username: ");
		username_placeholder.setBounds(100, 30, 350, 30);

		username = new JTextField();
		username.setBounds(100, 60, 350, 30);


		password_placeholder = new JLabel("Password: ");
		password_placeholder.setBounds(100, 100, 350, 30);

		password = new JTextField();
		password.setBounds(100, 130, 350, 30);


		login = new JButton("Login");
		login.setBounds(100, 190, 350, 35);
		login.setBackground(new Color(12, 149, 56));
		login.setForeground(new Color(244, 244, 244));


		middle.add(username_placeholder);
		middle.add(username);

		middle.add(password_placeholder);	
		middle.add(password);

		middle.add(login);

		login.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	ControlBoard sequel = new ControlBoard();
            	if(sequel.loginCheck(username.getText(), password.getText()) == true){
            		frame.dispose();
	                WindowManagin newWindow = new WindowManagin("Dashboard");
	                newWindow.DashboardWindow(username.getText(), password.getText());
	                newWindow.EndProcess();	
            	}
            }
        });
	}

	void RegistrationWindow(){
		
		JLabel name_placeholder;
		JLabel father_name_placeholder;
		JLabel mother_name_placeholder;
		JLabel nid_placeholder;
		JLabel age_placeholder;
		JLabel gender_placeholder;
		JLabel phone_placeholder;

		JTextField name;
		JTextField father_name;
		JTextField mother_name;
		JTextField nid;
		JTextField age;
		JTextField gender;
		JTextField phone;


		JButton register;
	

		name_placeholder = new JLabel("Name: ");
		name_placeholder.setBounds(100, 0, 350, 25);

		name = new JTextField();
		name.setBounds(100, 20, 350, 25);


		father_name_placeholder = new JLabel("Father's Name: ");
		father_name_placeholder.setBounds(100, 45, 350, 25);

		father_name = new JTextField();
		father_name.setBounds(100, 65, 150, 25);


		mother_name_placeholder = new JLabel("Mother's Name: ");
		mother_name_placeholder.setBounds(300, 45, 350, 25);

		mother_name = new JTextField();
		mother_name.setBounds(300, 65, 150, 25);


		gender_placeholder = new JLabel("gender: ");
		gender_placeholder.setBounds(100, 90, 350, 25);

		gender = new JTextField();
		gender.setBounds(100, 110, 150, 25);


		age_placeholder = new JLabel("Age: ");
		age_placeholder.setBounds(300, 90, 350, 25);

		age = new JTextField();
		age.setBounds(300, 110, 150, 25);


		nid_placeholder = new JLabel("National Id Card: ");
		nid_placeholder.setBounds(100, 135, 350, 25);

		nid = new JTextField();
		nid.setBounds(100, 155, 350, 25);


		phone_placeholder = new JLabel("Phone: ");
		phone_placeholder.setBounds(100, 180, 350, 25);

		phone = new JTextField();
		phone.setBounds(100, 200, 350, 25);




		register = new JButton("Register");
		register.setBounds(100, 10, 350, 35);
		register.setBackground(new Color(12, 149, 56));
		register.setForeground(new Color(244, 244, 244));





		middle.add(name_placeholder);
		middle.add(name);

		middle.add(father_name_placeholder);
		middle.add(father_name);

		middle.add(mother_name_placeholder);
		middle.add(mother_name);

		middle.add(gender_placeholder);
		middle.add(gender);

		middle.add(age_placeholder);
		middle.add(age);

		middle.add(nid_placeholder);
		middle.add(nid);

		middle.add(phone_placeholder);
		middle.add(phone);

		footer.add(register);



		register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ControlBoard sequel = new ControlBoard();
                sequel.RegistrationMySQL(name.getText(), father_name.getText(), mother_name.getText(), gender.getText(), age.getText(), nid.getText(), phone.getText());
                frame.dispose();
                WindowManagin newWindow = new WindowManagin("Login");
                newWindow.LoginWindow();
                newWindow.EndProcess();
            }
        });


	}



	void DashboardWindow(String cid, String password){
		JButton deposit;
		JButton withdraw;
		JButton balance;
		JButton exitB;

		deposit = new JButton("Deposit");
		deposit.setFocusPainted(false);
		deposit.setLayout(null);
		deposit.setBounds(100, 50, 100, 50);
		deposit.setBackground(new Color(80, 10, 10));
		deposit.setForeground(new Color(240, 240, 240));

		withdraw = new JButton("Withdraw");
		withdraw.setFocusPainted(false);
		withdraw.setLayout(null);
		withdraw.setBounds(350, 50, 100, 50);
		withdraw.setBackground(new Color(10, 10, 10));
		withdraw.setForeground(new Color(240, 240, 240));

		balance = new JButton("Balance");
		balance.setFocusPainted(false);
		balance.setLayout(null);
		balance.setBounds(100, 150, 100, 50);
		balance.setBackground(new Color(80, 10, 190));
		balance.setForeground(new Color(240, 240, 240));

		exitB = new JButton("Exit");
		exitB.setFocusPainted(false);
		exitB.setLayout(null);
		exitB.setBounds(350, 150, 100, 50);
		exitB.setBackground(new Color(210, 16, 13));
		exitB.setForeground(new Color(240, 240, 240));

		middle.add(deposit);
		middle.add(withdraw);
		middle.add(balance);
		middle.add(exitB);

		deposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.dispose();
                WindowManagin newWindow = new WindowManagin("Deposit");
                newWindow.DepositWindow(cid, password);
                newWindow.EndProcess();
            }
        });

        withdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.dispose();
                WindowManagin newWindow = new WindowManagin("Withdraw");
                newWindow.WithdrawWindow(cid, password);
                newWindow.EndProcess();
            }
        });

        balance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.dispose();
                WindowManagin newWindow = new WindowManagin("Balance");
                newWindow.BalanceWindow(cid, password);
                newWindow.EndProcess();
            }
        });

        exitB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ControlBoard sequel = new ControlBoard();
                frame.dispose();
            }
        });
	}


	void DepositWindow(String cid, String password_){
		JLabel password_placeholder;
		JLabel amount_placeholder;
		JTextField password;
		JTextField amount;

		JButton deposit;
	

		password_placeholder = new JLabel("Password: ");
		password_placeholder.setBounds(100, 30, 350, 30);

		password = new JTextField();
		password.setBounds(100, 60, 350, 30);


		amount_placeholder = new JLabel("Amount: ");
		amount_placeholder.setBounds(100, 100, 350, 30);

		amount = new JTextField();
		amount.setBounds(100, 130, 350, 30);


		deposit = new JButton("Deposit");
		deposit.setBounds(100, 190, 350, 35);
		deposit.setBackground(new Color(12, 149, 56));
		deposit.setForeground(new Color(244, 244, 244));


		middle.add(password_placeholder);	
		middle.add(password);

		middle.add(amount_placeholder);	
		middle.add(amount);

		middle.add(deposit);

		deposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if(password_.equals(password.getText())){
            		ControlBoard sequel = new ControlBoard();
            		sequel.DepositMySQL(cid, amount.getText());
            	}
            	frame.dispose();
                WindowManagin newWindow = new WindowManagin("Dashboard");
                newWindow.DashboardWindow(cid, password_);
                newWindow.EndProcess();
            }
        });
	}


	void WithdrawWindow(String cid, String password_){
		JLabel password_placeholder;
		JLabel amount_placeholder;
		JTextField password;
		JTextField amount;

		JButton withdraw;
	

		password_placeholder = new JLabel("Password: ");
		password_placeholder.setBounds(100, 30, 350, 30);

		password = new JTextField();
		password.setBounds(100, 60, 350, 30);


		amount_placeholder = new JLabel("Amount: ");
		amount_placeholder.setBounds(100, 100, 350, 30);

		amount = new JTextField();
		amount.setBounds(100, 130, 350, 30);


		withdraw = new JButton("Withdraw");
		withdraw.setBounds(100, 190, 350, 35);
		withdraw.setBackground(new Color(12, 149, 56));
		withdraw.setForeground(new Color(244, 244, 244));


		middle.add(password_placeholder);	
		middle.add(password);

		middle.add(amount_placeholder);	
		middle.add(amount);

		middle.add(withdraw);

		withdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if(password_.equals(password.getText())){
            		ControlBoard sequel = new ControlBoard();
            		sequel.WithdrawMySQL(cid, amount.getText());
            	}
            	frame.dispose();
                WindowManagin newWindow = new WindowManagin("Dashboard");
                newWindow.DashboardWindow(cid, password_);
                newWindow.EndProcess();
            }
        });
	}

	void BalanceWindow(String cid, String password_){
		JLabel password_placeholder;
		JTextField password;

		JButton chckbalance;
	

		password_placeholder = new JLabel("Password: ");
		password_placeholder.setBounds(100, 60, 350, 30);

		password = new JTextField();
		password.setBounds(100, 90, 350, 30);



		chckbalance = new JButton("Check Balance");
		chckbalance.setBounds(100, 140, 350, 35);
		chckbalance.setBackground(new Color(12, 149, 56));
		chckbalance.setForeground(new Color(244, 244, 244));


		middle.add(password_placeholder);	
		middle.add(password);

		middle.add(chckbalance);

		chckbalance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if(password_.equals(password.getText())){
            		ControlBoard sequel = new ControlBoard();
            		String myBalance = sequel.BalanceMySQL(cid);
            		JOptionPane.showMessageDialog(null, "current balance: " + myBalance + " dollar");
            	}
            	frame.dispose();
                WindowManagin newWindow = new WindowManagin("Dashboard");
                newWindow.DashboardWindow(cid, password_);
                newWindow.EndProcess();
            }
        });
	}

	void EndProcess(){
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}