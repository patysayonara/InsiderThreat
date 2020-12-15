package analyze;

/**
 * Classe responável pelos objetos que serão armazenados em um nó.
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 * @version 1.0
 */
public class Element {
	private String id;

	public Element (String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
