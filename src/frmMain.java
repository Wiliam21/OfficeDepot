
import Clases.Cliente;
import Clases.Producto;
import Clases.SQLConnection;
import Clases.Venta;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author apple
 */
public class frmMain extends javax.swing.JFrame {

    int xx, xy;
    SQLConnection sqlc;
    Connection connection;
    public Venta venta = new Venta();
    HashMap<Integer, String> categorias = new HashMap();
    String query;
    Cliente cliente;

    public frmMain() {
        initComponents();
        sqlc = new SQLConnection();
        connection = sqlc.conexion();
        pnlVenta.setVisible(true);
        pnlClientes.setVisible(false);
        pnlInventario.setVisible(false);
        pnlOfertas.setVisible(false);
        lblVentas.setForeground(Color.black);
        lblEnvio.setVisible(false);
        lblCostoEnvio.setVisible(false);
    }

    public void GenerarCuenta() {
        lblSubtotal.setText("$" + venta.getSubtotal());
        lblDescuento.setText("$" + venta.getDescuento());
        lblTotal.setText("$" + venta.getTotal());
    }

    public void CargarProductosVenta() {
        GenerarCuenta();
        String[] datos = new String[5];
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Cantidad");
        model.addColumn("Descuento");
        model.addColumn("Total");
        tblVenta.setModel(model);
        for (Producto producto : venta.productos) {
            datos[0] = producto.getNombre();
            datos[1] = producto.getPrecio() + "";
            datos[2] = producto.getCant() + "";
            datos[3] = producto.getDescuento() * 100 + "%";
            datos[4] = producto.getTotal() + "";
            model.addRow(datos);
        }
        tblVenta.setModel(model);
    }

