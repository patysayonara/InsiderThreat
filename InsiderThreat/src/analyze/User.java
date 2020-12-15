package analyze;

/**
 * Classe cujos objetos armazenam os dados de um usuário
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 *
 */
public class User extends Element {
	private String name;
	private String email;
	private String domain;
	private String role;
	
	public User(String name, String id, String domain, String email, String role) {
		super(id);
		this.name = name;
		this.email = email;
		this.domain = domain;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * Sobrecarga do método toString para que possa ser realizada a impressão dos objetos do tipo user
	 */
	@Override
	public String toString() {
		return "Id: " + super.getId() + "\n" + "Name: " + this.name + "\n" + "Email: " + this.email +"\n" + "Domain: " +this.domain + "\n" + "Role: " + this.role; 
	}

}
