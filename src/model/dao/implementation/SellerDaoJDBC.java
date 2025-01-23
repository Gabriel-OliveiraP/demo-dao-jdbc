package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
    
    private Connection conn; // Conexão com o banco de dados
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // Formato de data

    // Construtor para inicializar a conexão
    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement st = null;
        try {
            // Comando SQL para inserir um vendedor
            st = conn.prepareStatement(
                "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            
            int rowsAffected = st.executeUpdate(); // Executa o comando de inserção

            if (rowsAffected > 0) {
                // Se a inserção for bem-sucedida, recupera o ID gerado
                ResultSet rs = st.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st); // Fechamento do Statement
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;
        try {
            // Comando SQL para atualizar um vendedor
            st = conn.prepareStatement(
                "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE Id = ?");
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            st.setInt(6, seller.getId());
            
            st.executeUpdate(); // Executa o comando de atualização
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st); // Fechamento do Statement
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            // Comando SQL para deletar um vendedor
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
            st.setInt(1, id);
            st.executeUpdate(); // Executa o comando de exclusão
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st); // Fechamento do Statement
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            // Comando SQL para buscar um vendedor por ID com informações do departamento
            st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id WHERE seller.id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Department dep = instantiateDepartment(rs); // Instancia o departamento
                Seller obj = instantiateSeller(rs, dep); // Instancia o vendedor com o departamento
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs); // Fechamento do ResultSet
            DB.closeStatement(st); // Fechamento do Statement
        }
    }

    // Método auxiliar para instanciar um vendedor a partir de um ResultSet
    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(dep); // Associa o departamento ao vendedor
        return obj;
    }

    // Método auxiliar para instanciar um departamento a partir de um ResultSet
    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            // Comando SQL para buscar todos os vendedores com informações do departamento
            st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id ORDER BY Name");
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                
                if (dep == null) {
                    dep = instantiateDepartment(rs); // Cria o departamento se ainda não existir
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instantiateSeller(rs, dep); // Cria o vendedor
                list.add(obj);
            }
            return list; // Retorna a lista de vendedores
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs); // Fechamento do ResultSet
            DB.closeStatement(st); // Fechamento do Statement
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            // Comando SQL para buscar vendedores de um departamento específico
            st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id WHERE DepartmentId = ? ORDER BY Name");
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs); // Cria o departamento se ainda não existir
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instantiateSeller(rs, dep); // Cria o vendedor
                list.add(obj);
            }
            return list; // Retorna a lista de vendedores por departamento
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs); // Fechamento do ResultSet
            DB.closeStatement(st); // Fechamento do Statement
        }
    }
}
