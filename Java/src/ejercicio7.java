import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ejercicio7 {

    public static class Candidato {

        private String nombre;
        private String ciudad;
        private String pais;
        private String telefono;
        private String etiqueta;

        public Candidato(String nombre, String ciudad, String pais,
                         String telefono, String etiqueta) {
            this.nombre = nombre;
            this.ciudad = ciudad;
            this.pais = pais;
            this.telefono = telefono;
            this.etiqueta = etiqueta;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public void setPais(String pais) {
            this.pais = pais;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public void setEtiqueta(String etiqueta) {
            this.etiqueta = etiqueta;
        }

        @Override
        public String toString() {
            return "{" +
                    "nombre='" + nombre + '\'' +
                    ", ciudad='" + ciudad + '\'' +
                    ", pais='" + pais + '\'' +
                    ", telefono='" + telefono + '\'' +
                    ", etiqueta='" + etiqueta + '\'' +
                    '}';
        }

    }

    public static void main(String[] args) {

        LinkedHashMap<String, Candidato> candidatos = new LinkedHashMap<>();

            candidatos.put("pedro@gmail.com", new Candidato("Pedro", "Avila",
                    "España", "600100200", "Html"));
            candidatos.put("ana@gmail.com", new Candidato("Ana", "Paris",
                    "Francia", "600200300", "Java"));


        System.out.println("--- MENU ---" +
                "\n1. Añadir candidato" +
                "\n2. Borrar candidato" +
                "\n3. Filtrar por ciudad" +
                "\n4. Filtrar por presencialidad" +
                "\n5. Filtrar por traslado" +
                "\n6. Filtrar por varias cosas" +
                "\n7. Filtrar por email" +
                "\n8. Filtrar por telefono");

        Scanner input1 = new Scanner(System.in);

        int opcion;

        System.out.println("Introduce una opcion (numero):\n");
        opcion = input1.nextInt();

        switch (opcion) {

            case 1:

                Scanner input = new Scanner(System.in);

                // Declaramos las variables para leer los datos del usuario
                String nombre;
                String correo;
                String ciudad;
                String pais;
                String telefono;
                String etiqueta;

                // Se pide por teclado los datos de registro

                System.out.println("Introduce un correo:\n");
                correo = input.nextLine();
                System.out.println("Introduce un nombre:\n");
                nombre = input.nextLine();
                System.out.println("Introduce un ciudad:\n");
                ciudad = input.nextLine();
                System.out.println("Introduce un pais:\n");
                pais = input.nextLine();
                System.out.println("Introduce un telefono:\n");
                telefono = input.nextLine();
                System.out.println("Introduce un etiqueta:\n");
                etiqueta = input.nextLine();

                // Variable auxiliar que contendra la referencia de cada usuario nuevo
                Candidato nuevo;
                int i, N;

                // Se crea el objeto Usuario y se asigna su referencia
                nuevo = new Candidato(nombre, ciudad, pais, telefono, etiqueta);

                // Se asignan los valores a los atributos del nuevo usuario
                nuevo.setNombre(nombre);
                nuevo.setCiudad(ciudad);
                nuevo.setPais(pais);
                nuevo.setTelefono(telefono);
                nuevo.setEtiqueta(etiqueta);

                // Se añade el nuevo usuario al final del array
                candidatos.put(correo, nuevo);

                // Se muestra por pantalla, el usuario que se ha creado
                System.out.println("\nCandidato añadido: " + correo + " " + nombre + " " + ciudad + " " +
                        pais + " " + telefono + " " + etiqueta + "\n");
            break;
            case 2:

                Scanner input2 = new Scanner(System.in);

                String correodelete;

                System.out.println("Introduce un correo para eliminar a ese candidato:\n");
                correodelete = input2.nextLine();

                if (candidatos.containsKey(correodelete)) {
                    candidatos.remove(correodelete);
                    System.out.println("El usuario " + correodelete + " ha sido eliminado");

                    System.out.println("--- Mostrando Array Actualizada ---");
                    for (String key : candidatos.keySet()) {
                        System.out.println(key + " = " + candidatos.get(key));
                    }

                } else {
                    System.out.println("El candidato seleccionado no existe");
                }


                break;
            case 3:
                System.out.println("Opcion 3");

                Map<String, String> filteredMap = candidatos.entrySet()
                        .stream().filter(x->"España".equals(x.getValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                System.out.println("Lista Filtrada:\n" + filteredMap);
                

                break;
            case 4:
                System.out.println("Opcion 4");
                break;
            case 5:
                System.out.println("Opcion 5");
                break;
            case 6:
                System.out.println("Opcion 6");
                break;
            case 7:
                System.out.println("Opcion 7");
                break;
            case 8:
                System.out.println("Opcion 8");
                break;
            default:
                System.out.println("No existe esa opcion");
        }

    }

}
