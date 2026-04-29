package controller;

import db.PostgresDB;
import sec.SHA256Hasher;
import java.sql.SQLException;

public class UserController {
    public void signUser(String cpf, String nome, String email, String senha) throws Exception {
        // Validação RN01: Mínimo de 8 caracteres
        if (senha == null || senha.length() < 8) {
            throw new Exception("Erro: A senha deve ter no mínimo 8 caracteres.");
        }

        String hashSenha = SHA256Hasher.hashString(senha);

        try {
            PostgresDB.insertUsersData(cpf, nome, email, hashSenha);
        } catch (SQLException e) {
            if (e.getMessage().contains("users_cpf_key")) {
                throw new Exception("Erro: Este CPF já possui uma conta cadastrada.");
            }
            throw e;
        }
    }

    public void editUser(String cpf, String nome, String email) throws SQLException {
        PostgresDB.updateUserData(cpf, nome, email);
    }

    public void removeUser(String cpf) throws SQLException {
        PostgresDB.deleteUser(cpf);
    }
}
