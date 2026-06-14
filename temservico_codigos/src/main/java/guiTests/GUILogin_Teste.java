package guiTests;

import controller.ControllerLogin;
import controller.MainController;
import model.Usuario;

import javax.naming.ldap.Control;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class GUILogin_Teste {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Usuario user1 = new Usuario("12631096916", "souzaalves.lucas.2017@gmail.com", "Lucas", "banana");
        Usuario user2 = new Usuario("11111111111", "admin@admin.com", "admin", "1234");
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(user1);
        usuarios.add(user2);
        MainController mainController = new MainController();
        mainController.setUsuariosGerais(usuarios);
        mainController.uc02_login();
    }

}
