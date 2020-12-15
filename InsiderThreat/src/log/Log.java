package log;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import analyze.User;

/**
 * Classe responsável pela leitura de arquivos de log
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 *
 */
public class Log {
	private Scanner scanFile;
	private String fileName;
	
	public Log (String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * interpreta as informações de um arquivo atribuindo-as aos objetos correspodentes
	 * @return - Objeto correspondente ao arquivo lido
	 */
	public Object readFile() {
		Object element = null;
		String logline;
		if(scanFile.hasNextLine()) {
			logline = scanFile.nextLine();
			if(fileName.contains("ldap")) {
				String[] elements = logline.split(",");
				User user = new User(elements[0], elements[1], elements[2], elements[3], elements[4]);
				element = user;
			} 
			else if(fileName.contains("device")){
				String[] elements = logline.split(",");
				PenDrive pd = new PenDrive(elements[0], elements[1], elements[2], elements[3], elements[4]);
				element = pd;  
			}
			else if(fileName.contains("http")) {
				String[] elements = logline.split(",");
				Http http = new Http(elements[0], elements[1], elements[2], elements[3], elements[4]);
				element = http;
			}
			else if (fileName.contains("logon")) {
				String[] elements = logline.split(",");
				Logon logon = new Logon(elements[0], elements[1], elements[2], elements[3], elements[4]);
				element = logon;
			}
		} 
		return element;
	}
	
	/**
	 * Abre um arquivo
	 */
	public void openFile() {
		try {
			scanFile = new Scanner(new File(fileName));
			scanFile.nextLine();
		} 
		catch(FileNotFoundException e) {
            System.out.println("Program unable to read file! Problem: " + e);
        }
	}
	
	/**
	 * Fecha um arquivo
	 */
	public void closeFile() {
		scanFile.close();
	}
}
