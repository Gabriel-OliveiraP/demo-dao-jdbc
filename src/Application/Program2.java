package Application;

import java.nio.file.spi.FileSystemProvider;
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
		/**
		System.out.println("Test 1 Insert:");
		try {
			System.out.print("New Department Name: ");
			Department department = new Department(input.nextLine());
			
			dep.insert(department);
			System.out.println("Done!");
		}catch(DbException e) {
			e.getMessage();
		}*/
		
		/*
		System.out.println("Test 2 findById:");
		System.out.print("Id number:");
		System.out.println(dep.findById(input.nextInt()));
		*/
		
		System.out.println("Test 3 deleteById:");
		System.err.println("Insert Id Department for delete:");
		dep.deletedById(input.nextInt());
		
	}
}
