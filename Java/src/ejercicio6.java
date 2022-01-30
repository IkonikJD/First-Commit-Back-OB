import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;


public class ejercicio6 {

    public static class Candidato {

        ejercicio5.Usuario usuario;
        private String nombre;
        private String correo;
        private String ciudad;
        private String pais;
        private String telefono;
        private String etiqueta;

        public Candidato(String nombre, String correo, String ciudad, String pais,
                         String telefono, String etiqueta) {
            this.nombre = nombre;
            this.correo = correo;
            this.ciudad = ciudad;
            this.pais = pais;
            this.telefono = telefono;
            this.etiqueta = etiqueta;
        }

        static LinkedHashMap<Integer, Candidato> candidatos = new LinkedHashMap<>();

        static {
            candidatos.put(1, new Candidato("Pedro", "pedro@gmail.com", "Avila",
                    "España", "600100200", "Html"));
            candidatos.put(2, new Candidato("Ana", "ana@gmail.com", "Valencia",
                    "España", "600200300", "Java"));
        }

        private static final String iTextExampleImage = "C:\\Users\\J.David\\Documents\\BootCamp\\" +
                "01 - Proyecto First Commit\\Java\\captura.png";

        public static void main(String[] args) throws IOException, DocumentException {

            // Se inicia el scanner
            Scanner input = new Scanner(System.in);

            String nombre;
            String correo;
            String ciudad;
            String pais;
            String telefono;
            String etiqueta;

            System.out.println("Introduce un nombre:\n");
            nombre = input.nextLine();
            System.out.println("Introduce un correo:\n");
            correo = input.nextLine();
            System.out.println("Introduce un ciudad:\n");
            ciudad = input.nextLine();
            System.out.println("Introduce un pais:\n");
            pais = input.nextLine();
            System.out.println("Introduce un telefono:\n");
            telefono = input.nextLine();
            System.out.println("Introduce un etiqueta:\n");
            etiqueta = input.nextLine();

            Document document = new Document();

            String destino = "Curriculum.pdf";

            PdfWriter.getInstance(document, new FileOutputStream(destino));

            document.open();

            Image image;

            image = Image.getInstance(iTextExampleImage);
            image.setAbsolutePosition(450, 700);
            document.add(image);

            Phrase name = new Phrase("Nombre: " + nombre);
            document.add(name);
            Phrase email = new Phrase("\nCorreo: " + correo);
            document.add(email);
            Phrase ciud = new Phrase("\nCiudad: " + ciudad);
            document.add(ciud);
            Phrase pai = new Phrase("\nPais: " + pais);
            document.add(pai);
            Phrase tel = new Phrase("\nTelefono: " + telefono);
            document.add(tel);
            Phrase tag = new Phrase("\nEtiqueta: " + etiqueta);
            document.add(tag);

            document.close();

            System.out.println("\nPDF creado");

        }

    }
}
