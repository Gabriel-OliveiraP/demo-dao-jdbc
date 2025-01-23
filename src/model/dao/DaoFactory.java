package model.dao;

import db.DB;
import model.dao.implementation.DepartmentDaoJDBC;
import model.dao.implementation.SellerDaoJDBC;

public class DaoFactory {

    // Método para criar uma instância de SellerDao.
    // Retorna uma implementação de SellerDao (SellerDaoJDBC), injetando a conexão do banco.
    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    /*
     * O método acima encapsula a criação de SellerDaoJDBC, escondendo os detalhes
     * de implementação. Isso evita que o código principal (Program) precise conhecer
     * a classe SellerDaoJDBC diretamente, promovendo o uso do método DaoFactory.createSellerDao.
     * Vantagem: Reduz o acoplamento, facilitando a manutenção e possíveis mudanças na implementação.
     */

    // Método para criar uma instância de DepartmentDao.
    // Retorna uma implementação de DepartmentDao (DepartmentDaoJDBC), injetando a conexão do banco.
    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }

    /*
     * Semelhante ao método anterior, este encapsula a criação de DepartmentDaoJDBC.
     * Vantagem: Centraliza a lógica de criação de DAOs, permitindo alterações fáceis
     * na implementação sem impacto no restante do sistema.
     */
}
