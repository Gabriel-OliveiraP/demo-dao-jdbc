package Application;

import java.util.List;
import java.util.Scanner;

import db.DbException;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		DepartmentDao dep = DaoFactory.createDepartmentDao();
		//criando pelo daoFactory, e como é estático não 
		//precisamos instanciar o DaoFactory
		
		System.out.println("Test 1 Insert:");
		try {
			System.out.print("New Department Name: ");
			Department department = new Department(input.nextLine());
			dep.insert(department);
			System.out.println("Done!");
		}catch(DbException e) {
			e.getMessage();
		}
		
		System.out.println("Test 2 findById:");
		System.out.print("Id number:");
		System.out.println(dep.findById(input.nextInt()));
		
		
		System.out.println("Test 3 deleteById:");
		System.err.println("Insert Id Department for delete:");
		dep.deletedById(input.nextInt());
		
		System.out.println("Test 4 Update:");
		Department update = new Department();
		System.out.print("Enter Id to update:");
		update.setId(input.nextInt());
		input.nextLine();
		System.out.print("Insert new Department Name:");
		update.setName(input.nextLine());
		dep.update(update);
		System.out.println(dep.findById(update.getId()));
		
		System.out.println("Test 5 findAll:");
		List<Department> list = dep.findAll();
		for(Department i : list) {
			System.out.println(i);
			}
		

		
	}
}
