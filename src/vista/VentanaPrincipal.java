package vista;

import controlador.ControladorEntregas;
import controlador.ControladorPedidos;
import controlador.ControladorRepartidores;
import modelo.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ventana principal del sistema SpeedFast.
 * Permite gestionar pedidos, repartidores y entregas mediante Swing.
 */
public class VentanaPrincipal extends JFrame {
    private JButton btnRegistrarPedido;
    private JPanel ventanaPrincipal;
    private JButton btnCerrar;
    private JTabbedPane tbpVentanas;
    private JTextField txtDireccion;
    private JTextField txtNombreRepartidor;
    private JButton btnAgregarRepartidor;
    private JLabel lblDireccion;
    private JLabel lblTipoPedido;
    private JLabel lblEstado;
    private JLabel lblNombreRepartidor;
    private JComboBox cmbTipoPedido;
    private JComboBox cmbEstadoPedido;
    private JTable tblPedidos;
    private JTable tblRepartidores;
    private JComboBox cmbPedidoEntrega;
    private JComboBox cmbRepartidorEntrega;
    private JButton btnRegistrarEntrega;
    private JTable tblEntregas;
    private JLabel lblSeleccionarPedido;
    private JLabel lblSeleccionarRepartidor;
    private JButton btnEliminarPedido;
    private JButton btnEditarPedido;
    private JButton btnEliminarRepartidor;
    private JButton btnEditarRepartidor;
    private JButton btnEliminarEntrega;
    private JButton btnEditarEntrega;
    private JComboBox cmbFiltroTipo;
    private JLabel lblFiltroPorTipo;

    private ControladorPedidos controladorPedidos;
    private ControladorRepartidores controladorRepartidores;
    private ControladorEntregas controladorEntregas;

    private DefaultTableModel modeloTabla;
    private DefaultTableModel modeloTablaRepartidores;
    private DefaultTableModel modeloTablaEntregas;

    private int idRepartidorSeleccionado = -1;
    private int idPedidoSeleccionado = -1;
    private int idEntregaSeleccionada = -1;

    public VentanaPrincipal(ControladorPedidos controladorPedidos, ControladorRepartidores controladorRepartidores, ControladorEntregas controladorEntregas) {
        this.controladorPedidos = controladorPedidos;
        this.controladorRepartidores = controladorRepartidores;
        this.controladorEntregas = controladorEntregas;
        configurarVentana();
        configurarComponentes();
        configurarEventos();
        inicializarTablaPedidos();
        actualizarTablaPedidos();
        inicializarTablaRepartidores();
        inicializarTablaEntregas();
    }

    /**
     * Configura las propiedades principales de la ventana.
     */
    private void configurarVentana() {
        setTitle("Sistema Speed Fast");
        setContentPane(ventanaPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Carga en los combos de pedidos las opciones de tipo y estado disponibles.
     */
    private void configurarComponentes(){
        for (EstadoPedido estado : EstadoPedido.values()){
            cmbEstadoPedido.addItem(estado.toString());
        }

        for (TipoPedido tipo : TipoPedido.values()){
            cmbTipoPedido.addItem(tipo.toString());
        }

        cmbFiltroTipo.addItem("TODOS");

        for (TipoPedido tipo : TipoPedido.values()) {
            cmbFiltroTipo.addItem(tipo.toString());
        }
    }

    /**
     * Asocia las acciones de los botones y pestañas con sus métodos correspondientes.
     */
    private void configurarEventos() {
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnRegistrarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPedido();
            }
        });

        btnEditarPedido.addActionListener(e -> editarPedido());

        btnEliminarPedido.addActionListener(e -> eliminarPedido());

