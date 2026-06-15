package guiTests;

import controller.MainController;
import model.Usuario;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class GUIMainMenu_Teste {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MainController main = new MainController();
        Usuario user = new Usuario("12312312311", "lucas@lucas.com", "lucas", "123");
        user.addDatasIndisponiveis(LocalDate.now());
        main.addUsuariosGerais(user);
        main.loginSuccess(user);
    }
}
