package analyze;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import log.LogEntry;


/**
 * Classe responsável pela estrutura onde serão armazenadas as ávores.
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 *
 */
public class Forest {
	private HashMap<String, Tree> forest;

	public Forest() {
		forest = new HashMap<String, Tree>();
	}

	public HashMap<String, Tree> getForest(){
		return forest;
	}

	public void setForest(HashMap<String, Tree> forest){
		this.forest = forest;
	}

	/**
	 * Responsável por identificar o tipo de um elemento e enviá-lo para o método de inserção adequado.
	 * @param el Elemento que será inserido.
	 */
	public void addElement(Object el) {
		if(el instanceof User) {
			addUser((User) el);
		} else {
			addLogEntry((LogEntry) el);
		}
	}

	/**
	 * Insere um usuário na raiz de uma árvore e insere essa aŕvore na floresta.
	 * @param user Usuário a ser inserido.
	 */
	public void addUser(User user) {
		if (!this.forest.containsKey(user.getId())) {
			Node node = new Node(user);
			Tree tree = new Tree(node);
			forest.put(user.getId(),tree);
		}
	}

	/**
	 * Insere uma janela de tempo aos filhos da raiz de cada árvore.
	 * @param time Janela de tempo a ser inserida.
	 */
	public void addTimeWindow(TimeWindow time) {
		Set<String> keys = forest.keySet();
		for (String key : keys) {
			if(key != null) {
				Node root = forest.get(key).getRoot();
				Node tNode = new Node(time);
				root.getChildren().put(time.getId(), tNode);
			}

		}
	}


	/**
	 * Verifica se um LogEntry é adequado para ser inserido em uma árvore e o envia para essa inserção.
	 * @param logEntry LogEntry a ser inserido.
	 */
	public void addLogEntry(LogEntry logEntry) {
		String userId = logEntry.getUser().substring(5);
		String sDate = logEntry.getDate();
		Date date = null;
		if(this.forest.containsKey(userId)) {
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			try {
				date = formater.parse(sDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Tree tree = forest.get(userId);
			Set<String> keys = tree.getRoot().getChildren().keySet();
			for(String key : keys) {
				Node node = forest.get(userId).getRoot().getChildren().get(key);
				TimeWindow time = (TimeWindow) node.getInfo();

				if(time.compareDateTime(date)) {
					tree.addLogEntry(logEntry, node);
					Node hNode = tree.getRoot();
					tree.increaseHistogram(hNode.getHistogram(), logEntry.getDate());
				}
			}
		}
	}

	/**
	 * Realiza a impressão dos dados de um usuário.
	 * @param userId ID do usuário a ter seus dados impressos.
	 */
	public void userInfo(String userId) {
		if(this.forest.containsKey(userId)) {
			Node userNode = forest.get(userId).getRoot();
			User user = (User) userNode.getInfo();
			System.out.println("Employee name: " + user.getName());
			System.out.println("Employee role: " + user.getRole());
			System.out.println("Employee domain: " + user.getDomain());
			System.out.println("Employee e-mail: " + user.getEmail());
			System.out.println("Activity histogram: ");
			for(int i = 0; i < 24; i++) {
				System.out.print(userNode.getHistogram()[i]+" ");
			}
			System.out.println();
		}
		else {
			System.out.println("Invalid ID, there are no employees registered under that identifier.");
		}
	}

	/**
	 * Lista todos os usuários presentes na floresta
	 */
	public void listUsers() {
		Set<String> keys = forest.keySet();
		System.out.println("Employees' ID's:");
		for (String key : keys) {
			if(key != null) {
				System.out.println(key);

			}
		}
	}

	/**
	 * Cria uma nova floresta onde as árvores tem como raiz um nó contendo uma role e todos os usuários que possuem essa role passam a ser seus filhos
	 * @return nova floresta onde os usuários estão agrupados de acordo com a função
	 */
	public HashMap<String,Tree> buildRoleForest(){
		HashMap<String,Tree> support = new HashMap<String,Tree>();
		Set<String> keys = forest.keySet();
		for (String key : keys) {
			if(key != null) {
				Node nodeUser = forest.get(key).getRoot();
				if(isActive(nodeUser)) {
					User user = (User) nodeUser.getInfo();
					if(!support.containsKey(user.getRole())) {
						Element role = new Element(user.getRole());
						Node nodeRole = new Node(role);
						nodeRole.getChildren().put(user.getId(), nodeUser);
						Tree treeRole = new Tree(nodeRole);
						support.put(user.getRole(), treeRole);
					}
					else {
						Tree treeRole = support.get(user.getRole());
						Node nodeRole = treeRole.getRoot();
						nodeRole.getChildren().put(user.getId(), nodeUser);
					}
				}
			}
		}
		adjustHistograms(support);
		return support;
	}

	/**
	 * Calcula o histograma médio.
	 * @param supportForest Floresta de árvores reagrupadas de acordo com a função.
	 */
	private void adjustHistograms(HashMap<String,Tree> supportForest) {
		Set<String> keys = supportForest.keySet();
		for(String key : keys) {
			Tree roleTree = supportForest.get(key);
			Node roleNode = roleTree.getRoot();
			int numUsers = roleNode.getChildren().size();
			Set<String> users = roleNode.getChildren().keySet();
			for(String id : users) {
				for(int i = 0; i < 24; i++) {
					roleNode.getHistogram()[i] += roleNode.getChildren().get(id).getHistogram()[i];
				}
			}
			for(int i = 0; i < 24; i++) {
				roleNode.getHistogram()[i] /= numUsers;
			}
		}
	}

	/**
	 * Verifica se um usuário realizou atividades ou não.
	 * @param user Usuário a ser verificado.
	 * @return true se realizou atividades ou false se não.
	 */
	private boolean isActive(Node user) {
		int[] histogram = user.getHistogram();
		int verifier = 0;
		for(int i = 0; i < 24; i++) {
			verifier += histogram[i];
		}
		if(verifier == 0) {
			return false;
		}
		else {
			return true;
		}
	}
}
