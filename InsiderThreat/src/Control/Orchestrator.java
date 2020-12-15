package Control;

import java.util.HashMap;

import analyze.Analyser;
import analyze.Forest;
import analyze.TimeWindow;
import analyze.Tree;
import log.Log;


/**
 * Classe responsável pelo gerenciamento do programa
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 *
 */
public class Orchestrator {
	private Forest forest;
	private TimeWindow window;
	
	public Orchestrator() {
		forest = new Forest();
	}
	
	/**
	 * Realiza a leitura dos arquivos
	 * @param fileName - Caminho para o arquivo
	 */
	public void readLog(String fileName) {
		boolean finished = false;
		Log log = new Log(fileName);
		Object newElement = null;
		log.openFile();
		while(finished != true) {
			newElement = log.readFile();
			if(newElement != null) {
				addOnForest(newElement);
			} else {
				finished = true;
			}
		}
		log.closeFile();
	}
	
	/**
	 * Envia um objeto para inserção na floresta
	 * @param element - objeto a ser inserido
	 */
	private void addOnForest(Object element) {
		this.forest.addElement(element);
	}	
	
	/**
	 * Cria uma janela de tempo e a envia para inserção na árvore
	 * @param begin - data inicial
	 * @param end - data final
	 */
	public void defineTimeWindow(String begin, String end) {
		this.window = new TimeWindow(begin, end);
		forest.addTimeWindow(window);
	}
	
	/**
	 * Permite a vizualização da lista de usuários presntes na floresta
	 */
	public void visualizeEmployees() {
		this.forest.listUsers();
	}
	
	/**
	 * Permite a vizualização dos dados de um usuário
	 * @param id - id do usuário que se deseja vizualizar
	 */
	public void employeeInformation(String id) {
		this.forest.userInfo(id);
	}
	
	/**
	 * Envia os dados de usuários para análises
	 */
	public void analyseEmployees() {
		Analyser analyse = new Analyser(buildSupport());
	}
	
	/**
	 * Gera uma uma floresta com árvores onde os usuários estão agrupados de acordo com seu role
	 * @return estrutura com os dados reagrupados
	 */
	private HashMap<String,Tree> buildSupport() {
		return forest.buildRoleForest();
	}
}
