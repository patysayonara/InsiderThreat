package analyze;
import java.util.HashMap;
import java.util.Set;
import java.util.Arrays;

/**
 * Classe para realização das análises.
 * @author MARIA ESTER DE CARVALHO VEIGA and PATRICIA SAYONARA GOES DE ARAUJO
 * @version 1.0
 */

public class Analyser {
	private HashMap<String,Node> anomalies;
	private HashMap<String,String> thresholds;

	public Analyser(HashMap<String,Tree> supportTree) {
		anomalies = new HashMap<String,Node>();
		thresholds = new HashMap<String,String>();
		analyseTree(supportTree);
	}


	/**
	 * Método principal responsável por chamar todos os outros métodos da classe na ordem correta para que seja realizada a análise.
	 * @param supportTree Collection que armazena os agrupamentos de usuários.
	 */
	private void analyseTree(HashMap<String,Tree> supportTree) {
		Set<String> keys = supportTree.keySet();
		for(String key : keys) {
			if(key != null) {
				registerRole(key);
				Node roleNode = supportTree.get(key).getRoot();
				Set<String> employees = roleNode.getChildren().keySet();
				double[] distances = new double[roleNode.getChildren().size()];
				int i = 0;
				for(String employee : employees) {
					if(employee != null) {
						double dist = calculateDistance(roleNode.getHistogram(), roleNode.getChildren().get(employee).getHistogram());
						distances[i] = dist;
						i++;
					}
				}
				double threshold = findThreshold(distances);
				registerThreshold(key,Double.toString(threshold));
				findAnomalies(roleNode,threshold);
			}
		}
		listAnomalies();
	}

	/**
	 * Método que realiza o registro do limite de normalidade referente a cada função.
	 * @param role Função
	 * @param threshold Limite de normalidade
	 */
	private void registerThreshold(String role, String threshold) {
		thresholds.put(role, threshold);
	}


	/**
	 * Método que realiza o registro das funções, para que as anomalias possam ser agrupadas de acordo com a função dos usuários.
	 * @param role Função a ser registrada.
	 */
	private void registerRole(String role) {
		if(!anomalies.containsKey(role)) {
			Element idRole = new Element(role);
			Node roleNode = new Node(idRole);
			anomalies.put(role, roleNode);
		}
	}
	/**
	 * Calcula a distancia Euclidiana entre o histograma médio e o histograma do usuário.
	 *
	 * @param histRole Histograma médio
	 * @param histNode Histograma do usuário
	 * @return double Distância euclidiana
	 */
	private double calculateDistance(int[] histRole, int[] histNode) {
		double dist = 0.0;
		for(int i = 0; i < histRole.length; i++) {
			dist += Math.pow((histNode[i] - histRole[i]),2);
		}
		return Math.sqrt(dist);
	}


	/**
	 * Calcula o limite que separa usuários com atividades normais de usuários com anomalia.
	 * @param distances Array com as distâncias euclidianas de todos os usuários.
	 * @return double Valor do limite de normalidade.
	 */
	private double findThreshold(double[] distances) {
		Arrays.sort(distances);
		double q1 = 0.0;
		double q3 = 0.0;
		if(distances.length % 2 == 0) {
			q1 = findMedian(distances, distances.length/2, 0, false);
			q3 = findMedian(distances, distances.length/2, distances.length/2,true);
		}
		else {
			q1 = findMedian(distances, distances.length/2-1, 0, false);
			q3 = findMedian(distances, distances.length/2-1, distances.length/2+1,true);
		}

		double iqr = q3 - q1;
		return (q3 + (iqr*1.5));
	}


	/**
	 * Método que encontra a mediana da primeira parte do vetor e a mediana da segunda parte do vetor, logo,
	 * retornando o primeiro e o terceiro quartil.
	 * Código baseado no encontrado nos seguinte link: https://www.javacodex.com/Math-Examples/Find-The-Median-Value-Of-A-Set-Of-Numbers
	 * @param distances Vetor que contém as distâncias.
	 * @param length Tamanho a ser considerado do vetor.
	 * @param start Começo do vetor a ser considerado.
	 * @param upperHalf Sinaliza se a parte do vetor é a primeira (false) ou a segunda (true).
	 * @return a mediana.
	 */
	private double findMedian(double[] distances, int length, int start, boolean upperHalf) {
		double median = 0.0;
		double first = 0.0;
		double second = 0.0;

		if(upperHalf) {
			first = Math.floor((length - 1.0) / 2.0)+start;
			second = Math.ceil((length - 1.0) / 2.0)+start;
		}
		else {
			first = Math.floor((length - 1.0) / 2.0);
			second = Math.ceil((length - 1.0) / 2.0);
		}

	    if (first == second) {
	       median = distances[(int)first];
	    } else {
	       median = (distances[(int)first] + distances[(int)second]) / 2.0 ;
	    }
	    return median;
	}

	/**
	 * Método que irá detectar usuários com atividades anômalas em um determinado grupo.
	 * @param role Função do grupo de usuários.
	 * @param threshold Limite de normalidade dentro do grupo de usuários.
	 */
	private void findAnomalies(Node role, double threshold) {
		Set<String> employees = role.getChildren().keySet();
		for(String employee : employees) {
			if(employee != null) {
				double dist = calculateDistance(role.getHistogram(), role.getChildren().get(employee).getHistogram());
				if(isDangerous(dist, threshold)) {
					User user = (User) role.getChildren().get(employee).getInfo();
					anomalies.get(user.getRole()).getChildren().put(Double.toString(dist), role.getChildren().get(employee));
				}
			}
		}
	}

	/**
	 * Método que determina se um usuário está fora da média.
	 * @param dist Distância euclidiana entre o usuário e a média de seu grupo.
	 * @param threshold Limite de normalidade dentro do grupo.
	 * @return true se for uma anomalia ou false se não for.
	 */
	private boolean isDangerous(double dist, double threshold) {
		if(dist > threshold) {
			return true;
		}
		return false;
	}

	/**
	 * Método responsável por imprimir a lista de usuários com anomalias em suas atividades
	 */
	private void listAnomalies() {
		Set<String> roles = anomalies.keySet();
		for(String role : roles) {
			System.out.print(role + "'s Threshold: " + thresholds.get(role) + ". ");
			if(!anomalies.get(role).getChildren().isEmpty()) {
				System.out.println("Anomalies: ");
				Set<String> distances = anomalies.get(role).getChildren().keySet();
				for(String dist : distances) {
					User anomaly = (User) anomalies.get(role).getChildren().get(dist).getInfo();
					System.out.print("Employee's ID: " + anomaly.getId() + ". ");
					System.out.println("Distance: " + Double.parseDouble(dist) + ".");
				}
			} else {
				System.out.println("There has been no anomalies found among the " + role + "'s.");
			}
			System.out.println();
		}
	}

}
