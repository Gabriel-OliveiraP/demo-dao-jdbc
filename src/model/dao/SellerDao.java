package model.dao;

import java.util.List;
import model.entities.Department;
import model.entities.Seller;

// Interface para operações de acesso a dados da entidade Seller.
// Vantagem: Define um contrato para diferentes implementações, promovendo flexibilidade.

public interface SellerDao {

    void insert(Seller obj);                   // Insere um novo vendedor.
    void update(Seller obj);                   // Atualiza um vendedor existente.
    void deleteById(Integer id);               // Remove um vendedor pelo ID.
    Seller findById(Integer id);               // Busca um vendedor pelo ID.
    List<Seller> findAll();                    // Retorna todos os vendedores.
    List<Seller> findByDepartment(Department department); // Retorna vendedores de um departamento específico.
}

