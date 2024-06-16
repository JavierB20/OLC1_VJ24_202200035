/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import RptHTML.RptErrores;
import RptHTML.RptTablaSimbolos;
import VariablesGlobales.Variables;
import Excepciones.Errores;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import Patroninterprete.patroninterprete;
import Simbolos.Simbolo;

/**
 *
 * @author msWas
 */
public class MainGUI extends javax.swing.JFrame {
    Variables vars = new Variables();

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
        Font tabFont = new Font("Rockwell", Font.BOLD, 24);
        JPaneEditor.setFont(tabFont);
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
                guardarArchivoComo();
            }
        });
        
        // Agregar ActionListener para manejar EL RPT tokens
        subMenuRptErrores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generarReporteErrores();
                } catch (Exception ex) {
                    Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        // Agregar ActionListener para manejar EL RPT tokens
        subMenuTablaSimbolos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generarTablaSimbolo();
                } catch (Exception ex) {
                    Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       
        // Agregar ActionListener para ejecutar archivo
        subMenuEjecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarArchivo();
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
        Font tabFont = new Font("Rockwell", Font.BOLD, 24);
        JPaneEditor.setFont(tabFont);
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
  
    private void guardarArchivoComo() {
        JTabbedPane jTabbedPaneCodigo = JPaneEditor;

        int indexPestanaActual = jTabbedPaneCodigo.getSelectedIndex();
        if (indexPestanaActual == -1) {
            // No hay pestaña seleccionada
            JOptionPane.showMessageDialog(this, "No hay pestaña seleccionada para guardar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextArea textArea = (JTextArea) ((JScrollPane) jTabbedPaneCodigo.getComponentAt(indexPestanaActual)).getViewport().getView();
        String contenido = textArea.getText();

        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showSaveDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoGuardar = fileChooser.getSelectedFile();
            
            // Verificar si el archivo ya existe en la ubicación seleccionada
            if (archivoGuardar.exists()) {
                int respuesta = JOptionPane.showConfirmDialog(this,
                        "El archivo ya existe. ¿Deseas sobrescribirlo?",
                        "Archivo Existente",
                        JOptionPane.YES_NO_OPTION);
                
                if (respuesta != JOptionPane.YES_OPTION) {
                    return; // El usuario no quiere sobrescribir el archivo
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoGuardar))) {
                String nuevoNombre = archivoGuardar.getName();
                writer.write(contenido);
                jTabbedPaneCodigo.setTitleAt(indexPestanaActual, nuevoNombre);
                JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    
    //FIN FUNCIONES MANEJO DE ARCHIVOS
    
    
    //INICIO FUNCIONES REPORTES
    private void generarReporteErrores() throws Exception{
        RptErrores rptErrores = new RptErrores();
        LinkedList<Errores> list = Variables.getGlobalLinkedList();
        System.out.println(list);

        if(list.size() > 0) {
            rptErrores.generarReporte(list);
            JOptionPane.showMessageDialog(this, "Se ha generado el reporte de errores", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this, "No se ha analizado ningun archivo/ No se tienen errores", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generarTablaSimbolo() throws Exception{
        RptTablaSimbolos rptTablaSimbolo = new RptTablaSimbolos();
        LinkedList<Simbolo> list = Variables.getGlobalLstSimbolo();

        if(list.size() > 0) {
            rptTablaSimbolo.generarReporte(list);
            JOptionPane.showMessageDialog(this, "Se ha generado la tabla de simbolos", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this, "No se ha analizado ningun archivo/ No se tienen simbolo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //FIN FUNCIONES REPORTES
    private void ejecutarArchivo(){
        JTabbedPane jTabbedPaneCodigo = JPaneEditor;
        patroninterprete patron = new patroninterprete();
        JAreaConsola.setText("");


        int indexPestanaActual = jTabbedPaneCodigo.getSelectedIndex();
        if (indexPestanaActual == -1) {
            // No hay pestaña seleccionada
            JOptionPane.showMessageDialog(this, "No hay pestaña seleccionada para ejecutar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Component componenteActual = jTabbedPaneCodigo.getComponentAt(indexPestanaActual);
        if (componenteActual instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) componenteActual;
            Component componenteInterno = scrollPane.getViewport().getView();
            if (componenteInterno instanceof JTextArea) {
                JTextArea textArea = (JTextArea) componenteInterno;
                String contenido = textArea.getText();
                if (contenido.equals("") || contenido.equals(" ") || contenido == null) {
                    JOptionPane.showMessageDialog(this, "La pestaña actual no tiene codigo fuente que analizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String respuesta = patron.Ejecutar(contenido);
                    JAreaConsola.setText(respuesta);
                }
            } else {
                JOptionPane.showMessageDialog(this, "La pestaña actual no contiene un componente de texto editable.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "La pestaña actual no contiene un JScrollPane.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    //INICIO FUNCIONES EJECUTAR
    
    //FIN FUNCIONES EJECUTAR

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
        JPaneEditor.setFont(new java.awt.Font("Rockwell", 0, 36)); // NOI18N

        JAreaConsola.setBackground(new java.awt.Color(204, 204, 204));
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JPaneEditor, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(JPaneEditor, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
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