    public void CargarCategorias() {
        String[] datos = new String[2];
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        tblDepartamentos.setModel(model);
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Categoria");
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                categorias.put(rs.getInt(1), datos[1]);
                model.addRow(datos);
            }
            tblDepartamentos.setModel(model);
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void CargarProductos() {
        CargarCategorias();
        String[] datos = new String[5];
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Stock");
        model.addColumn("Departamento");
        int IdCategoria;
        tblProductos.setModel(model);
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Producto");
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                IdCategoria = rs.getInt(5);
                datos[4] = categorias.get(IdCategoria);
                model.addRow(datos);
            }
            tblProductos.setModel(model);
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void CargarClientes() {
        String[] datos = new String[5];
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Cliente Desde");
        model.addColumn("Direccion");
        tblClientes.setModel(model);
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Cliente");
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getDate(5).toLocalDate().toString();
                datos[4] = rs.getString(4);
                model.addRow(datos);
            }
            tblClientes.setModel(model);
            rs.close();
            st.close();
        } catch (SQLException e) {
        }
    }

    public void CargarOfertas() {
        CargarCategorias();
        String[] datos = new String[5];
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Fecha Inicio");
        model.addColumn("Fecha Fin");
        model.addColumn("Porcentaje");
        model.addColumn("Departamento");
        int IdCategoria;
        tblOfertas.setModel(model);
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Oferta");
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getDate(2).toLocalDate().toString();
                datos[2] = rs.getDate(3).toLocalDate().toString();
                datos[3] = rs.getDouble(4) * 100 + "%";
                IdCategoria = rs.getInt(5);
                datos[4] = categorias.get(IdCategoria);
                model.addRow(datos);
            }
            rs.close();
            st.close();
            tblOfertas.setModel(model);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void CargarCompras() {
        String[] datos = new String[5];
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("IdCompra");
        model.addColumn("IdCliente");
        model.addColumn("Fecha");
        model.addColumn("Descuento");
        model.addColumn("Total");
        tblCompras.setModel(model);
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Venta");
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = "$" + rs.getString(4);
                datos[4] = "$" + rs.getString(5);
                model.addRow(datos);
            }
            rs.close();
            st.close();
            tblCompras.setModel(model);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLateral = new javax.swing.JPanel();
        lblVentas = new javax.swing.JLabel();
        lblDescuentos = new javax.swing.JLabel();
        lblClientes = new javax.swing.JLabel();
        lblInventario = new javax.swing.JLabel();
        lblCompras = new javax.swing.JLabel();
        pnlPrincipal = new javax.swing.JPanel();
        pnlVenta = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVenta = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblDescuento = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnBuscarCliente = new javax.swing.JButton();
        pnlClientData = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        lblClienteFrecuente = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblidCliente = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JFormattedTextField();
        btnVenta = new javax.swing.JButton();
        btnAgregarCompra = new javax.swing.JButton();
        btnQuitarProducto = new javax.swing.JButton();
        chbxEnvio = new javax.swing.JCheckBox();
        lblEnvio = new javax.swing.JLabel();
        lblCostoEnvio = new javax.swing.JLabel();
        pnlClientes = new javax.swing.JPanel();
        btnEliminarCliente = new javax.swing.JButton();
        btnAgregarCliente = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        cbxCriterioBusqueda = new javax.swing.JComboBox<>();
        txtBusqueda = new javax.swing.JTextField();
        btnBuscarClientes = new javax.swing.JButton();
        pnlInventario = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDepartamentos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        cbxCriterioBusquedaCliente = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtCampoCliente = new javax.swing.JTextField();
        btnBuscarProducto = new javax.swing.JButton();
        btnAgregarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnAgregarProducto1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        btnEliminarCategoria = new javax.swing.JButton();
        pnlOfertas = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblOfertas = new javax.swing.JTable();
        btnEliminarOferta = new javax.swing.JButton();
        btnAgregarOferta = new javax.swing.JButton();
        pnlCompras = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblCompras = new javax.swing.JTable();
        btnVerDetalles = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cbxFiltroCompra = new javax.swing.JComboBox<>();
        txtCampoCompras = new javax.swing.JTextField();
        btnFiltrarCompra = new javax.swing.JButton();
        pnlSuperior = new javax.swing.JPanel();
        lblMinimize = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        lblPanelActual = new javax.swing.JLabel();
        lblAbout = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 700));
        setUndecorated(true);
        setSize(new java.awt.Dimension(1100, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLateral.setBackground(new java.awt.Color(51, 51, 51));

        lblVentas.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 48)); // NOI18N
        lblVentas.setForeground(new java.awt.Color(255, 255, 255));
        lblVentas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblVentas.setText(" ");
        lblVentas.setToolTipText("Realizar venta");
        lblVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblVentasMouseClicked(evt);
            }
        });

        lblDescuentos.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 48)); // NOI18N
        lblDescuentos.setForeground(new java.awt.Color(255, 255, 255));
        lblDescuentos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescuentos.setText("ﰢ ");
        lblDescuentos.setToolTipText("Ofertas");
        lblDescuentos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDescuentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDescuentosMouseClicked(evt);
            }
        });

        lblClientes.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 48)); // NOI18N
        lblClientes.setForeground(new java.awt.Color(255, 255, 255));
        lblClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClientes.setText(" ");
        lblClientes.setToolTipText("Clientes");
        lblClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblClientesMouseClicked(evt);
            }
        });

        lblInventario.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 48)); // NOI18N
        lblInventario.setForeground(new java.awt.Color(255, 255, 255));
        lblInventario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblInventario.setText(" ");
        lblInventario.setToolTipText("Inventario");
        lblInventario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblInventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInventarioMouseClicked(evt);
            }
        });

        lblCompras.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 48)); // NOI18N
        lblCompras.setForeground(new java.awt.Color(255, 255, 255));
        lblCompras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCompras.setText(" ");
        lblCompras.setToolTipText("Historial");
        lblCompras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblComprasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlLateralLayout = new javax.swing.GroupLayout(pnlLateral);
        pnlLateral.setLayout(pnlLateralLayout);
        pnlLateralLayout.setHorizontalGroup(
            pnlLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblInventario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlLateralLayout.createSequentialGroup()
                .addGroup(pnlLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(lblDescuentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLateralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCompras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLateralLayout.setVerticalGroup(
            pnlLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLateralLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(lblInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(lblDescuentos, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addGap(52, 52, 52)
                .addComponent(lblCompras, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addGap(48, 48, 48))
        );

        getContentPane().add(pnlLateral, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 100, 650));

        pnlPrincipal.setBackground(new java.awt.Color(102, 102, 102));
        pnlPrincipal.setLayout(new java.awt.CardLayout());

        pnlVenta.setBackground(new java.awt.Color(102, 102, 102));

        tblVenta.setBackground(new java.awt.Color(102, 102, 102));
        tblVenta.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tblVenta.setForeground(new java.awt.Color(255, 255, 255));
        tblVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Cantidad", "Precio", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVenta.setGridColor(new java.awt.Color(102, 102, 102));
        tblVenta.setRowHeight(20);
        tblVenta.setShowVerticalLines(false);
        tblVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVentaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblVenta);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Subtotal:");

        lblSubtotal.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblSubtotal.setForeground(new java.awt.Color(255, 255, 255));
        lblSubtotal.setText("$");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total:");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Descuento:");

        lblDescuento.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblDescuento.setForeground(new java.awt.Color(255, 255, 255));
        lblDescuento.setText("$");

        lblTotal.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setText("$");

        jLabel1.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("  ID del cliente");

        btnBuscarCliente.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscarCliente.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnBuscarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarCliente.setText("Buscar");
        btnBuscarCliente.setBorder(null);
        btnBuscarCliente.setBorderPainted(false);
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        pnlClientData.setBackground(new java.awt.Color(102, 102, 102));

        jLabel5.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nombre:");

        lblNombreCliente.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 18)); // NOI18N
        lblNombreCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreCliente.setToolTipText("");

        lblClienteFrecuente.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 18)); // NOI18N
        lblClienteFrecuente.setForeground(new java.awt.Color(255, 255, 255));
        lblClienteFrecuente.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cliente frecuente:");

        jLabel8.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("ID:");

        lblidCliente.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 18)); // NOI18N
        lblidCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblidCliente.setToolTipText("");

        javax.swing.GroupLayout pnlClientDataLayout = new javax.swing.GroupLayout(pnlClientData);
        pnlClientData.setLayout(pnlClientDataLayout);
        pnlClientDataLayout.setHorizontalGroup(
            pnlClientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlClientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlClientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(pnlClientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClienteFrecuente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlClientDataLayout.createSequentialGroup()
                        .addGroup(pnlClientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblidCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlClientDataLayout.setVerticalGroup(
            pnlClientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientDataLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(pnlClientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblidCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlClientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlClientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlClientDataLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblClienteFrecuente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );

        txtIdCliente.setBackground(new java.awt.Color(51, 51, 51));
        txtIdCliente.setForeground(new java.awt.Color(255, 255, 255));
        txtIdCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIdCliente.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        btnVenta.setBackground(new java.awt.Color(0, 0, 0));
        btnVenta.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnVenta.setText("Realizar venta");
        btnVenta.setBorder(null);
        btnVenta.setBorderPainted(false);
        btnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaActionPerformed(evt);
            }
        });

        btnAgregarCompra.setBackground(new java.awt.Color(0, 0, 0));
        btnAgregarCompra.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 24)); // NOI18N
        btnAgregarCompra.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCompra.setText("  Agregar Producto");
        btnAgregarCompra.setBorder(null);
        btnAgregarCompra.setBorderPainted(false);
        btnAgregarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCompraActionPerformed(evt);
            }
        });

        btnQuitarProducto.setBackground(new java.awt.Color(0, 0, 0));
        btnQuitarProducto.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 24)); // NOI18N
        btnQuitarProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnQuitarProducto.setText("Quitar Producto");
        btnQuitarProducto.setBorder(null);
        btnQuitarProducto.setBorderPainted(false);
        btnQuitarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarProductoActionPerformed(evt);
            }
        });

        chbxEnvio.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        chbxEnvio.setForeground(new java.awt.Color(255, 255, 255));
        chbxEnvio.setText("Envio");
        chbxEnvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbxEnvioActionPerformed(evt);
            }
        });

        lblEnvio.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblEnvio.setForeground(new java.awt.Color(255, 255, 255));
        lblEnvio.setText("Costo de Envio:");

        lblCostoEnvio.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblCostoEnvio.setForeground(new java.awt.Color(255, 255, 255));
        lblCostoEnvio.setText("$100");

        javax.swing.GroupLayout pnlVentaLayout = new javax.swing.GroupLayout(pnlVenta);
        pnlVenta.setLayout(pnlVentaLayout);
        pnlVentaLayout.setHorizontalGroup(
            pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVentaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chbxEnvio)
                    .addGroup(pnlVentaLayout.createSequentialGroup()
                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblEnvio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotal)
                            .addComponent(lblSubtotal)
                            .addComponent(lblCostoEnvio)
                            .addComponent(lblDescuento))))
                .addContainerGap(73, Short.MAX_VALUE))
            .addGroup(pnlVentaLayout.createSequentialGroup()
                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlVentaLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlClientData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlVentaLayout.createSequentialGroup()
                                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAgregarCompra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnQuitarProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlVentaLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel1)))
                .addGap(39, 39, 39))
        );
        pnlVentaLayout.setVerticalGroup(
            pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVentaLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlVentaLayout.createSequentialGroup()
                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBuscarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                            .addComponent(txtIdCliente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlClientData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlVentaLayout.createSequentialGroup()
                        .addComponent(btnAgregarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuitarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlVentaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlVentaLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(chbxEnvio)
                        .addGap(14, 14, 14)
                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCostoEnvio)
                            .addComponent(lblEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSubtotal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDescuento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotal))
                        .addGap(18, 18, 18)
                        .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))))
        );

        pnlPrincipal.add(pnlVenta, "card2");

        pnlClientes.setBackground(new java.awt.Color(102, 102, 102));

        btnEliminarCliente.setBackground(new java.awt.Color(51, 51, 51));
        btnEliminarCliente.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 24)); // NOI18N
        btnEliminarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCliente.setText("  Eliminar Cliente");
        btnEliminarCliente.setBorder(null);
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnAgregarCliente.setBackground(new java.awt.Color(51, 51, 51));
        btnAgregarCliente.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 24)); // NOI18N
        btnAgregarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCliente.setText("  Agregar Cliente");
        btnAgregarCliente.setBorder(null);
        btnAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarClienteActionPerformed(evt);
            }
        });

        tblClientes.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Apellido", "Cliente Desde", "Direccion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.setRowHeight(20);
        jScrollPane1.setViewportView(tblClientes);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Buscar por:");

        cbxCriterioBusqueda.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cbxCriterioBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Apellido", "IdCliente" }));

        txtBusqueda.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        btnBuscarClientes.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscarClientes.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnBuscarClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarClientes.setText("Buscar");
        btnBuscarClientes.setBorder(null);
        btnBuscarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClientesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlClientesLayout = new javax.swing.GroupLayout(pnlClientes);
        pnlClientes.setLayout(pnlClientesLayout);
        pnlClientesLayout.setHorizontalGroup(
            pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientesLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlClientesLayout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlClientesLayout.createSequentialGroup()
                            .addComponent(cbxCriterioBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(36, 36, 36)
                            .addComponent(btnBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlClientesLayout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlClientesLayout.createSequentialGroup()
                        .addComponent(btnAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119)
                        .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(182, 182, 182))))
        );
        pnlClientesLayout.setVerticalGroup(
            pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxCriterioBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pnlPrincipal.add(pnlClientes, "card2");

        pnlInventario.setBackground(new java.awt.Color(102, 102, 102));

        tblDepartamentos.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tblDepartamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDepartamentos.setRowHeight(20);
        jScrollPane4.setViewportView(tblDepartamentos);

        tblProductos.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Apellido", "Cliente Desde", "Direccion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProductos.setRowHeight(20);
        jScrollPane3.setViewportView(tblProductos);

        cbxCriterioBusquedaCliente.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cbxCriterioBusquedaCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "IdProducto", "IdCategoria" }));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Buscar producto por:");

        txtCampoCliente.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        btnBuscarProducto.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscarProducto.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnBuscarProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarProducto.setText("Buscar");
        btnBuscarProducto.setBorder(null);
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });

        btnAgregarProducto.setBackground(new java.awt.Color(51, 51, 51));
        btnAgregarProducto.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 18)); // NOI18N
        btnAgregarProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarProducto.setText("  Agregar Producto");
        btnAgregarProducto.setBorder(null);
        btnAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });

        btnEliminarProducto.setBackground(new java.awt.Color(51, 51, 51));
        btnEliminarProducto.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 18)); // NOI18N
        btnEliminarProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarProducto.setText("  Eliminar Producto");
        btnEliminarProducto.setBorder(null);
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(51, 51, 51));
        btnActualizar.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 18)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("  Modificar Producto");
        btnActualizar.setToolTipText("");
        btnActualizar.setBorder(null);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Productos");

        btnAgregarProducto1.setBackground(new java.awt.Color(51, 51, 51));
        btnAgregarProducto1.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 18)); // NOI18N
        btnAgregarProducto1.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarProducto1.setText("  Agregar Departamento");
        btnAgregarProducto1.setBorder(null);
        btnAgregarProducto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProducto1ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Departamentos");

        btnEliminarCategoria.setBackground(new java.awt.Color(51, 51, 51));
        btnEliminarCategoria.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 18)); // NOI18N
        btnEliminarCategoria.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCategoria.setText("  Eliminar Departamento");
        btnEliminarCategoria.setBorder(null);
        btnEliminarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInventarioLayout = new javax.swing.GroupLayout(pnlInventario);
        pnlInventario.setLayout(pnlInventarioLayout);
        pnlInventarioLayout.setHorizontalGroup(
            pnlInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInventarioLayout.createSequentialGroup()
                .addGroup(pnlInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInventarioLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel10))
                    .addGroup(pnlInventarioLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(pnlInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlInventarioLayout.createSequentialGroup()
                                .addComponent(cbxCriterioBusquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCampoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btnBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(25, 65, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInventarioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInventarioLayout.createSequentialGroup()
                        .addGroup(pnlInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAgregarProducto1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarCategoria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInventarioLayout.createSequentialGroup()
                        .addGroup(pnlInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(pnlInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnEliminarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                                .addComponent(btnAgregarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(19, 19, 19))))
        );
        pnlInventarioLayout.setVerticalGroup(
            pnlInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInventarioLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(13, 13, 13)
                .addGroup(pnlInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxCriterioBusquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCampoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInventarioLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInventarioLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(pnlInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInventarioLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregarProducto1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        pnlPrincipal.add(pnlInventario, "card2");

        pnlOfertas.setBackground(new java.awt.Color(102, 102, 102));

        tblOfertas.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tblOfertas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblOfertas);

        btnEliminarOferta.setBackground(new java.awt.Color(0, 0, 0));
        btnEliminarOferta.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnEliminarOferta.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarOferta.setText("Eliminar Oferta");
        btnEliminarOferta.setBorder(null);
        btnEliminarOferta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarOfertaActionPerformed(evt);
            }
        });

        btnAgregarOferta.setBackground(new java.awt.Color(0, 0, 0));
        btnAgregarOferta.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnAgregarOferta.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarOferta.setText("Agregar Oferta");
        btnAgregarOferta.setBorder(null);
        btnAgregarOferta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarOfertaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlOfertasLayout = new javax.swing.GroupLayout(pnlOfertas);
        pnlOfertas.setLayout(pnlOfertasLayout);
        pnlOfertasLayout.setHorizontalGroup(
            pnlOfertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOfertasLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 911, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(pnlOfertasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarOferta, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(btnEliminarOferta, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(240, 240, 240))
        );
        pnlOfertasLayout.setVerticalGroup(
            pnlOfertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOfertasLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(pnlOfertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarOferta, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarOferta, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        pnlPrincipal.add(pnlOfertas, "card2");

        pnlCompras.setBackground(new java.awt.Color(102, 102, 102));

        tblCompras.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tblCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tblCompras);

        btnVerDetalles.setBackground(new java.awt.Color(0, 0, 0));
        btnVerDetalles.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnVerDetalles.setForeground(new java.awt.Color(255, 255, 255));
        btnVerDetalles.setText("Ver Detalles");
        btnVerDetalles.setBorder(null);
        btnVerDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetallesActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Filtrar Compras");

        cbxFiltroCompra.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cbxFiltroCompra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fecha", "IdCliente", "IdVenta" }));

        txtCampoCompras.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        btnFiltrarCompra.setBackground(new java.awt.Color(0, 0, 0));
        btnFiltrarCompra.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnFiltrarCompra.setForeground(new java.awt.Color(255, 255, 255));
        btnFiltrarCompra.setText("Filtrar");
        btnFiltrarCompra.setBorder(null);
        btnFiltrarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarCompraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlComprasLayout = new javax.swing.GroupLayout(pnlCompras);
        pnlCompras.setLayout(pnlComprasLayout);
        pnlComprasLayout.setHorizontalGroup(
            pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlComprasLayout.createSequentialGroup()
                .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlComprasLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnVerDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 911, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlComprasLayout.createSequentialGroup()
                        .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlComprasLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cbxFiltroCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlComprasLayout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(jLabel6)))
                        .addGap(18, 18, 18)
                        .addComponent(txtCampoCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnFiltrarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        pnlComprasLayout.setVerticalGroup(
            pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlComprasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addGap(17, 17, 17)
                .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnFiltrarCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(txtCampoCompras)
                    .addComponent(cbxFiltroCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVerDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        pnlPrincipal.add(pnlCompras, "card2");

        getContentPane().add(pnlPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 1000, 650));

        pnlSuperior.setBackground(new java.awt.Color(0, 0, 0));
        pnlSuperior.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlSuperiorMouseDragged(evt);
            }
        });
        pnlSuperior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlSuperiorMousePressed(evt);
            }
        });

        lblMinimize.setBackground(new java.awt.Color(255, 255, 255));
        lblMinimize.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 36)); // NOI18N
        lblMinimize.setForeground(new java.awt.Color(255, 255, 255));
        lblMinimize.setText("");
        lblMinimize.setToolTipText("Minimizar");
        lblMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimizeMouseClicked(evt);
            }
        });

        lblExit.setBackground(new java.awt.Color(255, 255, 255));
        lblExit.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 36)); // NOI18N
        lblExit.setForeground(new java.awt.Color(255, 255, 255));
        lblExit.setText("");
        lblExit.setToolTipText("Cerrar");
        lblExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });

        lblPanelActual.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblPanelActual.setForeground(new java.awt.Color(255, 255, 255));
        lblPanelActual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPanelActual.setText("Ventas");

        lblAbout.setFont(new java.awt.Font("UbuntuMono Nerd Font", 1, 36)); // NOI18N
        lblAbout.setForeground(new java.awt.Color(255, 255, 255));
        lblAbout.setText("𥉉");
        lblAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAboutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlSuperiorLayout = new javax.swing.GroupLayout(pnlSuperior);
        pnlSuperior.setLayout(pnlSuperiorLayout);
        pnlSuperiorLayout.setHorizontalGroup(
            pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSuperiorLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 282, Short.MAX_VALUE)
                .addComponent(lblPanelActual, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(174, 174, 174)
                .addComponent(lblMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSuperiorLayout.setVerticalGroup(
            pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblAbout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblExit)
                        .addComponent(lblMinimize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPanelActual)))
                .addGap(17, 17, 17))
        );

        getContentPane().add(pnlSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 50));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVentasMouseClicked
        lblVentas.setForeground(Color.BLACK);
        lblClientes.setForeground(Color.white);
        lblInventario.setForeground(Color.white);
        lblDescuentos.setForeground(Color.white);
        lblCompras.setForeground(Color.white);
        pnlCompras.setVisible(false);
        pnlVenta.setVisible(true);
        pnlClientes.setVisible(false);
        pnlInventario.setVisible(false);
        pnlOfertas.setVisible(false);
        lblPanelActual.setText("Ventas");
        CargarProductosVenta();
    }//GEN-LAST:event_lblVentasMouseClicked

    private void lblDescuentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDescuentosMouseClicked
        lblVentas.setForeground(Color.white);
        lblClientes.setForeground(Color.white);
        lblInventario.setForeground(Color.white);
        lblDescuentos.setForeground(Color.black);
        lblCompras.setForeground(Color.white);
        pnlCompras.setVisible(false);
        pnlVenta.setVisible(false);
        pnlClientes.setVisible(false);
        pnlInventario.setVisible(false);
        pnlOfertas.setVisible(true);
        lblPanelActual.setText("Descuentos");
        CargarOfertas();
    }//GEN-LAST:event_lblDescuentosMouseClicked

    private void lblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClientesMouseClicked
        lblVentas.setForeground(Color.white);
        lblClientes.setForeground(Color.black);
        lblInventario.setForeground(Color.white);
        lblDescuentos.setForeground(Color.white);
        lblCompras.setForeground(Color.white);
        pnlCompras.setVisible(false);
        pnlVenta.setVisible(false);
        pnlClientes.setVisible(true);
        pnlInventario.setVisible(false);
        pnlOfertas.setVisible(false);
        lblPanelActual.setText("Clientes");
        CargarClientes();
    }//GEN-LAST:event_lblClientesMouseClicked

    private void lblInventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInventarioMouseClicked
        lblVentas.setForeground(Color.white);
        lblClientes.setForeground(Color.white);
        lblInventario.setForeground(Color.black);
        lblDescuentos.setForeground(Color.white);
        lblCompras.setForeground(Color.white);
        pnlCompras.setVisible(false);
        pnlVenta.setVisible(false);
        pnlClientes.setVisible(false);
        pnlInventario.setVisible(true);
        pnlOfertas.setVisible(false);
        lblPanelActual.setText("Inventario");
        CargarProductos();
    }//GEN-LAST:event_lblInventarioMouseClicked

    private void tblVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVentaMouseClicked
        System.out.println(tblVenta.getSelectedRow());
    }//GEN-LAST:event_tblVentaMouseClicked

    private void lblMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizeMouseClicked
        this.setState(frmMain.ICONIFIED);
    }//GEN-LAST:event_lblMinimizeMouseClicked

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }//GEN-LAST:event_lblExitMouseClicked

    private void pnlSuperiorMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSuperiorMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_pnlSuperiorMouseDragged

    private void pnlSuperiorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSuperiorMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_pnlSuperiorMousePressed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        int id;
        if (!txtIdCliente.getText().isEmpty()) {
            id = Integer.parseInt(txtIdCliente.getText());
            LlenarCamposCliente(id);
        } else {
            if (tblClientes.getSelectedRow() >= 0) {
                id = Integer.parseInt(tblClientes.getValueAt(tblClientes.getSelectedRow(), 0).toString());
                LlenarCamposCliente(id);
            } else {
                JOptionPane.showMessageDialog(null, "Ingresa un Id de cliente o selecciona un cliente en la tabla de clientes");
            }
        }
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void LlenarCamposCliente(int id) {
        try {
            txtIdCliente.setText("");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Cliente WHERE IdCliente = '" + id + "'");
            rs.next();
            String Nombre = rs.getString(2);
            String Apellido = rs.getString(3);
            String Direccion = rs.getString(4);
            Date FechaRegistro = rs.getDate(5);
            cliente = new Cliente(id, Nombre, Apellido, FechaRegistro, Direccion);
            lblidCliente.setText(id + "");
            lblNombreCliente.setText(cliente.getFullName());
            venta.setIdCliente(id);
            rs = st.executeQuery("SELECT COUNT(IdVenta) FROM Venta WHERE IdCliente = '" + id + "'");
            rs.next();
            if (rs.getInt(1) > 2) {
                lblClienteFrecuente.setText("");
                lblClienteFrecuente.setForeground(Color.green);
                venta.setClienteFrecuente(true);
            } else {
                lblClienteFrecuente.setText("");
                lblClienteFrecuente.setForeground(Color.red);
                venta.setClienteFrecuente(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "El cliente no existe", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        int row = tblClientes.getSelectedRow();
        if (row >= 0) {
            int result = JOptionPane.showConfirmDialog(null, "¿Quieres eliminar este cliente?\nTambien se elimanaran las compras hechas por este cliente", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == 0) {
                try {
                    query = "DELETE FROM DetalleVenta WHERE IdVenta IN (SELECT IdVenta FROM Venta WHERE IdCliente = '" + tblClientes.getValueAt(row, 0) + "')";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.executeUpdate();
                    query = "DELETE FROM Venta WHERE IdCliente = '" + tblClientes.getValueAt(row, 0) + "'";
                    pst = connection.prepareStatement(query);
                    pst.executeUpdate();
                    query = "DELETE FROM Cliente WHERE IdCliente = '" + tblClientes.getValueAt(row, 0) + "'";
                    pst = connection.prepareStatement(query);
                    pst.executeUpdate();
                    CargarClientes();
                } catch (SQLException ex) {
                    Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnBuscarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClientesActionPerformed
        String[] datos = new String[5];
        DefaultTableModel model = new DefaultTableModel();
        if (!txtBusqueda.getText().isEmpty()) {
            String busqueda = txtBusqueda.getText();
            String campo = cbxCriterioBusqueda.getSelectedItem().toString();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Apellido");
            model.addColumn("Cliente Desde");
            model.addColumn("Direccion");
            tblClientes.setModel(model);
            txtBusqueda.setText("");
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM Cliente WHERE " + campo + " LIKE '%" + busqueda + "%'");
                while (rs.next()) {
                    datos[0] = rs.getString(1);
                    datos[1] = rs.getString(2);
                    datos[2] = rs.getString(3);
                    datos[3] = rs.getDate(5).toLocalDate().toString();
                    datos[4] = rs.getString(4);
                    model.addRow(datos);
                }
                tblClientes.setModel(model);
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btnBuscarClientesActionPerformed

    private void btnAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarClienteActionPerformed
        new frmCrearCliente(this);
    }//GEN-LAST:event_btnAgregarClienteActionPerformed

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed
        String[] datos = new String[5];
        DefaultTableModel model = new DefaultTableModel();
        if (!txtCampoCliente.getText().isEmpty()) {
            String busqueda = txtCampoCliente.getText();
            String campo = cbxCriterioBusquedaCliente.getSelectedItem().toString();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Precio");
            model.addColumn("Stock");
            model.addColumn("Departamento");
            tblProductos.setModel(model);
            txtCampoCliente.setText("");
            int IdCategoria;
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM Producto WHERE " + campo + " LIKE '%" + busqueda + "%'");
                while (rs.next()) {
                    datos[0] = rs.getString(1);
                    datos[1] = rs.getString(2);
                    datos[2] = rs.getString(3);
                    datos[3] = rs.getString(4);
                    IdCategoria = rs.getInt(5);
                    datos[4] = categorias.get(IdCategoria);
                    model.addRow(datos);
                }
                tblProductos.setModel(model);
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        new frmCrearProducto(this);
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        int row = tblProductos.getSelectedRow();
        if (row >= 0) {
            int result = JOptionPane.showConfirmDialog(null, "¿Quieres eliminar este producto?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == 0) {
                try {
                    query = "DELETE FROM Producto WHERE IdProducto = '" + tblProductos.getValueAt(row, 0) + "'";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.executeUpdate();
                    CargarProductos();
                } catch (SQLException ex) {
                    Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        int row = tblProductos.getSelectedRow();
        if (row >= 0) {
            try {
                int newStock=Integer.parseInt(tblProductos.getValueAt(row, 3).toString());
                query = "UPDATE Producto SET Stock = "+newStock+" WHERE IdProducto = '" + tblProductos.getValueAt(row, 0) + "'";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.executeUpdate();
                CargarProductos();
            } catch (SQLException ex) {
                Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnAgregarProducto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProducto1ActionPerformed
        new frmCrearCategoria(this);
    }//GEN-LAST:event_btnAgregarProducto1ActionPerformed

    private void btnEliminarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCategoriaActionPerformed
        int row = tblDepartamentos.getSelectedRow();
        if (row >= 0) {
            int result = JOptionPane.showConfirmDialog(null, "¿Quieres eliminar este departamento?\nTambien se eliminaron todos los productos de esta categoria", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == 0) {
                try {
                    query = "DELETE FROM Producto WHERE IdCategoria = '" + tblDepartamentos.getValueAt(row, 0) + "'";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.executeUpdate();
                    query = "DELETE FROM Oferta WHERE IdCategoria = '" + tblDepartamentos.getValueAt(row, 0) + "'";
                    pst = connection.prepareStatement(query);
                    pst.executeUpdate();
                    query = "DELETE FROM Categoria WHERE IdCategoria = '" + tblDepartamentos.getValueAt(row, 0) + "'";
                    pst = connection.prepareStatement(query);
                    pst.executeUpdate();
                    CargarProductos();
                } catch (SQLException ex) {
                    Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnEliminarCategoriaActionPerformed

    private void btnAgregarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCompraActionPerformed
        new frmAgregarProducto(this);
    }//GEN-LAST:event_btnAgregarCompraActionPerformed

    private void btnAgregarOfertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarOfertaActionPerformed
        new frmCrearOferta(this);
    }//GEN-LAST:event_btnAgregarOfertaActionPerformed

    private void btnEliminarOfertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarOfertaActionPerformed
        int row = tblOfertas.getSelectedRow();
        if (row >= 0) {
            int result = JOptionPane.showConfirmDialog(null, "¿Quieres eliminar esta oferta?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == 0) {
                try {
                    query = "DELETE FROM Oferta WHERE IdOferta = '" + tblOfertas.getValueAt(row, 0) + "'";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.executeUpdate();
                    CargarOfertas();
                } catch (SQLException ex) {
                    Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnEliminarOfertaActionPerformed

    private void lblComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblComprasMouseClicked
        lblVentas.setForeground(Color.white);
        lblClientes.setForeground(Color.white);
        lblInventario.setForeground(Color.white);
        lblDescuentos.setForeground(Color.white);
        lblCompras.setForeground(Color.black);
        pnlCompras.setVisible(true);
        pnlVenta.setVisible(false);
        pnlClientes.setVisible(false);
        pnlInventario.setVisible(false);
        pnlOfertas.setVisible(false);
        lblPanelActual.setText("Clientes");
        CargarCompras();
    }//GEN-LAST:event_lblComprasMouseClicked

    private void btnVerDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetallesActionPerformed
        int row = tblCompras.getSelectedRow();
        if (row >= 0) {
            int idVenta = Integer.parseInt(tblCompras.getValueAt(row, 0).toString());
            new frmDetallesVenta(idVenta);
        }
    }//GEN-LAST:event_btnVerDetallesActionPerformed

    private void btnQuitarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarProductoActionPerformed
        int row = tblVenta.getSelectedRow();
        if (row >= 0) {
            venta.RemoveProducto(row);
            CargarProductosVenta();
        }
    }//GEN-LAST:event_btnQuitarProductoActionPerformed

    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed
        if (!lblidCliente.getText().isEmpty()) {
            if (venta.productos.size() > 0) {
                try {
                    query = "INSERT INTO Venta (IdCliente,Descuento,Total,Envio) " + "VALUES (?,?,?,?)";
                    PreparedStatement pst = connection.prepareStatement(query,
                            Statement.RETURN_GENERATED_KEYS);
                    pst.setInt(1, cliente.getIdCliente());
                    pst.setDouble(2, venta.getDescuento());
                    pst.setDouble(3, venta.getTotal());
                    pst.setBoolean(4, venta.getEnvio());
                    pst.executeUpdate();
                    ResultSet result = pst.getGeneratedKeys();
                    int idVenta;
                    if (result.next()) {
                        idVenta = result.getInt(1);
                        for (Producto p : venta.productos) {
                            pst = connection.prepareStatement("INSERT INTO DetalleVenta (IdVenta,NombreProducto,Precio,Cantidad,Descuento,Total) " + "VALUES (?,?,?,?,?,?)");
                            pst.setInt(1, idVenta);
                            pst.setString(2, p.getNombre());
                            pst.setDouble(3, p.getPrecio());
                            pst.setInt(4, p.getCant());
                            pst.setDouble(5, p.getDescuento());
                            pst.setDouble(6, p.getTotal());
                            pst.executeUpdate();
                            pst = connection.prepareStatement("UPDATE Producto SET Stock = Stock - " + p.getCant() + " WHERE IdProducto = " + p.getIdProducto());
                            pst.executeUpdate();
                        }
                    }
                    pst.close();
                    venta = new Venta();
                    CargarProductosVenta();
                    lblidCliente.setText("");
                    lblClienteFrecuente.setText("");
                    lblNombreCliente.setText("");
                } catch (SQLException ex) {
                    Logger.getLogger(frmCrearCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes que ingresar productos", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes que ingresar un cliente", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnVentaActionPerformed

    private void btnFiltrarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarCompraActionPerformed
        String[] datos = new String[5];
        DefaultTableModel model = new DefaultTableModel();
        if (!txtCampoCompras.getText().isEmpty()) {
            String busqueda = txtCampoCompras.getText();
            String campo = cbxFiltroCompra.getSelectedItem().toString();
            model.addColumn("IdCompra");
            model.addColumn("IdCliente");
            model.addColumn("Fecha");
            model.addColumn("Descuento");
            model.addColumn("Total");
            tblCompras.setModel(model);
            txtCampoCliente.setText("");
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM Venta WHERE " + campo + " LIKE '%" + busqueda + "%'");
                while (rs.next()) {
                    datos[0] = rs.getString(1);
                    datos[1] = rs.getString(2);
                    datos[2] = rs.getString(3);
                    datos[3] = "$" + rs.getString(4);
                    datos[4] = "$" + rs.getString(5);
                    model.addRow(datos);
                }
                tblCompras.setModel(model);
            } catch (Exception e) {
            }
        } else {
            CargarCompras();
        }
    }//GEN-LAST:event_btnFiltrarCompraActionPerformed

    private void chbxEnvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbxEnvioActionPerformed
        if(chbxEnvio.isSelected()){
            lblCostoEnvio.setVisible(true);
            lblEnvio.setVisible(true);
            venta.setEnvio(true);
        }else{
            lblCostoEnvio.setVisible(false);
            lblEnvio.setVisible(false);
            venta.setEnvio(false);
        }
        GenerarCuenta();
    }//GEN-LAST:event_chbxEnvioActionPerformed

    private void lblAboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAboutMouseClicked
        new frmAbout();
    }//GEN-LAST:event_lblAboutMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregarCliente;
    private javax.swing.JButton btnAgregarCompra;
    private javax.swing.JButton btnAgregarOferta;
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnAgregarProducto1;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarClientes;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnEliminarCategoria;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarOferta;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnFiltrarCompra;
    private javax.swing.JButton btnQuitarProducto;
    private javax.swing.JButton btnVenta;
    private javax.swing.JButton btnVerDetalles;
    private javax.swing.JComboBox<String> cbxCriterioBusqueda;
    private javax.swing.JComboBox<String> cbxCriterioBusquedaCliente;
    private javax.swing.JComboBox<String> cbxFiltroCompra;
    private javax.swing.JCheckBox chbxEnvio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblAbout;
    private javax.swing.JLabel lblClienteFrecuente;
    private javax.swing.JLabel lblClientes;
    private javax.swing.JLabel lblCompras;
    private javax.swing.JLabel lblCostoEnvio;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblDescuentos;
    private javax.swing.JLabel lblEnvio;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblInventario;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblPanelActual;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblVentas;
    private javax.swing.JLabel lblidCliente;
    private javax.swing.JPanel pnlClientData;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JPanel pnlCompras;
    private javax.swing.JPanel pnlInventario;
    private javax.swing.JPanel pnlLateral;
    private javax.swing.JPanel pnlOfertas;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlSuperior;
    private javax.swing.JPanel pnlVenta;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblCompras;
    private javax.swing.JTable tblDepartamentos;
    private javax.swing.JTable tblOfertas;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTable tblVenta;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCampoCliente;
    private javax.swing.JTextField txtCampoCompras;
    private javax.swing.JFormattedTextField txtIdCliente;
    // End of variables declaration//GEN-END:variables
}
