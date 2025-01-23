package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {
    private static final long serialVersionUID = 1L;

    // Atributos privados da classe.
    private Integer id;          // Identificador único do vendedor.
    private String name;         // Nome do vendedor.
    private String email;        // Email do vendedor.
    private Date birthDate;      // Data de nascimento do vendedor.
    private Double baseSalary;   // Salário base do vendedor.
    private Department department; // Departamento associado ao vendedor.

    // Construtor padrão (sem argumentos).
    public Seller() {
    }

    // Construtor que inicializa todos os atributos da classe.
    public Seller(Integer id, String name, String email, Date birthDate, Double baseSalary, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.department = department;
    }

    // Getter e setter para o atributo `id`.
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter e setter para o atributo `name`.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter e setter para o atributo `email`.
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter e setter para o atributo `birthDate`.
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    // Getter e setter para o atributo `baseSalary`.
    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    // Getter e setter para o atributo `department`.
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    // Sobrescrita do método `hashCode`, gera um código hash baseado no `id`.
    // Importante para coleções baseadas em hash, como `HashMap` e `HashSet`.
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Sobrescrita do método `equals` para comparar objetos `Seller`.
    // Dois objetos são considerados iguais se tiverem o mesmo `id`.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) // Verifica se os objetos são exatamente o mesmo.
            return true;
        if (obj == null) // Retorna falso se o objeto comparado for nulo.
            return false;
        if (getClass() != obj.getClass()) // Verifica se os objetos pertencem à mesma classe.
            return false;
        Seller other = (Seller) obj; // Converte o objeto comparado para `Seller`.
        return Objects.equals(id, other.id); // Compara os `id` dos dois objetos.
    }

    // Sobrescrita do método `toString` para representar o objeto como texto.
    // Retorna uma string com todos os atributos relevantes do vendedor.
    @Override
    public String toString() {
        return "Seller id: " + id + 
               "| name: " + name + 
               "| email: " + email + 
               "| birthDate: " + birthDate + 
               "| baseSalary: " + baseSalary + 
               "| department: " + department.getName();
    }
}
