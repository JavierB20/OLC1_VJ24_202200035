/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author msWas
 */
public class MainGUI extends javax.swing.JFrame {

    /**
     * Creates new form MainGUI
     */
    
    //Manejo de pestañas en JtabbedPane
    private void agregarBotonCierre(int index, String titulo){
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tabPanel.setOpaque(false);

        JLabel label = new JLabel(titulo);

        JButton closeButton = new JButton("x");
        closeButton.setMargin(new Insets(0, 0, 0, 0));
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarPestana(index);
            }
        });

        tabPanel.add(label);
        tabPanel.add(closeButton);
        JPaneEditor.setTabComponentAt(index, tabPanel);
    }
    
    private void cerrarPestana(int index) {
        if (index >= 0 && index < JPaneEditor.getTabCount()) {
            JPaneEditor.removeTabAt(index);
        }
    }
    
    
    public MainGUI() {
        initComponents();
        
        //embellecedores
        JAreaConsola.setEditable(false);
        setTitle("JavaCraft - Javier Bonilla");
        this.setLocationRelativeTo(null);
        
        //Manejo de los subMenus
        JTabbedPane jTabbedPaneCodigo = JPaneEditor;

        JMenuBar menuBar = JMenuBar;
        JMenu menuArchivo = JMenuArchivo; 
        JMenu menuEjecutar = JMenuEjecutar;
        JMenu menuRpt = JMenuRpt;
        
        //Creando los submenus
        JMenuItem subMenuArchivoAbrir = new JMenuItem("Abrir Archivo");
        JMenuItem subMenuArchivoNuevo = new JMenuItem("Nuevo Archivo");
        JMenuItem subMenuArchivoGuardarComo = new JMenuItem("Guardar Como");
        JMenuItem subMenuArchivoGuardar = new JMenuItem("Guardar");
        JMenuItem subMenuTablaSimbolos = new JMenuItem("Tabla de Simbolos");
        JMenuItem subMenuRptErrores = new JMenuItem("Reporte Errores");
        JMenuItem subMenuAST = new JMenuItem("AST");
        JMenuItem subMenuEjecutar = new JMenuItem("Ejecutar");

        
        //INICIO FUNCIONES MENUS
        
        // Agregar ActionListener para manejar la apertura de archivos
        subMenuArchivoAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo();
            }
        });
        
         // Agregar ActionListener para manejar un nuevo archivo
        subMenuArchivoNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nuevoArchivo();
            }
        });
        
        // Agregar ActionListener para manejar el guardado de archivo
        subMenuArchivoGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //guardarArchivo();
            }
        });
        
        // Agregar ActionListener para manejar el guardado como de archivo
        subMenuArchivoGuardarComo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //guardarArchivoComo();
            }
        });
        
        //FIN    FUNCIONES MENUS
        
        
        // Agregar el submenú al menú principal
        menuArchivo.add(subMenuArchivoAbrir);
        menuArchivo.add(subMenuArchivoNuevo);
        menuArchivo.add(subMenuArchivoGuardar);
        menuArchivo.add(subMenuArchivoGuardarComo);
        menuRpt.add(subMenuTablaSimbolos);
        menuRpt.add(subMenuRptErrores);
        menuRpt.add(subMenuAST);
        menuEjecutar.add(subMenuEjecutar);

        // Configurar el JMenuBar en el JFrame
        setJMenuBar(menuBar);

        // Configurar el JTabbedPane
        jTabbedPaneCodigo.setPreferredSize(new Dimension(539, 274));
        jTabbedPaneCodigo.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    
    //INICIO FUNCIONES MANEJO DE ARCHIVOS
    private void abrirArchivo() {
        JTabbedPane jTabbedPaneCodigo = JPaneEditor;

        JFileChooser fileChooser = new JFileChooser();

        // Limitar las extensiones de archivo permitidas
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (.jc)", "jc");
        fileChooser.setFileFilter(filter);

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            // Leer el contenido del archivo
            try (BufferedReader br = new BufferedReader(new FileReader(archivoSeleccionado))) {
                String contenido = "";
                String linea;
                while ((linea = br.readLine()) != null) {
                    contenido += linea + "\n";
                }

                // Mostrar el contenido en un nuevo JTabbedPane (o en tu JTabbedPane existente)
                String nombreArchivo = archivoSeleccionado.getName();
                for (int i = 0; i < jTabbedPaneCodigo.getTabCount(); i++) {
                    String nombrePestana = jTabbedPaneCodigo.getTitleAt(i);
                    if (nombrePestana.equals(nombreArchivo)) {
                        // El archivo ya está abierto, puedes mostrar un mensaje o realizar alguna acción
                        JOptionPane.showMessageDialog(this, "El archivo ya está abierto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return; // Sale del método si el archivo ya está abierto
                    }
                }
                JTextArea textArea = new JTextArea(contenido);
                JScrollPane scrollPane = new JScrollPane(textArea);
                jTabbedPaneCodigo.addTab(archivoSeleccionado.getName(), scrollPane);
                agregarBotonCierre(jTabbedPaneCodigo.indexOfTab(archivoSeleccionado.getName()), archivoSeleccionado.getName());

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al abrir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void nuevoArchivo() {
        JTabbedPane jTabbedPaneCodigo = JPaneEditor;
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        int nuevoNumero = 1;

        
        for (int i = 0; i < jTabbedPaneCodigo.getTabCount(); i++) {
            String nombrePestana = jTabbedPaneCodigo.getTitleAt(i);
                if (nombrePestana.matches("Sin nombre \\((\\d+)\\)")) {
                    int numero = Integer.parseInt(nombrePestana.replaceAll("Sin nombre \\((\\d+)\\)", "$1"));
                    nuevoNumero = Math.max(nuevoNumero, numero + 1);
            }
        }

        jTabbedPaneCodigo.addTab("Sin nombre (" + nuevoNumero + ")", scrollPane);
        agregarBotonCierre(jTabbedPaneCodigo.indexOfTab("Sin nombre (" + nuevoNumero + ")"), "Sin nombre (" + nuevoNumero + ")");

    }
    
    //FUN FUNCIONES MANEJO DE ARCHIVOS
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPaneEditor = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        JAreaConsola = new javax.swing.JTextArea();
        JMenuBar = new javax.swing.JMenuBar();
        JMenuArchivo = new javax.swing.JMenu();
        JMenuEjecutar = new javax.swing.JMenu();
        JMenuRpt = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPaneEditor.setBackground(new java.awt.Color(208, 205, 209));

        JAreaConsola.setBackground(new java.awt.Color(190, 178, 173));
        JAreaConsola.setColumns(20);
        JAreaConsola.setRows(5);
        jScrollPane1.setViewportView(JAreaConsola);

        JMenuArchivo.setText("Archivo");
        JMenuBar.add(JMenuArchivo);

        JMenuEjecutar.setText("Ejecutar");
        JMenuBar.add(JMenuEjecutar);

        JMenuRpt.setText("Reportes");
        JMenuBar.add(JMenuRpt);

        setJMenuBar(JMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
                    .addComponent(JPaneEditor))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(JPaneEditor, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea JAreaConsola;
    private javax.swing.JMenu JMenuArchivo;
    private javax.swing.JMenuBar JMenuBar;
    private javax.swing.JMenu JMenuEjecutar;
    private javax.swing.JMenu JMenuRpt;
    private javax.swing.JTabbedPane JPaneEditor;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
