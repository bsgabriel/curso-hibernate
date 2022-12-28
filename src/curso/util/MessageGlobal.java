package curso.util;

import javax.swing.*;

public class MessageGlobal {
    private MessageGlobal() {
    }

    public static void showErrorMessage(String message) {
        showErrorMessage(message, null);
    }

    public static void showErrorMessage(String message, Throwable ex) {
        if (message == null || message.isBlank()) {
            message = "Erro ao realizar ação";
        }

        if (ex != null) {
            ex.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }


    public static void showInformationMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean showConfirmationDialog(String message) {
        int opt = JOptionPane.showConfirmDialog(null, message, "Confirmação", JOptionPane.YES_NO_OPTION);

        // 0 indica que clicou em OK
        return opt == 0;
    }
}
