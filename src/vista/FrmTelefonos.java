/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import dao.*;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Telefono;
import modelo.Trabajador;

/**
 *
 * @author Usuario
 */
public class FrmTelefonos extends javax.swing.JInternalFrame {

    TelefonoDAO tlfDAO = new TelefonoDAO();
    TrabajadorDAO tDAO = new TrabajadorDAO();
    ClienteDAO clDAO = new ClienteDAO();
    String accion, person;

    /**
     * Creates new form FrmTelefonos
     */
    public FrmTelefonos() {
        initComponents();
        this.listar();
        this.cargarTrabajadores();
        this.cargarClientes();
        this.accion = "guardar";
        txtId.setVisible(false);
    }

    private void listar() {
        ArrayList<Object> listaTrabajadores = tlfDAO.getListTrabajadores();
        ArrayList<Object> listaClientes = tlfDAO.getListClientes();
        DefaultTableModel modeloTabla;

        String[] titulos = {"ID", "Nombre", "Apellido", "RUT", "Nro. Telefono", "Persona"};
        modeloTabla = new DefaultTableModel(null, titulos);

        for (Object obj : listaTrabajadores) {
            Trabajador trabajador = (Trabajador) obj;
            Object[] row = new Object[6];
            row[0] = trabajador.getTelefono().getIdTelefono();
            row[1] = trabajador.getNombre();
            row[2] = trabajador.getApellido();
            row[3] = trabajador.getRut();
            row[4] = trabajador.getTelefono().getNumTelf();
            row[5] = "Trabajador";
            modeloTabla.addRow(row);
        }
        for (Object obj : listaClientes) {
            Cliente cliente = (Cliente) obj;
            Object[] row = new Object[6];
            row[0] = cliente.getTelefono().getIdTelefono();
            row[1] = cliente.getNombre();
            row[2] = cliente.getApellido();
            row[3] = cliente.getRut();
            row[4] = cliente.getTelefono().getNumTelf();
            row[5] = "Cliente";
            modeloTabla.addRow(row);
        }

        tablaListadoTelefonos.setModel(modeloTabla);
        tabGeneral.setEnabledAt(1, false);
    }

    private void limpiar() {
        txtTelefono.setText("");
        this.accion = "guardar";
    }

