package log;

/**
 * Classe que contém as informações sobre inserção e remoção de pendrives em máquinas
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 *
 */
public class PenDrive extends LogEntry {
	private String action;

	public PenDrive(String id, String date, String user, String pc, String action) {
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
