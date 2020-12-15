package analyze;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe que contém o intervalo de tempo no qual as atividades serão avaliadas
 * @author
 * MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 */
public class TimeWindow extends Element {
	private Date initialDate;
	private Date finalDate;
	
	/**
	 * Converte Strings em objetos do tipo Date e os atribui aos parâmetros da classe
	 * @param initialDate - data inicial
	 * @param finalDate - data final
	 */
	public TimeWindow(String initialDate, String finalDate) {
		super(initialDate+","+finalDate);
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		
		if (initialDate != null) {
			if(this.isFormatRight(initialDate)) {
				String initialString = initialDate;
				System.out.println(initialString);
				try {
					this.initialDate = formater.parse(initialString);
					System.out.println(this.initialDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("invalid format, use the format: dd/MM/yyyy");
			}
		}
		else {
			System.out.println("informe uma data");
		}
		
		if (finalDate != null) {
			if(this.isFormatRight(initialDate)) {
				String finalString =  finalDate;
				System.out.println(finalString);
				try {
					this.finalDate = formater.parse(finalString);
					System.out.println(this.finalDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("invalid format, use the format: dd/MM/yyyy");
			}
		}
		else {
			System.out.println("informe uma data");
		}		
	}
	
	public Date getInitialDate() {
		return initialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}
	
	/**
	 * Verifica se uma data está no formato correto
	 * @param date - data a ser verificada
	 * @return - Valor da verificação
	 */
	public boolean isFormatRight(String date) {
		char [] aux =date.toCharArray();
				
		if(aux[2]== '/' && aux[5]== '/') {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Verifica se uma data está dentro da janela de tempo
	 * @param date - Data a ser verificada
	 * @return - Valor da verificação
	 */
	public boolean compareDateTime (Date date) {
		if(date != null) {
			if(this.initialDate != null && this.finalDate != null) {
				if (date.after(initialDate) && date.before(finalDate)) {
					return true;
				}
				else
					return false;
			}
			else if(this.initialDate != null && this.finalDate == null) {
				if (date.after(initialDate) ) {
					return true;
				}
				else
					return false;
			}
			else if(this.initialDate == null && this.finalDate != null) {
				if (date.before(finalDate)) { 
					return true;
				}
				else
					return false;
			}
			else {
				return true;
			}
		}
		return false;
	}
	
}