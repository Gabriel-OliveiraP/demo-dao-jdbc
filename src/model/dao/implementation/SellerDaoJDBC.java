package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private	Connection conn;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller seller) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					 "INSERT INTO seller"
					+"(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+"VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
					st.setString(1, seller.getName());
					st.setString(2, seller.getEmail());
					st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
					st.setDouble(4, seller.getBaseSalary());
					st.setInt(5, seller.getDepartment().getId());
					
					int rowsAffected = st.executeUpdate();
					
					if(rowsAffected > 0) {
						ResultSet rs = st.getGeneratedKeys();
						if(rs.next()) {
							int id = rs.getInt(1);
							seller.setId(id);
							DB.closeResultSet(rs);
						}
					}
					else {
						throw new DbException("Unexpected error! No rows affected!");
					}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally{
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller "
					+"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+"WHERE Id = ?");
					st.setString(1, seller.getName());
					st.setString(2, seller.getEmail());
					st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
					st.setDouble(4, seller.getBaseSalary());
					st.setInt(5, seller.getDepartment().getId());
					
					st.setInt(6, seller.getId());
					
					st.executeUpdate();
					
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally{
			DB.closeStatement(st);
		}
	}

	@Override
	public void deletedById(Integer id) {
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName\r\n"
					+ "FROM seller INNER JOIN department\r\n"
					+ "ON seller.DepartmentId = department.Id\r\n"
					+ "WHERE seller.id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			// acha o vendedor pelo Id
			if(rs.next()) {//se tiver achado ele entra no if
				
				Department dep = instantiateDepartment(rs);
				//com o Department criado vamos criar um Seller apontando para o Department
				Seller obj = instantiateSeller(rs, dep);
			
				return obj;
			}
			return null;
			
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}finally {
				DB.closeResultSet(rs);
				DB.closeStatement(st);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		/*nesse método a gente propaga a exceção sem trata-la, pois o metodo que implementar
		esse outro método, ele sim irá tratar*/
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		/* vai setar cada variavel do Seller passando do rs a coluna que o mesmo "pegou"
		 e no final setar o departament apontando para o departament(dep) criado anteriormente*/
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		/*nesse método a gente propaga a exceção sem trata-la, pois o metodo que implementar
		esse outro método, ele sim irá tratar*/
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		// vai setar o Id pegando do resultSet o Int da coluna de nome "departmentId"
		dep.setName(rs.getString("DepName"));
		// vai setar o Id pegando do resultSet o String da coluna de nome "DepName"
		
		return dep;
	}

	@Override
	public List<Seller> findAll() {
			PreparedStatement st = null;
			ResultSet rs = null;
			
			try {
				st = conn.prepareStatement(
						"SELECT seller.*,department.Name as DepName "
						+ "FROM seller INNER JOIN department "
						+ "ON seller.DepartmentId = department.Id "
						+ "ORDER BY Name");
				
				rs = st.executeQuery();
				
				List<Seller> list = new ArrayList<>();
				Map<Integer, Department> map = new HashMap<>();
				/*vai consultar a chave integer pelo DepartmentId,
				 se já existir vai usar a existente, se não,
				 irá criar um novo departamento*/
				while(rs.next()) {
					Department dep = map.get(rs.getInt("DepartmentId"));
					
					if(dep == null) {//testando se esse departamento já foi criado
						dep = instantiateDepartment(rs);
						map.put(rs.getInt("DepartmentId"), dep);
					}
					Seller obj = instantiateSeller(rs, dep);
					list.add(obj);
				}
				return list;
						
			}catch(SQLException e ) {
				throw new DbException(e.getMessage());
			}finally {
				DB.closeResultSet(rs);
				DB.closeStatement(st);
			}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			//criando um map com a chave integer e o valor Department
			
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				/*vai consultar a chave integer pelo DepartmentId,
				 se já existir vai usar a existente, se não,
				 irá criar um novo departamento*/
				
				if(dep == null) {//testando se esse departamento já foi criado
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
					
		}catch(SQLException e ) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

}
