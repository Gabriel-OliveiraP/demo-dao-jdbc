package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    // Construtor que recebe a conexão com o banco de dados.
    public DepartmentDaoJDBC(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void insert(Department department) {
        ResultSet rs = null;
        PreparedStatement st = null;
        try {
            // Prepara o comando SQL para inserir um novo departamento.
            st = conn.prepareStatement(
                    "INSERT INTO department (Name) VALUES (?);",
                    Statement.RETURN_GENERATED_KEYS);

            // Define o parâmetro de nome do departamento.
            st.setString(1, department.getName());

            // Executa a inserção e verifica se houve alteração no banco.
            int rowsAffected = st.executeUpdate();
            
            // Se a inserção for bem-sucedida, recupera o ID gerado.
            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    department.setId(id); // Define o ID gerado no objeto departamento.
                }
            } else {
                System.out.println("Unexpected Error!"); // Caso não haja inserção.
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage()); // Lança exceção personalizada.
        } finally {
            // Fecha os recursos (ResultSet e PreparedStatement).
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            // Prepara o comando SQL para atualizar o nome de um departamento.
            st = conn.prepareStatement(
                    "UPDATE department SET Name = ? WHERE Id = ?");

            // Define os parâmetros do nome e ID do departamento.
            st.setString(1, department.getName());
            st.setInt(2, department.getId());

            // Executa a atualização.
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage()); // Lança exceção personalizada.
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletedById(Integer id) {
        ResultSet rs = null;
        PreparedStatement st = null;
        try {
            // Prepara o comando SQL para deletar um departamento pelo ID.
            st = conn.prepareStatement(
                    "DELETE FROM department WHERE Id = ?");
            st.setInt(1, id);

            // Executa a exclusão.
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage()); // Lança exceção personalizada.
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        ResultSet rs = null;
        PreparedStatement st = null;
        try {
            // Prepara o comando SQL para buscar um departamento pelo ID.
            st = conn.prepareStatement(
                    "SELECT * FROM department WHERE Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                // Se encontrar um departamento, cria e retorna um objeto Department.
                return new Department(rs.getInt("Id"), rs.getString("Name"));
            } else {
                return null; // Retorna null se não encontrar.
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage()); // Lança exceção personalizada.
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        ResultSet rs = null;
        PreparedStatement st = null;
        try {
            // Prepara o comando SQL para buscar todos os departamentos.
            st = conn.prepareStatement("SELECT * FROM department ORDER BY Id");

            rs = st.executeQuery();
            List<Department> lista = new ArrayList<>();

            // Preenche a lista com os departamentos encontrados no banco de dados.
            while (rs.next()) {
                lista.add(new Department(rs.getInt(1), rs.getString(2)));
            }
            return lista; // Retorna a lista de departamentos.
        } catch (SQLException e) {
            throw new DbException(e.getMessage()); // Lança exceção personalizada.
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
