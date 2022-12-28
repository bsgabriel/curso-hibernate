package curso;

import curso.view.controller.MenuPrincipalController;

public class Launcher {
    public static void main(String[] args) {
        MenuPrincipalController menu = new MenuPrincipalController();
        menu.setVisible(true);
    }
}
