package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection connection) {
		this.conn = connection;
	}
	
	@Override
	public void insert(Department department) {
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO department "
					+"(Name) "
					+ "VALUES (?);",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, department.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				while(rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
				}
			}
			else {
				System.out.println("Unexpected Error!");
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Department obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletedById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Department findById(Integer id) {
		ResultSet rs = null;
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                      "SELECT * FROM department "
                    + "WHERE Id = ? ");

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()) {
            //primeiro movemos o rs para a primeira linha e conferimos se achou um produto
            // para depois "separa-lo"
            Department departmentFound = new Department(rs.getInt("Id"), rs.getString("Name"));
            return departmentFound;
            }
            else {
                return null;
            }
        }catch(SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
