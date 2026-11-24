package com.mycompany.space_invaders;

import UI.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Space_Invaders {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}