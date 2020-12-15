package analyze;
import log.Http;
import log.LogEntry;
import log.Logon;
import log.PenDrive;

/**
 * Classe responsável pela estrutura onde os perfis de usuários serão armazenados
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 *
 */
public class Tree {
	private Node root;
	
	public Tree (Node root) {
		this.root = root;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	
	/**
	 * chama o método addPc
	 * @param log - LogEntry a ser inserido
	 * @param root - nó no qual a informação será adicionada aos filhos
	 */
	public void addLogEntry(LogEntry log, Node root){
		this.addPc(log, root);
	}
	
	/**
	 * Soma o valor de uma determinada posição do histograma com 1
	 */
	public void increaseHistogram(int[] histogram, String date) {
		String time = date.substring(11, 13);
		int hour = Integer.parseInt(time);
		histogram[hour]++;
	}
	
	/**
	 * Adiciona um pc a árvore e chama a inserção do próximo nível
	 * @param - log LogEntry que contém o pc a ser inserido
	 * @param - root nó no qual o pc será adicionado ao children
	 */
	private void addPc(LogEntry log, Node root) {
		String pc = log.getPc();
		Node node;
		if (!root.getChildren().containsKey(pc)) {
			Element element = new Element(pc);
			node = new Node(element);
			root.getChildren().put(pc, node);
			this.addAction(log, node);
		}
		else{
			node = root.getChildren().get(pc);
			this.addAction(log, node);
		}
		this.increaseHistogram(root.getHistogram(), log.getDate());
	}
	
	
	/**
	 * Adiciona uma atividade a árvore e chama a inserção do próximo nível
	 * @param log - LogEntry que contém a informação
	 * @param root - nó onde a atividade será inserida
	 */
	private void addAction(LogEntry log, Node root) {
		if(log instanceof Http) {
			Node node;
			String activity = "Http";
			if (!root.getChildren().containsKey(activity)) {
				Element element = new Element(activity);
				node = new Node(element);
				root.getChildren().put(activity, node);
				Http http = (Http)log;
				this.addHttp(http, node);
			}
			else {
				node = root.getChildren().get(activity);
				Http http = (Http)log;
				this.addHttp(http, node);
			}
			this.increaseHistogram(root.getHistogram(), log.getDate());
		}
		else if (log instanceof Logon) {
			String activity = "Logon";
			Node node;
			if (!root.getChildren().containsKey(activity)) {
				Element element = new Element(activity);
				node = new Node(element);
				root.getChildren().put(activity, node);
				Logon logon = (Logon) log;
				this.addLogon(logon, node);
			}
			else {
				node = root.getChildren().get(activity);
				Logon logon = (Logon) log;
				this.addLogon(logon, node);
			}
			this.increaseHistogram(root.getHistogram(), log.getDate());
			//System.out.println(node.getInfo().getId());
		}
		else if (log instanceof PenDrive) {
			String activity = "PenDrive";
			Node node;
			if (!root.getChildren().containsKey(activity)) {
				Element element = new Element(activity);
				node = new Node(element);
				root.getChildren().put(activity, node);
				PenDrive pendrive = (PenDrive) log;
				this.addPenDrive(pendrive, node);
			}
			else {
				node = root.getChildren().get(activity);
				PenDrive pendrive = (PenDrive) log;
				this.addPenDrive(pendrive, node);
			}
			this.increaseHistogram(root.getHistogram(), log.getDate());
			//System.out.println(node.getInfo().getId());
		}
	}
	
	
	/**
	 * Insere uma url na árvore
	 * @param http - Contém a informação a ser inserida
	 * @param root - Nó onde a informação será adicionada ao children
	 */
	private void addHttp(Http http, Node root) {
		String url = http.getUrl();
		Node node;
		if (!root.getChildren().containsKey(url)) {
			Element element = new Element(url);
			node = new Node(element);
			root.getChildren().put(url, node);
		}

		else {
			node = root.getChildren().get(url);
		}
		
		this.increaseHistogram(node.getHistogram(), http.getDate());
		this.increaseHistogram(root.getHistogram(), http.getDate());
	}
	
	
	/**
	 * Insere ações de logon ou logoff na árvore
	 * @param logon - Contém a informação a ser inserida
	 * @param root - Nó onde a informação será adicionada ao children
	 */
	private void addLogon(Logon logon, Node root) {
		String action = logon.getAction();
		Node node;
		if (!root.getChildren().containsKey(action)) {
			Element element = new Element(action);
			node = new Node(element);
			root.getChildren().put(action, node);
		}

		else {
			node = root.getChildren().get(action);
		}
		
		this.increaseHistogram(node.getHistogram(), logon.getDate());
		this.increaseHistogram(root.getHistogram(), logon.getDate());
	}
	
	/**
	 * Adiciona as informações sobre inserção e remoção de um pendrive
	 * @param penDrive - Contém a informação a ser inserida
	 * @param root - Nó onde a informação será adicionada ao children
	 */
	private void addPenDrive(PenDrive penDrive, Node root) {
		String action = penDrive.getAction();
		Node node;
		if (!root.getChildren().containsKey(action)) {
			Element element = new Element(action);
			node = new Node(element);
			root.getChildren().put(action, node);
		}
		else {
			node = root.getChildren().get(action);
		}
		this.increaseHistogram(root.getHistogram(), penDrive.getDate());
		this.increaseHistogram(node.getHistogram(), penDrive.getDate());
	}
}