        btnAgregarRepartidor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarRepartidor();
            }
        });

        btnEditarRepartidor.addActionListener(e -> {
            editarRepartidor();
        });

        btnEliminarRepartidor.addActionListener(e -> {
            eliminarRepartidor();
        });

        btnRegistrarEntrega.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarEntrega();
            }
        });

        btnEditarEntrega.addActionListener(e -> editarEntrega());

        btnEliminarEntrega.addActionListener(e -> eliminarEntrega());

        tbpVentanas.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tbpVentanas.getSelectedIndex();
                String ventana = tbpVentanas.getTitleAt(selectedIndex);
                if (ventana.equalsIgnoreCase("repartidores")) {
                    actualizarTablaRepartidores();
                }
                if (ventana.equalsIgnoreCase("entregas")) {
                    actualizarEntregas();
                }
            }
        });

        cmbFiltroTipo.addActionListener(e -> filtrarPedidosPorTipo());
    }

    /**
     * Valida y registra un pedido desde los controles de la interfaz.
     */
    private void agregarPedido(){
        if (cmbTipoPedido.getSelectedItem() == null
                || cmbEstadoPedido.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione el tipo y el estado del pedido.");
            return;
        }
        String direccion = txtDireccion.getText().trim();
        String tipoPedido = cmbTipoPedido.getSelectedItem().toString();
        String estado = cmbEstadoPedido.getSelectedItem().toString();
        boolean resultado = controladorPedidos.registrarPedido(direccion, tipoPedido, estado);
        if (resultado){
            JOptionPane.showMessageDialog(
                    this,
                    "Pedido agregado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );
            actualizarTablaPedidos();
            limpiarCamposPedido();
        }else{
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el pedido. Verifica los datos y la conexión.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Actualiza el pedido seleccionado con los datos ingresados en el formulario.
     */
    private void editarPedido() {
        if (idPedidoSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla antes de actualizar.");
            return;
        }

        String direccion = txtDireccion.getText().trim();

        if (cmbTipoPedido.getSelectedItem() == null
                || cmbEstadoPedido.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione el tipo y el estado del pedido.");
            return;
        }

        boolean resultado = controladorPedidos.actualizar(
                idPedidoSeleccionado,
                direccion,
                cmbTipoPedido.getSelectedItem().toString(),
                cmbEstadoPedido.getSelectedItem().toString()
        );

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Pedido actualizado correctamente.");
            actualizarTablaPedidos();
            limpiarCamposPedido();
        } else {
            JOptionPane.showMessageDialog(this, "No fue posible actualizar el pedido.");
        }
    }

    /**
     * Solicita confirmación y elimina el pedido seleccionado.
     */
    private void eliminarPedido() {
        if (idPedidoSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla antes de eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar el pedido seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean resultado = controladorPedidos.eliminar(idPedidoSeleccionado);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Pedido eliminado correctamente.");
            actualizarTablaPedidos();
            limpiarCamposPedido();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible eliminar el pedido. Compruebe si tiene una entrega asociada."
            );
        }
    }

    /**
     * Limpia el formulario de pedidos y deselecciona la fila actual.
     */
    private void limpiarCamposPedido() {
        txtDireccion.setText("");

        if (cmbTipoPedido.getItemCount() > 0) {
            cmbTipoPedido.setSelectedIndex(0);
        }

        if (cmbEstadoPedido.getItemCount() > 0) {
            cmbEstadoPedido.setSelectedIndex(0);
        }
        idPedidoSeleccionado = -1;
        tblPedidos.clearSelection();
    }

    /**
     * Registra un repartidor usando el nombre ingresado en la interfaz.
     */
    private void agregarRepartidor() {

        String nombre = txtNombreRepartidor.getText().trim();

        boolean resultado = controladorRepartidores.registrarRepartidor(nombre);
        if (resultado) {
            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor agregado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );
            actualizarTablaRepartidores();
            limpiarCamposRepartidor();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se agrego el repartidor.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE

            );
        }

    }

    /**
     * Actualiza el nombre del repartidor seleccionado.
     */
    private void editarRepartidor(){
        if (idRepartidorSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un repartidor de la tabla antes de actualizar");
            return;
        }
        boolean resultado = controladorRepartidores.actualizar(idRepartidorSeleccionado, txtNombreRepartidor.getText());
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Repartidor actualizado correctamente.");
            actualizarTablaRepartidores();
            limpiarCamposRepartidor();
        }else{
            JOptionPane.showMessageDialog(this, "No fue posible actualizar el repartidor.");
        }
    }

    /**
     * Solicita confirmación y elimina el repartidor seleccionado.
     */
    private void eliminarRepartidor(){
        if (idRepartidorSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un repartidor de la tabla antes de eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar el repartidor seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean resultado = controladorRepartidores.eliminar(idRepartidorSeleccionado);
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Repartidor eliminado correctamente.");
            actualizarTablaRepartidores();
            limpiarCamposRepartidor();
        }else{
            JOptionPane.showMessageDialog(this, "No fue posible eliminar el repartidor. Compruebe si tiene una entrega asociada.");
        }
    }


    /**
     * Registra una entrega con el pedido y el repartidor seleccionados.
     */
    private void registrarEntrega() {
        Pedido pedido = (Pedido) cmbPedidoEntrega.getSelectedItem();
        Repartidor repartidor = (Repartidor) cmbRepartidorEntrega.getSelectedItem();
        if (pedido == null || repartidor == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido y un repartidor.");
            return;
        }

        if (controladorEntregas.registrarEntrega(pedido.getId(), repartidor.getId())) {
            actualizarEntregas();
            limpiarCamposEntrega();
            actualizarTablaPedidos();
            JOptionPane.showMessageDialog(this, "Entrega registrada correctamente. Se actualizo el estado del pedido a ENTREGADO.");
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar la entrega. Verifica los datos y la conexión."
            );
        }
    }

    /**
     * Actualiza el pedido y el repartidor asociados a la entrega seleccionada.
     */
    private void editarEntrega() {
        int fila = tblEntregas.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una entrega de la tabla antes de actualizar.");
            return;
        }

        Pedido pedido = (Pedido) cmbPedidoEntrega.getSelectedItem();
        Repartidor repartidor = (Repartidor) cmbRepartidorEntrega.getSelectedItem();

        if (pedido == null || repartidor == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido y un repartidor.");
            return;
        }

        int idEntrega = Integer.parseInt(
                modeloTablaEntregas.getValueAt(fila, 0).toString()
        );

        boolean resultado = controladorEntregas.actualizar(
                idEntrega,
                pedido.getId(),
                repartidor.getId()
        );

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Entrega actualizada correctamente.");
            actualizarEntregas();
            limpiarCamposEntrega();
        } else {
            JOptionPane.showMessageDialog(this, "No fue posible actualizar la entrega.");
        }

    }

    /**
     * Solicita confirmación y elimina la entrega seleccionada.
     */
    private void eliminarEntrega() {
        int fila = tblEntregas.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una entrega de la tabla antes de eliminar.");
            return;
        }

        int idEntrega = Integer.parseInt(
                modeloTablaEntregas.getValueAt(fila, 0).toString()
        );

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar la entrega seleccionada?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean resultado = controladorEntregas.eliminar(idEntrega);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Entrega eliminada correctamente.");
            actualizarEntregas();
            limpiarCamposEntrega();
        } else {
            JOptionPane.showMessageDialog(this, "No fue posible eliminar la entrega.");
        }
    }

    /**
     * Configura la tabla de pedidos y el evento para cargar un pedido seleccionado.
     */
    private void inicializarTablaPedidos(){
        String[] columnas = {"Numero", "Direccion", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas,0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tblPedidos.setModel(modeloTabla);
        tblPedidos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblPedidos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tblPedidos.getSelectedRow();
                if (fila >= 0) {
                    idPedidoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                    txtDireccion.setText(modeloTabla.getValueAt(fila, 1).toString()); // nombre
                    cmbTipoPedido.setSelectedItem(modeloTabla.getValueAt(fila, 2).toString()); // categoría
                    cmbEstadoPedido.setSelectedItem( modeloTabla.getValueAt(fila, 3).toString());

                }
            }
        });
    }

    /**
     * Configura la tabla de repartidores y el evento de selección de fila.
     */
    private void inicializarTablaRepartidores(){
        String[] columnas = {"ID", "NOMBRE"};
        modeloTablaRepartidores = new DefaultTableModel(columnas,0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tblRepartidores.setModel(modeloTablaRepartidores);
        tblRepartidores.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblRepartidores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblRepartidores.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tblRepartidores.getSelectedRow();
                if (fila >= 0) {
                    idRepartidorSeleccionado = (int) modeloTablaRepartidores.getValueAt(fila, 0);
                    txtNombreRepartidor.setText(modeloTablaRepartidores.getValueAt(fila, 1).toString()); // nombre
                }
            }
        });
    }

    /**
     * Recarga la tabla de pedidos con los datos obtenidos desde el controlador.
     */
    private void actualizarTablaPedidos() {
        mostrarPedidos(controladorPedidos.obtenerPedidos());
        cmbFiltroTipo.setSelectedItem("TODOS");

    }

    /**
     * Recarga la tabla de repartidores con los datos de la base de datos.
     */
    private void actualizarTablaRepartidores() {
        modeloTablaRepartidores.setRowCount(0);
        for (Repartidor r : controladorRepartidores.obtenerRepartidores()) {
            Object[] fila = {
                    r.getId(),
                    r.getNombre()
            };
            modeloTablaRepartidores.addRow(fila);
        }
    }

    /**
     * Configura la tabla de entregas, oculta el ID auxiliar del repartidor
     * y carga en los combos los objetos relacionados al seleccionar una fila.
     */
    private void inicializarTablaEntregas() {
        modeloTablaEntregas = new DefaultTableModel(
                new String[]{"ID", "Pedido", "Dirección", "Repartidor", "Fecha", "Hora", "ID Repartidor"}, 0) {
            @Override public boolean isCellEditable(int fila, int columna) { return false; }
        };
        tblEntregas.setModel(modeloTablaEntregas);
        tblEntregas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ocultar la columna del ID del repartidor en la vista.
        tblEntregas.getColumnModel().getColumn(6).setMinWidth(0);
        tblEntregas.getColumnModel().getColumn(6).setMaxWidth(0);
        tblEntregas.getColumnModel().getColumn(6).setPreferredWidth(0);

        tblEntregas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tblEntregas.getSelectedRow();

                if (fila >= 0) {
                    idEntregaSeleccionada = Integer.parseInt(
                            modeloTablaEntregas.getValueAt(fila, 0).toString()
                    );

                    int idPedido = Integer.parseInt(
                            modeloTablaEntregas.getValueAt(fila, 1).toString()
                    );

                    int idRepartidor = Integer.parseInt(
                            modeloTablaEntregas.getValueAt(fila, 6).toString()
                    );

                    seleccionarPedidoEnCombo(idPedido);
                    seleccionarRepartidorEnCombo(idRepartidor);
                }
            }
        });
    }

    /**
     * Recarga los combos de pedidos y repartidores y la tabla de entregas.
     * Los IDs se conservan en los objetos y en una columna auxiliar oculta.
     */
    private void actualizarEntregas() {
        List<Pedido> pedidos = controladorPedidos.obtenerPedidos();
        List<Repartidor> repartidores = controladorRepartidores.obtenerRepartidores();
        Map<Integer, Pedido> pedidosPorId = new HashMap<>();
        Map<Integer, Repartidor> repartidoresPorId = new HashMap<>();
        cmbPedidoEntrega.removeAllItems();
        cmbRepartidorEntrega.removeAllItems();
        for (Pedido pedido : pedidos) {
            pedidosPorId.put(pedido.getId(), pedido);
            cmbPedidoEntrega.addItem(pedido);
        }
        for (Repartidor repartidor : repartidores) {
            repartidoresPorId.put(repartidor.getId(), repartidor);
            cmbRepartidorEntrega.addItem(repartidor);
        }
        modeloTablaEntregas.setRowCount(0);
        for (Entrega entrega : controladorEntregas.obtenerEntregas()) {
            Pedido pedido = pedidosPorId.get(entrega.getIdPedido());
            Repartidor repartidor = repartidoresPorId.get(entrega.getIdRepartidor());
            modeloTablaEntregas.addRow(new Object[]{entrega.getId(), entrega.getIdPedido(),
                    pedido == null ? "" : pedido.getDireccion(),
                    repartidor == null ? entrega.getIdRepartidor() : repartidor.getNombre(),
                    entrega.getFecha(), entrega.getHora(), entrega.getIdRepartidor()});
        }
    }

    /**
     * Selecciona en el combo el pedido que coincide con el ID recibido.
     *
     * @param idPedido identificador del pedido que se desea seleccionar.
     */
    private void seleccionarPedidoEnCombo(int idPedido) {
        for (int i = 0; i < cmbPedidoEntrega.getItemCount(); i++) {
            Pedido pedido = (Pedido) cmbPedidoEntrega.getItemAt(i);

            if (pedido.getId() == idPedido) {
                cmbPedidoEntrega.setSelectedIndex(i);
                return;
            }
        }
    }

    /**
     * Selecciona en el combo el repartidor que coincide con el ID recibido.
     *
     * @param idRepartidor identificador del repartidor que se desea seleccionar.
     */
    private void seleccionarRepartidorEnCombo(int idRepartidor) {
        for (int i = 0; i < cmbRepartidorEntrega.getItemCount(); i++) {
            Repartidor repartidor = (Repartidor) cmbRepartidorEntrega.getItemAt(i);

            if (repartidor.getId() == idRepartidor) {
                cmbRepartidorEntrega.setSelectedIndex(i);
                return;
            }
        }
    }

    /**
     * Filtra los pedidos por el tipo seleccionado en el combo.
     * Si la opción seleccionada es {@code TODOS}, muestra todos los pedidos.
     */
    private void filtrarPedidosPorTipo() {
        String tipoSeleccionado = cmbFiltroTipo.getSelectedItem().toString();

        List<Pedido> pedidos = controladorPedidos.obtenerPedidos();

        if (!tipoSeleccionado.equals("TODOS")) {
            pedidos.removeIf(pedido ->
                    !pedido.getTipo().equalsIgnoreCase(tipoSeleccionado)
            );
        }

        mostrarPedidos(pedidos);
    }

    /**
     * Actualiza las filas visibles de la tabla de pedidos.
     *
     * @param pedidos lista de pedidos que se mostrarán.
     */
    private void mostrarPedidos(List<Pedido> pedidos) {
        modeloTabla.setRowCount(0);

        for (Pedido pedido : pedidos) {
            modeloTabla.addRow(new Object[]{
                    pedido.getId(),
                    pedido.getDireccion(),
                    pedido.getTipo(),
                    pedido.getEstado()
            });
        }
    }

    /**
     * Limpia el campo de nombre y quita la selección de la tabla de repartidores.
     */
    private void limpiarCamposRepartidor() {
        txtNombreRepartidor.setText("");
        tblRepartidores.clearSelection();
        idRepartidorSeleccionado = -1;
    }

    /**
     * Restablece los combos y la selección de la tabla de entregas.
     */
    private void limpiarCamposEntrega() {
        if (cmbPedidoEntrega.getItemCount() > 0) {
            cmbPedidoEntrega.setSelectedIndex(0);
        }

        if (cmbRepartidorEntrega.getItemCount() > 0) {
            cmbRepartidorEntrega.setSelectedIndex(0);
        }

        tblEntregas.clearSelection();
        idEntregaSeleccionada = -1;
    }
}
