package model.dao;

import db.DB;
import model.dao.implementation.DepartmentDaoJDBC;
import model.dao.implementation.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	/*
	 * Serve para instanciar um SellerDaoJDBC sem expor a implementação
	 * ao invez de fazer no Program o new SellerDaoJDBC a gente implementa
	 * isso no método do DaoFactory assim assim usando DaoFactory.createSellerDao
	 */
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
