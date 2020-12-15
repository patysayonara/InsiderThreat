package log;

/**
 * Classe que contém as informações sobre o usuário ter logado ou deslogado de uma máquina
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 *
 */
public class Logon extends LogEntry {
	private String action;

	public Logon(String id, String date, String user, String pc, String action) {
		super(id, date, user, pc);
		this.setAction(action);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
