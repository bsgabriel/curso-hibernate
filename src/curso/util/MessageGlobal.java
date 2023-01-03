package curso.util;

import javax.swing.*;

import static curso.util.StringUtils.isBlank;

public class MessageGlobal {
    private MessageGlobal() {
    }

    public static void showErrorMessage(String message) {
        showErrorMessage(message, null);
    }

    public static void showErrorMessage(String message, Throwable ex) {
        if (isBlank(message)) {
            message = "Erro ao realizar ação";
        }

        if (ex != null) {
            ex.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }


    public static void showInformationMessage(String message) {
        showInformationMessage("Info", message);
    }

    public static void showInformationMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean showConfirmationDialog(String message) {
        int opt = JOptionPane.showConfirmDialog(null, message, "Confirmação", JOptionPane.YES_NO_OPTION);

        // 0 indica que clicou em OK
        return opt == 0;
    }
}
