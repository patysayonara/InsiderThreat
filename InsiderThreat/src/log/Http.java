package log;

/**
 * Classe que contém as informações sobre o acesso dos usuários a sites
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 *
 */
public class Http extends LogEntry{
	private String url;
	
	public Http(String id, String date, String user, String pc, String url) {
		super(id, date, user, pc);
		this.setUrl(url);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
