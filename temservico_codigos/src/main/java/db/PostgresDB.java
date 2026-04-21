package db;

import org.postgresql.util.PSQLException;
import sec.SHA256Hasher;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;

public class PostgresDB {
    static String JDBC_URL = "jdbc:postgresql://localhost:5432/TODO?currentSchema=public&user=postgres&password=123456";

    // quais colunas tem na tabela de users: serve para analisar de qual tipo é a entrada do login (CPF, nome ou email)
    public enum UsersCols {
        CPF, NAME, EMAIL, PASSWORDHASH
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {

        // inicializa a tabela de users e coloca um dado de admin para iniciar
        if(!doesTableExist("users")){
            createUsersTable();
            insertUsersData("11111111111", "admin", "admin@admin.com", SHA256Hasher.hashString("1234"));
        }
        //userTest();
        System.out.println(getPasswordHash("admin@admin.com", UsersCols.EMAIL));

    }

    // função de teste: insere user com dados inseridos pelo terminal: irá servir para fazer cadastro
    private static void userTest() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL);
        System.out.print("Quer adicionar um dado de user na tabela? [Y/N]");
        try(Scanner scanner = new Scanner(System.in)){
            if(scanner.nextLine().equalsIgnoreCase("Y")){
                System.out.print("CPF: ");
                String CPF = scanner.nextLine();
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("E-mail: ");
                String email = scanner.nextLine();
                System.out.print("Senha: ");
                String hashSenha = SHA256Hasher.hashString(scanner.nextLine());
                insertUsersData(CPF, nome, email, hashSenha);
                System.out.println("Insert bem sucedido!");
            }
        } catch (NoSuchAlgorithmException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // cria tabela de users
    private static void createUsersTable() throws SQLException{
        Connection connection = DriverManager.getConnection(JDBC_URL);
        Statement statement = connection.createStatement();
        String createTableQuery = """
                CREATE TABLE users (
                id SERIAL PRIMARY KEY,
                CPF VARCHAR(11) UNIQUE NOT NULL,
                Name VARCHAR(255) NOT NULL,
                Email VARCHAR(255) UNIQUE NOT NULL,
                PasswordHash VARCHAR(255) NOT NULL)
                """;
        statement.execute(createTableQuery);
    }

    // insere dados da tabela de users
    public static void insertUsersData(String CPF, String name, String email, String passwordHash) throws SQLException{
        Connection connection = DriverManager.getConnection(JDBC_URL);
        String insertQuery = "INSERT INTO users (CPF, Name, Email, PasswordHash) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertQuery);
        statement.setString(1, CPF);
        statement.setString(2, name);
        statement.setString(3, email);
        statement.setString(4, passwordHash);
        statement.execute();
    }

    // retorna hash da senha do parametro, depende do tipo do parametro
    public static String getPasswordHash(String param, UsersCols col) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL);
        String selectQuery;
        switch (col){
            case CPF -> selectQuery = "SELECT PasswordHash FROM users WHERE CPF = ?";
            case NAME -> selectQuery = "SELECT PasswordHash FROM users WHERE Name = ?";
            case EMAIL -> selectQuery = "SELECT PasswordHash FROM users WHERE Email = ?";
            default -> {
                return "";
            }
        }

        PreparedStatement statement = connection.prepareStatement(selectQuery);
        statement.setString(1, param);
        ResultSet rs = statement.executeQuery();
        rs.next();
        try{
            return rs.getString("PasswordHash");
        }
        catch (PSQLException e){ // se nao tiver user, da erro
            return "";
        }
    }

    // verifica se tabela existe
    public static boolean doesTableExist(String tableName) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL);
        DatabaseMetaData meta = connection.getMetaData();
        try (ResultSet rs = meta.getTables(null, null, tableName, new String[] {"TABLE"})) {
            return rs.next();
        }
    }
}
