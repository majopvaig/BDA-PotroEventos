package utilerias;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BotonUtileria {

    private BotonUtileria() {
    }

    //Para JButton
    public static void estilizarBoton(JButton boton) {

        Color normal = new Color(44, 114, 243);
        Color hover = new Color(0, 49, 141);

        boton.setBackground(normal);
        boton.setForeground(Color.WHITE);

        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(normal);
            }
        });

        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    //Para JPanel
    public static void estilizarBoton(JPanel panel) {

        Color normal = new Color(44, 114, 243);
        Color hover = new Color(0, 49, 141);

        panel.setOpaque(true);
        panel.setBackground(normal);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(normal);
            }
        });

        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    
    // Perdon profe pero era esto o echarme lo que ya funcionaba de lo otro
    public static void btnFacturar(JButton boton) {

        Color normal = new Color(44, 114, 243);
        Color hover = new Color(0, 49, 141);

        boton.setOpaque(true);
        boton.setBackground(normal);

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(normal);
            }
        });

        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
