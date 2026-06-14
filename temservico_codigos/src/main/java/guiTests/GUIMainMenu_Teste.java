package guiTests;

import controller.MainController;
import model.Usuario;

import java.security.NoSuchAlgorithmException;

public class GUIMainMenu_Teste {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MainController main = new MainController();
        main.loginSuccess(new Usuario("12312312311", "admin@admin.com", "admin", "1234"));
    }
}
