import java.io.*;
import java.util.*;

public class ejercicio1 {

    public static void main(String[] args) {

        String fichero = "C:\\Users\\J.David\\Documents\\BootCamp\\01 - Proyecto First Commit\\correos.csv";
        // String fichero = "C:\Users\J.David\Documents\BootCamp\01 - Proyecto First Commit\correos.csv";

        // Leemos el fichero si el usuario lo desea
        // Scanner leer = new Scanner(System.in);
        // System.out.println("Indica el fichero csv");
        // String fichero = leer.nextLine();

        BufferedReader bufferLectura = null;
        BufferedReader bufferLectura2;
        try {
            // Abrir el .csv en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader(fichero));

            // Abrir el csv para contar lineas totales
            bufferLectura2 = new BufferedReader(new FileReader(fichero));
            int contador = (int) bufferLectura2.lines().count();
            //System.out.println(contador);

            // Leer una linea del archivo
            String linea = bufferLectura.readLine();
            int contLinea = contador;

            //System.out.println(linea);

            LinkedHashMap<String,String> map = new LinkedHashMap<>();

            while (linea != null) {
                // Separar la linea leída con el separador definido previamente
                String[] repetidos = linea.split(",");

                String correo = repetidos[0];
                String usuario = repetidos[2];

                //Mostrar fichero completo
                //System.out.println(correo + "," + nombre + "," + usuario);

                if (map.containsKey(correo)) {
                    while((bufferLectura.readLine()) != null) {
                        contLinea--;
                    }
                    System.err.println("El correo " + correo +
                            " se ha repetido en la linea: " + contLinea);
                }

                else {
                    map.put(repetidos[0], repetidos[1] + "," + usuario + "\n");
                }

                //System.out.println(Arrays.toString(repetidos));

                // Volver a leer otra línea del fichero
                linea = bufferLectura.readLine();
            }
            System.out.println("\nEl fichero se ha almacenado correctamente " +
                    "omitiendo los duplicados:\n\n" + map);
            System.out.println("Las lineas procesadas correctamente son: " + contador +
                    " | No ha ocurrido ningun error");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierro el buffer de lectura
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}