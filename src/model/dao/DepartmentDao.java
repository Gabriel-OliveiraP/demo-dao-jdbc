package model.dao;

import java.util.List;
import model.entities.Department;

// Interface que define operações de acesso a dados para a entidade Department.
// Vantagem: Garante flexibilidade para diferentes implementações.

public interface DepartmentDao {
    
    // Insere um novo departamento.
    void insert(Department obj);

    // Atualiza um departamento existente.
    void update(Department obj);

    // Remove um departamento pelo ID.
    void deletedById(Integer id);

    // Busca um departamento pelo ID.
    Department findById(Integer id);

    // Retorna todos os departamentos.
    List<Department> findAll();
}