    private void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "System", JOptionPane.ERROR_MESSAGE);
    }

    private void mensajeOk(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "System", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cargarTrabajadores() {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        ArrayList<Object> lista = tDAO.getList();

        for (Object obj : lista) {
            Trabajador trabajador = (Trabajador) obj;
            items.addElement(trabajador);
        }
        cboTrabajadores.setModel(items);
    }

    private void cargarClientes() {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        ArrayList<Object> lista = clDAO.getList();

        for (Object obj : lista) {
            Cliente cliente = (Cliente) obj;
            items.addElement(cliente);
        }
        cboClientes.setModel(items);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabGeneral = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListadoTelefonos = new javax.swing.JTable();
        btnNuevoTrabajador = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblTrabajador = new javax.swing.JLabel();
        cboTrabajadores = new javax.swing.JComboBox<>();
        cboClientes = new javax.swing.JComboBox<>();
        lblClientes = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setTitle("Telefonos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tablaListadoTelefonos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaListadoTelefonos);

        btnNuevoTrabajador.setText("Nuevo Telefono Trabajadores");
        btnNuevoTrabajador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTrabajadorActionPerformed(evt);
            }
        });

        btnNuevoCliente.setText("Nuevo Telefono Clientes");
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(btnNuevoTrabajador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(331, 331, 331))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(82, 82, 82)
                    .addComponent(btnNuevoCliente)
                    .addContainerGap(593, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(btnEliminar)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoTrabajador)
                    .addComponent(btnEditar))
                .addGap(60, 60, 60))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(335, Short.MAX_VALUE)
                    .addComponent(btnNuevoCliente)
                    .addGap(114, 114, 114)))
        );

        tabGeneral.addTab("Listado", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Nro. Telefono(*)");

        lblTrabajador.setText("Trabajador(*)");

        lblClientes.setText("Cliente(*)");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        jLabel2.setText("(*) Indica que es un campo obligatorio");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblClientes)
                        .addGap(84, 84, 84)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addGap(80, 80, 80)
                                .addComponent(btnVolver))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(lblTrabajador)
                                .addGap(48, 48, 48)
                                .addComponent(cboTrabajadores, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(187, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTrabajador)
                    .addComponent(cboTrabajadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientes)
                    .addComponent(cboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnVolver))
                .addGap(114, 114, 114))
        );

        tabGeneral.addTab("Mantenimiento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabGeneral, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabGeneral, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoTrabajadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTrabajadorActionPerformed
        tabGeneral.setEnabledAt(1, true);
        tabGeneral.setEnabledAt(0, false);
        tabGeneral.setSelectedIndex(1);
        this.accion = "guardar";
        btnGuardar.setText("Guardar");
        lblClientes.setVisible(false);
        cboClientes.setVisible(false);
        lblTrabajador.setVisible(true);
        cboTrabajadores.setVisible(true);
        this.person = "Trabajador";
    }//GEN-LAST:event_btnNuevoTrabajadorActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (tablaListadoTelefonos.getSelectedRowCount() == 1) {
            String id = String.valueOf(tablaListadoTelefonos.getValueAt(tablaListadoTelefonos.getSelectedRow(), 0));
            String telefono = String.valueOf(tablaListadoTelefonos.getValueAt(tablaListadoTelefonos.getSelectedRow(), 4));

            txtId.setText(id);
            txtTelefono.setText(telefono);

            tabGeneral.setEnabledAt(0, false);
            tabGeneral.setEnabledAt(1, true);
            tabGeneral.setSelectedIndex(1);
            this.accion = "editar";
            btnGuardar.setText("Editar");
            lblClientes.setVisible(false);
            cboClientes.setVisible(false);
            lblTrabajador.setVisible(false);
            cboTrabajadores.setVisible(false);
        } else {
            this.mensajeError("Seleccione 1 registro a editar.");
        }

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtTelefono.getText().length() == 0 || txtTelefono.getText().length() > 9) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un n??mero v??lido", "System", JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
            return;
        }

        if (this.accion.equals("editar")) {
            int id = Integer.parseInt(txtId.getText());
            String telefono = txtTelefono.getText();
            Telefono tlf = new Telefono(id, telefono);
            if (tlfDAO.modificar(tlf)) {
                this.mensajeOk("Numero actualizado existosamente");
                this.limpiar();
                this.listar();
                tabGeneral.setEnabledAt(0, true);
                tabGeneral.setEnabledAt(1, false);
                tabGeneral.setSelectedIndex(0);
            } else {
                this.mensajeError("Ocurri?? un error, el numero no fue actualizado");
            }

        } else {

            if (this.person.equals("Trabajador")) {
                String numero = txtTelefono.getText();
                Trabajador trabajador = (Trabajador) cboTrabajadores.getSelectedItem();
                Telefono tlf = new Telefono(numero);
                int id = tlfDAO.insertarID(tlf);
                if (id != 0) {
                    if (tlfDAO.insertarTlfTrabajador(id, trabajador.getIdTrabajador())) {
                        this.mensajeOk("Telefono a??adido existosamente");
                        this.limpiar();
                        this.listar();
                        tabGeneral.setEnabledAt(0, true);
                        tabGeneral.setEnabledAt(1, false);
                        tabGeneral.setSelectedIndex(0);
                    } else {
                        this.mensajeError("Ocurri?? un error al intentar agregar el telefono");
                    }
                }
            } else {
                String numero = txtTelefono.getText();
                Cliente cliente = (Cliente) cboClientes.getSelectedItem();
                Telefono tlf = new Telefono(numero);
                int id = tlfDAO.insertarID(tlf);
                if (id != 0) {
                    if (tlfDAO.insertarTlfCliente(id, cliente.getIdCliente())) {
                        this.mensajeOk("Telefono a??adido existosamente");
                        this.limpiar();
                        this.listar();
                        tabGeneral.setEnabledAt(0, true);
                        tabGeneral.setEnabledAt(1, false);
                        tabGeneral.setSelectedIndex(0);
                    } else {
                        this.mensajeError("Ocurri?? un error al intentar agregar el telefono");
                    }
                }
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        tabGeneral.setEnabledAt(1, true);
        tabGeneral.setEnabledAt(0, false);
        tabGeneral.setSelectedIndex(1);
        this.accion = "guardar";
        btnGuardar.setText("Guardar");
        lblTrabajador.setVisible(false);
        cboTrabajadores.setVisible(false);
        lblClientes.setVisible(true);
        cboClientes.setVisible(true);
        this.person = "Cliente";
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (tablaListadoTelefonos.getSelectedRowCount() == 1) {
            String persona = String.valueOf(tablaListadoTelefonos.getValueAt(tablaListadoTelefonos.getSelectedRow(), 5));
            if (persona.equals("Trabajador")) {

                String id = String.valueOf(tablaListadoTelefonos.getValueAt(tablaListadoTelefonos.getSelectedRow(), 0));
                String telefono = String.valueOf(tablaListadoTelefonos.getValueAt(tablaListadoTelefonos.getSelectedRow(), 4));
                if (JOptionPane.showConfirmDialog(this, "Deseas eliminar el n??mero de telefono : " + telefono + " ?", "Eliminar", JOptionPane.YES_NO_OPTION) == 0) {
                    if (tlfDAO.eliminar(Integer.parseInt(id))) {
                        this.mensajeOk("Registro eliminado");
                        this.listar();
                    } else {
                        this.mensajeError("Error, el regristro no pudo ser eliminado");
                    }
                }
            } else {
                String id = String.valueOf(tablaListadoTelefonos.getValueAt(tablaListadoTelefonos.getSelectedRow(), 0));
                String telefono = String.valueOf(tablaListadoTelefonos.getValueAt(tablaListadoTelefonos.getSelectedRow(), 4));
                if (JOptionPane.showConfirmDialog(this, "Deseas eliminar el n??mero de telefono : " + telefono + " ?", "Eliminar", JOptionPane.YES_NO_OPTION) == 0) {
                    if (tlfDAO.eliminarCl(Integer.parseInt(id))) {
                        this.mensajeOk("Registro eliminado");
                        this.listar();
                    } else {
                        this.mensajeError("Error, el regristro no pudo ser eliminado");
                    }

                }
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        tabGeneral.setEnabledAt(0, true);
        tabGeneral.setEnabledAt(1, false);
        tabGeneral.setSelectedIndex(0);
        cboTrabajadores.setVisible(false);
        lblTrabajador.setVisible(false);
        cboClientes.setVisible(false);
        lblClientes.setVisible(false);
        this.limpiar();
    }//GEN-LAST:event_btnVolverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoTrabajador;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cboClientes;
    private javax.swing.JComboBox<String> cboTrabajadores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClientes;
    private javax.swing.JLabel lblTrabajador;
    private javax.swing.JTabbedPane tabGeneral;
    private javax.swing.JTable tablaListadoTelefonos;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
