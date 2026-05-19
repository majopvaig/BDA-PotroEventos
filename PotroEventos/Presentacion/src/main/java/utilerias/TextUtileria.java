/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.security.auth.callback.TextOutputCallback;
import javax.swing.JTextField;

/**
 *
 * @author aaron
 */
public class TextUtileria {

    public static JTextField estilizarText(JTextField txt, String texto) {
        String placeHolder = texto;

        txt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt.getText().equals(placeHolder)) {
                    txt.setText("");
                    txt.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt.getText().equals(placeHolder)) {
                    txt.setText(placeHolder);
                    txt.setForeground(Color.GRAY);
                }
            }
        });
        return txt;
    }
}
