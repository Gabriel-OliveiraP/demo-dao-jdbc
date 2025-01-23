package db;

// Importações necessárias para manipulação de conexões JDBC, leitura de arquivos, e gerenciamento de exceções.
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

    // Variável estática para armazenar a conexão única do banco de dados.
    private static Connection conn = null;

    // Método público para obter a conexão com o banco de dados.
    public static Connection getConnection() {
        // Verifica se a conexão ainda não foi estabelecida.
        if (conn == null) {
            try {
                // Carrega as propriedades de configuração do arquivo db.properties.
                Properties props = loadProperties();
                // Obtém a URL do banco de dados a partir das propriedades.
                String url = props.getProperty("dburl");
                // Estabelece a conexão com o banco de dados usando as propriedades.
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                // Lança uma exceção personalizada em caso de erro na conexão.
                throw new DbException(e.getMessage());
            }
        }
        // Retorna a conexão já criada ou recém-estabelecida.
        return conn;
    }

    // Método público para fechar a conexão com o banco de dados.
    public static void closeConnection() {
        if (conn != null) {
            try {
                // Fecha a conexão com o banco de dados.
                conn.close();
            } catch (SQLException e) {
                // Lança uma exceção personalizada em caso de erro ao fechar a conexão.
                throw new DbException(e.getMessage());
            }
        }
    }

    // Método privado para carregar as propriedades de configuração do banco de dados.
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            // Cria um objeto Properties e carrega os dados do arquivo db.properties.
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            // Lança uma exceção personalizada em caso de erro na leitura do arquivo.
            throw new DbException(e.getMessage());
        }
    }

    // Método público para fechar um objeto Statement.
    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                // Fecha o Statement se ele não for nulo.
                st.close();
            } catch (SQLException e) {
                // Lança uma exceção personalizada em caso de erro ao fechar o Statement.
                throw new DbException(e.getMessage());
            }
        }
    }

    // Método público para fechar um objeto ResultSet.
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                // Fecha o ResultSet se ele não for nulo.
                rs.close();
            } catch (SQLException e) {
                // Lança uma exceção personalizada em caso de erro ao fechar o ResultSet.
                throw new DbException(e.getMessage());
            }
        }
    }
}
