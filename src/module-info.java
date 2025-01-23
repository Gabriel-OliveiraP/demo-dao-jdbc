/**
 * Declaração do módulo `demoDaoJdbc`.
 * Este arquivo é usado para definir os requisitos e as dependências necessárias 
 * para o funcionamento do módulo no sistema modular introduzido no Java 9.
 */
module demoDaoJdbc {
    // Declara que o módulo `demoDaoJdbc` requer o módulo `java.sql`.
    // Isso é necessário para usar as funcionalidades relacionadas ao JDBC (Java Database Connectivity),
    // que estão contidas no pacote `java.sql`.
    requires java.sql;
}
