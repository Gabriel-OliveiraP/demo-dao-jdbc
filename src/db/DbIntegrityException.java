package db;

// Exceção personalizada para erros relacionados à integridade do banco de dados,
// como violações de chave estrangeira ou restrições de exclusão.
// Vantagens:
// 1. Facilita o diagnóstico ao diferenciar erros de integridade de outros.
// 2. Centraliza e organiza o tratamento de erros específicos.
// 3. Oferece flexibilidade para expansão futura com funcionalidades adicionais.

public class DbIntegrityException extends RuntimeException {

    // Garante compatibilidade em processos de serialização.
    private static final long serialVersionUID = 1L;

    // Construtor que recebe uma mensagem de erro e a passa para a classe base.
    public DbIntegrityException(String msg) {
        super(msg);
    }
}
