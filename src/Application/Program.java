package Application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.implementation.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("====== TEST 1: seller findById ======");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("====== TEST 2: seller findByDepartment ======");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);	
		for (Seller obj : list) {
			System.out.println(obj);
		}
		System.out.println("====== TEST 3: seller findAll ======");
		List<Seller> listAll = sellerDao.findAll();	
		for (Seller obj : listAll) {
			System.out.println(obj);
		}
		
		System.out.println("====== TEST 5: seller InsertSeller ======");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());
		 
		
		System.out.println("====== TEST 5: seller SellerUpdate ======");
		seller = sellerDao.findById(1);
		seller.setName("Martha Waine");
		sellerDao.update(seller);
		System.out.println("Update Completed");
		
		System.out.println("====== TEST 6: seller Delete ======");
		System.out.print("Enter id for delete test: ");
		String lista = input.nextLine();
		String[] id = lista.split(",");
		for(int i = 0; i < id.length; i++) {
		sellerDao.deleteById(Integer.parseInt(id[i]));
		}
		System.out.println("Deleted!");
	}
}
