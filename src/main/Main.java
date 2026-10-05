package main;

import controlador.ControladorEntregas;
import controlador.ControladorPedidos;
import controlador.ControladorRepartidores;
import vista.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ControladorPedidos controlador = new ControladorPedidos();
            ControladorRepartidores controladorRepartidores = new ControladorRepartidores();
            ControladorEntregas controladorEntregas = new ControladorEntregas();
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(controlador, controladorRepartidores, controladorEntregas);
            ventanaPrincipal.setVisible(true);
        });
    }
}
