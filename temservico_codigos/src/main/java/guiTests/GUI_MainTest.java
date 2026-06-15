package guiTests;

import controller.MainController;

import java.security.NoSuchAlgorithmException;

public class GUI_MainTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MainController main = new MainController();
        main.menuEntrar();
    }
}
