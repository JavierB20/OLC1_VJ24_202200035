/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RptHTML;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import simbolo.tablaSimbolos;

/**
 *
 * @author msWas
 */
public class RptTablaSimbolos {
    //Creacion de nombre del reportes
    int contadorT = 1;

    LocalDateTime fechaHoraActual = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    String fechaFormateada = fechaHoraActual.format(formatter);
    String filename = "TablaSimbolos" + fechaFormateada + ".html";            
    String rutaArchivo = "src/RptSalida/TablaSimbolo/" + filename; 

    public void generarReporte(List<tablaSimbolos> tokenList) {
        // Creacion del archivo
        File file = new File(rutaArchivo);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            // Escribir encabezado HTML
            writer.write("<!DOCTYPE html>");
            writer.write("<html><head><title>Tabla Simbolos</title></head><body>");
            writer.write("<h1>Tabla de Simbolos</h1>");
            writer.write("<table border='1'><tr><th>#</th><th>Id</th><th>Tipo</th><th>Tipo Dato</th><th>Entorno</th><th>Valor</th><th>Línea</th><th>Columna</th></tr>");
            
            for (tablaSimbolos token : tokenList) {
                writer.write("<tr>");
                writer.write("<td>" + contadorT + "</td>");
                writer.write("<td>" + token.getNombre() + "</td>");
                writer.write("<td>" + token.getNombre() + "</td>");
                writer.write("<td>" + token.getNombre() + "</td>");
                writer.write("<td>" + token.getNombre() + "</td>");
                writer.write("<td>" + token.getNombre() + "</td>");
                writer.write("<td>" + token.getNombre() + "</td>");
                writer.write("<td>" + token.getNombre() + "</td>");
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
