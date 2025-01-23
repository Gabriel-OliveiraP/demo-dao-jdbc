package aplicacao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dominio.Pessoa;

public class Program {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		//pegamos o persistenceUnit que colocamos no file persistence
		EntityManager em = emf.createEntityManager();

		Pessoa p = em.find(Pessoa.class, 2);
		//fazendo pesquisa no banco de dados
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();
		
		System.out.println("Pronto!");
		
		em.close();
		emf.close();
	}

}
