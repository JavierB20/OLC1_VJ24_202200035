/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RptHTML;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import Simbolos.Simbolo;

/**
 *
 * @author msWas
 */
public class RptTablaSimbolos {
    //Creacion de nombre del reportes
    int contadorT = 1;

    // Genera el nombre del archivo con la fecha formateada
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String fechaFormateada = sdf.format(new Date());
    String filename = "RptTablaSimbolo" + fechaFormateada + ".html";   

    // Obtiene la ruta relativa al directorio de trabajo actual
    String rutaArchivo = "./RptSalida/TablaSimbolo/" + filename; 

    public void generarReporte(List<Simbolo> tokenList) {
        // Creacion del archivo
        File file = new File(rutaArchivo);

        try {
            // Asegúrate de que los directorios necesarios existan
            File directory = new File("./RptSalida/TablaSimbolo");
            if (!directory.exists()) {
                directory.mkdirs(); // Crea los directorios necesarios
            }            
            // Escribe en el archivo
            FileWriter writer = new FileWriter(file);            // Escribir encabezado HTML
            writer.write("<!DOCTYPE html>");
            writer.write("<html><head><title>Tabla Simbolos</title></head><body>");
            writer.write("<h1>Tabla de Simbolos</h1>");
            writer.write("<table border='1'><tr><th>#</th><th>Id</th><th>Tipo</th><th>Tipo Dato</th><th>Entorno</th><th>Valor</th><th>Línea</th><th>Columna</th></tr>");
            
            for (Simbolo token : tokenList) {
                writer.write("<tr>");
                writer.write("<td>" + contadorT + "</td>");
                writer.write("<td>" + token.getId() + "</td>");
                writer.write("<td>" + token.getTipos() + "</td>");
                writer.write("<td>" + token.getTipoDato() + "</td>");
                writer.write("<td>" + token.getEntorno() + "</td>");
                writer.write("<td>" + token.getValor() + "</td>");
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

            System.out.println("HTML report generated successfully.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo HTML: " + e.getMessage());
        }
    }
}
