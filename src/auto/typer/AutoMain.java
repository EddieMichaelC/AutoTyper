package auto.typer;

import java.awt.AWTException;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.text.NumberFormatter;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class AutoMain {

	private JFrame frmAutoTyperV;
	private JTextField msg;
	private Robot r;
	private String mess;
	private int interval;
	private JComboBox<String> itPerS;
	private JButton start;
	private Timer timer;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutoMain window = new AutoMain();
					window.frmAutoTyperV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AutoMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAutoTyperV = new JFrame();
		frmAutoTyperV.setResizable(false);
		frmAutoTyperV.setTitle("Auto Typer v1.0");
		frmAutoTyperV.setBounds(697, 417, 423, 174);
		frmAutoTyperV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAutoTyperV.getContentPane().setLayout(null);
		
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
		
		JLabel lblmsg = new JLabel("Message to Type:");
		lblmsg.setBounds(10, 55, 121, 14);
		frmAutoTyperV.getContentPane().add(lblmsg);
		
		msg = new JTextField();
		msg.setBounds(114, 52, 293, 20);
		frmAutoTyperV.getContentPane().add(msg);
		msg.setColumns(10);
		
		JLabel inst1 = new JLabel("Enter Message Below");
		inst1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		inst1.setHorizontalAlignment(SwingConstants.CENTER);
		inst1.setVerticalAlignment(SwingConstants.TOP);
		inst1.setBounds(10, 24, 397, 20);
		frmAutoTyperV.getContentPane().add(inst1);
		
		JButton stopBtn = new JButton("Stop");
		stopBtn.setBounds(10, 111, 397, 23);
		frmAutoTyperV.getContentPane().add(stopBtn);
		stopBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				start.setVisible(true);
				stopBtn.setVisible(false);
				msg.setFocusable(true);
			}
			
		});
		stopBtn.setVisible(false);
		
		ActionListener maximised = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (frmAutoTyperV.getExtendedState() == Frame.NORMAL) {
					timer.stop();
					start.setVisible(true);
					stopBtn.setVisible(false);
					msg.setFocusable(true);
				}
			}
			
		};
		
		ActionListener started = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				msg.setFocusable(false);
				try {
					r = new Robot();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (char c:mess.toCharArray()) {
					int code = KeyEvent.getExtendedKeyCodeForChar(c);
					if (Character.isUpperCase(c)) 
						r.keyPress(KeyEvent.VK_SHIFT);
					r.keyPress(code);
					r.keyRelease(code);
					if (Character.isUpperCase(c))
						r.keyRelease(KeyEvent.VK_SHIFT);
				}
				r.keyPress(KeyEvent.VK_ENTER);
				r.keyRelease(KeyEvent.VK_ENTER);
			}
		};
		
		start = new JButton("Start");
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(msg.getText().isEmpty())
					JOptionPane.showMessageDialog(frmAutoTyperV, "Please input a message");
				else {
					frmAutoTyperV.setState(Frame.ICONIFIED);
					mess = msg.getText();
					start.setVisible(false);
					stopBtn.setVisible(true);
					switch (itPerS.getSelectedIndex()) {
						case 0:
							interval = 1000;
							break;
						case 1:
							interval = 2000;
							break;
						case 2:
							interval = 5000;
							break;
						case 3:
							interval = 10000;
							break;
						case 4:
							interval = 20000;
							break;
					}

					timer = new Timer(5000,maximised);
					timer.addActionListener(started);
					timer.setDelay(interval);
					timer.start();
				}
				
			}
			
		});
		start.setBounds(10, 111, 397, 23);
		frmAutoTyperV.getContentPane().add(start);
		
		itPerS = new JComboBox<String>();
		itPerS.setBounds(200, 80, 132, 20);
		frmAutoTyperV.getContentPane().add(itPerS);
		
		JLabel lblHowOften = new JLabel("How Often:");
		lblHowOften.setBounds(87, 83, 81, 14);
		frmAutoTyperV.getContentPane().add(lblHowOften);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 417, 21);
		frmAutoTyperV.getContentPane().add(menuBar);
		
		JMenu mnMain = new JMenu("Help");
		menuBar.add(mnMain);
		
		JMenuItem mntmInstructions = new JMenuItem("Instructions");
		mntmInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frmAutoTyperV, "Enter message into textbox and then select how \noften you would like the message to be typed out. \nonce you hit start, screen will minimize and will \ntype out the message until you restore the screen.");
			}
		});
		mnMain.add(mntmInstructions);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmAutoTyperV, "Developed by Eddie Caraballo on April of 2017. \nVersion 1.0");
			}
		});
		mnMain.add(mntmAbout);
		
		itPerS.addItem("Every Second");
		itPerS.addItem("Every 2 Seconds");
		itPerS.addItem("Every 5 Seconds");
		itPerS.addItem("Every 10 Seconds");
		itPerS.addItem("Every 20 Seconds");
		
	}
}
