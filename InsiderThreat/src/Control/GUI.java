package Control;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * GUI é a classe que representa a interface gráfica do programa, para inicializar
 * e interagir com os outros componentes do programa.
 *
 * Para inicializar esta GUI, deve ser executado o Main.java
 *
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 * @version 1.0
 * @since 29/11/2018
 */

public class GUI {

	//Frame da GUI
	private JFrame frame;
	//Dimensões pré-determinadas da GUI
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
	//Objeto para interagir com a classe Orchestrator
	private Orchestrator orch;
	//Botões da GUI
	private JButton loadTree;
	private JButton loadUsers;
	private JButton timeWindow;
	private JButton loadPendrivers;
	private JButton loadLogon;
	private JButton loadHttp;
	private JButton getUsers;
	private JButton viewUser;
	private JButton findAnomalies;
	//File chooser da GUI
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    //Objeto OutputStream
    private OutputStream stream;
    //Área de texto para o output do sistema
    private JTextArea output;

    /**
     * Requisita a criação e exibição da GUI, assim como a inicialização do objeto Orchestrator.
     */
	public GUI() {
		orch = new Orchestrator();
		makeGUI();
	}

	/**
     * Cria a GUI e exibe na tela.
	 */
	private void makeGUI() {
		//Criando a frame
		frame = new JFrame("Anomalies Analyser Program");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Criando o menu da GUI
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenuItem item = new JMenuItem("Help");
		item.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(item);
		addHelp(item);

		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		//Botões da GUI para carregamento dos arquivos
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0,1));
		loadUsers = new JButton("Load Users File");
		panel1.add(loadUsers);
		timeWindow = new JButton("Set Time Window");
		panel1.add(timeWindow);
		loadPendrivers = new JButton("Load Pendrive File");
		panel1.add(loadPendrivers);
		loadLogon = new JButton("Load Logon File");
		panel1.add(loadLogon);
		loadHttp = new JButton("Load Http File");
		panel1.add(loadHttp);
		container.add(panel1, BorderLayout.WEST);

		//Botões da GUI para realização de ações do programa
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		getUsers = new JButton("View Employees' ID's.");
		panel2.add(getUsers);
		viewUser = new JButton("View Employee's Information");
		panel2.add(viewUser);
		findAnomalies = new JButton("Find Anomalies");
		panel2.add(findAnomalies);
		container.add(panel2, BorderLayout.SOUTH);

		//Criando a área de texto para o output do sistema
		//Creating the text area for the system's output
		output = new JTextArea(100,100);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(output);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(output.getPreferredScrollableViewportSize());
		container.add(scroll, BorderLayout.CENTER);

		//Desativando os botões
		activateUsers(false);
		activateTime(false);
		activatePendrivers(false);
		activateLogon(false);
		activateHttp(false);
		activateGetUsers(false);
		activateViewUser(false);
		activateFindAnomalies(false);

		//Ativando o botão para carregamento do arquivo dos usuários
		activateUsers(true);
		loadUsers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Thread obj = new Thread() {
						@Override
						public void run(){
							activateUsers(false);
							getFile();
							activateTime(true);
						}
					};
					obj.start();
				}
				catch(RuntimeException b) {
					System.out.println("Unable to activate buttom! Problem: " + b);
				}
			}
		});

		//Ativando o botão para escolha da janela de tempo
		timeWindow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Thread obj = new Thread() {
						@Override
						public void run(){
							activateTime(false);
							getTimeWindow();
							activatePendrivers(true);
						}
					};
					obj.start();
				}
				catch(RuntimeException b) {
					System.out.println("Unable to activate buttom! Problem: " + b);
				}
			}
		});

		//Ativando o botão para carregamento do arquivo de atividades de Pendrive
		loadPendrivers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Thread obj = new Thread() {
						@Override
						public void run(){
							activatePendrivers(false);
							getFile();
							activateLogon(true);
						}
					};
					obj.start();
				}
				catch(RuntimeException b) {
					System.out.println("Unable to activate buttom! Problem: " + b);
				}
			}
		});

		//Ativando o botão para carregamento do arquivo de atividades de Logon
		loadLogon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Thread obj;
					obj = new Thread() {
						@Override
						public void run(){
							activateLogon(false);
							getFile();
							activateHttp(true);
						}
					};
					obj.start();
				}
				catch(RuntimeException b) {
					System.out.println("Unable to activate buttom! Problem: " + b);
				}
			}
		});

		//Ativando o botão de carregamento do arquivo de atividades de Http
		loadHttp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Thread obj = new Thread() {
						@Override
						public void run(){
							activateHttp(false);
							getFile();
							activateGetUsers(true);
							activateViewUser(true);
							activateFindAnomalies(true);
						}
					};
					obj.start();
				}
				catch(RuntimeException b) {
					System.out.println("Unable to activate buttom! Problem: " + b);
				}
			}
		});

		//Ativando o botão que mostra informações sobre os usuários do sistema
		getUsers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Thread obj = new Thread() {
						@Override
						public void run(){
							activateGetUsers(false);
							listUsers();
							activateGetUsers(true);
						}
					};
					obj.start();
				}
				catch(RuntimeException b) {
					System.out.println("Unable to activate buttom! Problem: " + b);
				}
			}
		});

		//Ativando o botão que permite que informações de um usuário sejam mostradas
		viewUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Thread obj = new Thread() {
						@Override
						public void run(){
							activateViewUser(false);
							getUserInfo();
							activateViewUser(true);
						}
					};
					obj.start();
				}
				catch(RuntimeException b) {
					System.out.println("Unable to activate buttom! Problem: " + b);
				}
			}
		});

        //Ativando o botão que inicializa a análise de anomalias
		findAnomalies.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Thread obj = new Thread() {
						@Override
						public void run(){
							activateFindAnomalies(false);
							findAnomalies();
							activateFindAnomalies(true);
						}
					};
					obj.start();
				}
				catch(RuntimeException b) {
					System.out.println("Unable to activate buttom! Problem: " + b);
				}
			}
		});

		//Permitir que o output do sistema seja transferido para a GUI
		redirectSystemStreams();
	}

	/**
	 * Ativar o botão que carrega o arquivo dos usuários.
	 * @param t Recebe "true" para ativar e "false" para desativar.
	 */
	private void activateUsers(boolean t) {
		loadUsers.setEnabled(t);
	}

	/**
	 * Ativar o botão para receber a janela de tempo.
	 * @param t Recebe "true" para ativar e "false" para desativar.
	 */
	private void activateTime(boolean t) {
		timeWindow.setEnabled(t);
	}

	/**
	 * Ativar o botão que carrega o arquivo de atividades de Pendrive.
	 * @param t Recebe "true" para ativar e "false" para desativar.
	 */
	private void activatePendrivers(boolean t) {
		loadPendrivers.setEnabled(t);
	}

	/**
	 * Ativar o botão que carrega o arquivo de atividades de Logon.
	 * @param t Recebe "true" para ativar e "false" para desativar.
	 */
	private void activateLogon(boolean t) {
		loadLogon.setEnabled(t);
	}

	/**
	 * Ativar o botão que carrega o arquivo de atividades de Http.
	 * @param t Recebe "true" para ativar e "false" para desativar.
	 */
	private void activateHttp(boolean t) {
		loadHttp.setEnabled(t);
	}

	/**
	 * Ativar o botão que mostra os usuários registrados no sistema.
	 * @param t Recebe "true" para ativar e "false" para desativar.
	 */
	private void activateGetUsers(boolean t) {
		getUsers.setEnabled(t);
	}

	/**
	 * Ativar o botão para visualizar as informações de um usuário.
	 * @param t Recebe "true" para ativar e "false" para desativar.
	 */
	private void activateViewUser(boolean t) {
		viewUser.setEnabled(t);
	}

	/**
	 * Ativar o botão para iniciar a análise de anomalias.
	 * @param t Recebe "true" para ativar e "false" para desativar.
	 */
	private void activateFindAnomalies(boolean t) {
		findAnomalies.setEnabled(t);
	}

	/**
	 * Adiciona o texta para a opção "Help" do menu.
	 * @param menu O menu da GUI.
	 */
	private void addHelp(JMenuItem menu) {
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "This program analyses a company's data with the goal to identify anomalies in the system based on the online activities executed by the employees.\n"
								+ "The correct order to execute this program is by loading the required csv files (users' information, pendriver activities, logon activies and http activities)\n"
								+ " and placing a time window to be analysed.The correct order for the files to be loaded in the program is the following:\n"
								+ " 1. Load users' information file\n 2. Choose a time window to be analysed\n 3. Load Pendriver activities file\n 4. Load Logon activities file\n 5. Load Http activities file\r\n" +
						          "If those files are not placed in the correct order, the program will not provide the correct analysis. Futhermore, it's possible to choose 3 options after the data is fully loaded:\n"
						        + " 1. View Employees' ID's\n 2. View Employee's Information\n 3. Find Anomalies\r\n" +
					 	          "The first one will show the employees' ID's that are registered in the system while the second one will require one ID to be informed with the goal of showing the employee's information.\n"
					 	        + "Meanwhile, the third option will perform the data's analysis and detect the anomalies in the system.";
				JOptionPane.showMessageDialog(frame, message);
			}
		});
	}

	/**
	 * Pega o arquivo do sistema que está executando o programa.
	 */
	private void getFile() {
		 int returnVal = fileChooser.showOpenDialog(frame);

	     if(returnVal != JFileChooser.APPROVE_OPTION) {
	         return;
	     }
	     File selectedFile = fileChooser.getSelectedFile();
	     orch.readLog(selectedFile.getAbsolutePath());
	}

	/**
	 * Abre área de texto para pegar a janela de tempo a ser analisada.
	 */
	private void getTimeWindow() {
		JTextField begin = new JTextField();
		JTextField end = new JTextField();

	    JPanel panel = new JPanel(new GridLayout(0,1));
	    panel.add(new JLabel("Initial date (dd/MM/YYYY format):"));
	    panel.add(begin);
	    panel.add(Box.createHorizontalStrut(30));
	    panel.add(new JLabel("Final date (dd/MM/YYYY format):"));
	    panel.add(end);

	    int aux = JOptionPane.showConfirmDialog(null, panel,
	               "Time Window", JOptionPane.OK_CANCEL_OPTION);
	    if (aux == JOptionPane.OK_OPTION) {
	    	orch.defineTimeWindow(begin.getText(), end.getText());
	    }
	}

	/**
	 * Solicita que o programa exiba os ID's dos usuários.
	 */
	private void listUsers() {
		orch.visualizeEmployees();
	}

	/**
	 * Exibe informações de um usuário que esteja registrado no sistema.
	 */
	private void getUserInfo() {

			JTextField id = new JTextField();

			JPanel panel = new JPanel(new GridLayout(0,1));
			panel.add(new JLabel("Employee's ID:"));
			panel.add(id);

			int aux = JOptionPane.showConfirmDialog(null, panel,
			               "Employee's Information", JOptionPane.OK_CANCEL_OPTION);
			orch.employeeInformation(id.getText());
	}

	/**
	 * Executa a análise de dados para encontrar as anomalias.
	 */
	private void findAnomalies() {
			orch.analyseEmployees();
	}

	/**
	 * Recebe o texto a ser exibido na área de texto da GUI.
	 * Código encontrado no seguinte link: http://unserializableone.blogspot.com/2009/01/redirecting-systemout-and-systemerr-to.html
	 * @param text Texto a ser exibido.
	 */
	private void updateTextArea(final String text) {
		  SwingUtilities.invokeLater(new Runnable() {
			  public void run() {
				  output.append(text);
			  }
		  });
	}

	/**
	 * Redireciona o output do sistema para ser exibido na área de texto da GUI.
	 * Código encontrado no seguinte link: http://unserializableone.blogspot.com/2009/01/redirecting-systemout-and-systemerr-to.html
	 */
	private void redirectSystemStreams() {
		  stream = new OutputStream() {
		    @Override
		    public void write(int b) throws IOException {
		      updateTextArea(String.valueOf((char) b));
		    }

		    @Override
		    public void write(byte[] b, int off, int len) throws IOException {
		      updateTextArea(new String(b, off, len));
		    }

		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		  };

		  System.setOut(new PrintStream(stream, true));
		  System.setErr(new PrintStream(stream, true));
	}
}
