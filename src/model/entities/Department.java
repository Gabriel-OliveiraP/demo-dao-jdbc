package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {
    // Serialização para garantir compatibilidade entre diferentes versões do objeto.
    private static final long serialVersionUID = 1L;

    // Atributos privados da classe.
    private Integer id; // Identificador único do departamento.
    private String name; // Nome do departamento.

    // Construtor padrão (sem argumentos).
    public Department() {
    }

    // Construtor que inicializa o `id` e o `name`.
    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Construtor que inicializa apenas o `name`, útil quando o `id` ainda não é conhecido.
    public Department(String name) {
        this.name = name;
    }

    // Método `toString` para representar o objeto como texto.
    // Retorna uma string que descreve o departamento com id e nome.
    @Override
    public String toString() {
        return "Department Id: " + id + " | Name: " + name;
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

    // Sobrescrita do método `hashCode`, que gera um código hash baseado no `id`.
    // Importante para uso em coleções como `HashMap` e `HashSet`.
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Sobrescrita do método `equals` para comparar objetos `Department`.
    // Dois objetos são considerados iguais se seus `id` forem iguais.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) // Verifica se os objetos são exatamente o mesmo.
            return true;
        if (obj == null) // Retorna falso se o objeto comparado for nulo.
            return false;
        if (getClass() != obj.getClass()) // Verifica se os objetos pertencem à mesma classe.
            return false;
        Department other = (Department) obj; // Converte o objeto comparado para `Department`.
        return Objects.equals(id, other.id); // Compara os `id` dos dois objetos.
    }
}
