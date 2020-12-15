package analyze;
import java.util.HashMap;

/**
 * Classe cujos objetos podem ser armazenados em uma árvore.
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 *
 */
public class Node {
	private int[] histogram;
	private HashMap<String, Node> children;
	private Element info;

	public Node (Element info) {
		this.info =info;
		children = new HashMap<String, Node>();
		this.histogram = new int[24];
	}

	public int[] getHistogram() {
		return histogram;
	}

	public void setHistogram(int[] histogram) {
		this.histogram = histogram;
	}

	public HashMap<String, Node> getChildren() {
		return children;
	}

	public void setChildren(HashMap<String, Node> children) {
		this.children = children;
	}

	public Element getInfo() {
		return info;
	}

	public void setInfo(Element info) {
		this.info = info;
	}



}
