/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RptHTML;

import excepciones.Errores;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author msWas
 */
public class RptErrores {
    //Creacion de nombre del reportes
    int contadorT = 1;

    // Genera el nombre del archivo con la fecha formateada
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String fechaFormateada = sdf.format(new Date());
    String filename = "RptErrores" + fechaFormateada + ".html";   

    // Obtiene la ruta relativa al directorio de trabajo actual
    String rutaArchivo = "./RptSalida/Errores/" + filename; 


    public void generarReporte(List<Errores> tokenList) {
        // Creacion del archivo
        File file = new File(rutaArchivo);

        try {
            // Asegúrate de que los directorios necesarios existan
            File directory = new File("./RptSalida/Errores");
            if (!directory.exists()) {
                directory.mkdirs(); // Crea los directorios necesarios
            }            
            // Escribe en el archivo
            FileWriter writer = new FileWriter(file);
            // Escribir encabezado HTML
            writer.write("<!DOCTYPE html>");
            writer.write("<html><head><title>Reporte de Errores</title></head><body>");
            writer.write("<h1>Reporte de Errores</h1>");
            writer.write("<table border='1'><tr><th>#</th><th>Tipo</th><th>Descripcion</th><th>Línea</th><th>Columna</th></tr>");
            
            for (Errores token : tokenList) {
                writer.write("<tr>");
                writer.write("<td>" + contadorT + "</td>");
                writer.write("<td>" + token.getTipo() + "</td>");
                writer.write("<td>" + token.getDesc() + "</td>");
                writer.write("<td>" + token.getLinea() + "</td>");
                writer.write("<td>" + token.getColumna() + "</td>");
                writer.write("</tr>");
                contadorT++;
            }
            writer.write("</table>");
            // Cerrar etiquetas HTML
            writer.write("</body>");
            writer.write("</html>");
            writer.close();

            System.out.println("HTML report generated successfully." + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al crear el archivo HTML: " + e.getMessage());
        }
    }
}

