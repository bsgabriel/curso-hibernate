package curso.util;

import javax.swing.*;
import java.awt.*;

public class ComponentFactory {
    public static JPanel createFieldWithLabel(String labelText, JTextField field) {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        pnl.add(label);
        pnl.add(Box.createRigidArea(new Dimension(0, 10)));
        pnl.add(field);
        return pnl;
    }
}
