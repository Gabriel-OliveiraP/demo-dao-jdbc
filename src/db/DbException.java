package db;

// A classe DbException é uma exceção personalizada que herda de RuntimeException.
// Ela é projetada para encapsular e representar erros específicos relacionados a operações com o banco de dados.
// Utilizar uma exceção personalizada como esta oferece diversas vantagens:
// 1. **Clareza e Organização:** Permite identificar claramente que o erro é relacionado ao banco de dados,
//    diferenciando de outras exceções genéricas ou não relacionadas.
// 2. **Manutenção Simplificada:** Facilita a manutenção do código, pois todas as exceções relacionadas ao banco
//    podem ser tratadas de forma uniforme.
// 3. **Personalização:** Oferece a possibilidade de incluir mensagens específicas ou funcionalidades adicionais
//    relacionadas a erros de banco de dados no futuro.
// 4. **Evita Uso de Exceções Genéricas:** Promove boas práticas ao evitar exceções genéricas como RuntimeException ou SQLException diretamente no código principal.

public class DbException extends RuntimeException {

    // O atributo serialVersionUID é usado para garantir a compatibilidade durante a serialização da classe.
    // Ele identifica de forma única esta classe em processos de serialização e desserialização.
    // Embora a serialização possa não ser usada diretamente aqui, definir o serialVersionUID é uma boa prática
    // ao trabalhar com classes que herdam de Serializable (como RuntimeException), pois evita warnings e possíveis conflitos.
    private static final long serialVersionUID = 1L;

    // Construtor da classe DbException:
    // - Recebe como parâmetro uma mensagem de erro (String msg).
    // - Essa mensagem será passada para o construtor da classe base (RuntimeException),
    //   permitindo que a exceção carregue informações detalhadas sobre o erro ocorrido.
    public DbException(String msg) {
        super(msg); // Chama o construtor da superclasse RuntimeException com a mensagem fornecida.
    }
}
