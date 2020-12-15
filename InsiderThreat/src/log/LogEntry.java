package log;

/**
 * Classe responssável pelo armazenamento dos dados em comum entre os diferentes tipos de atividades
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 *
 */
public class LogEntry {
	private String id;
	private String date;
	private String user;
	private String pc;
	
	public LogEntry(String id, String date, String user, String pc) {
		this.id = id;
		this.date = date;
		this.user = user;
		this.pc = pc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

}
