import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Scanner;

public class argon2 implements PasswordEncoder {

    private static final int DEFAULT_SALT_LENGTH = 16;
    private static final int DEFAULT_HASH_LENGTH = 32;
    private static final int DEFAULT_PARALLELISM = 1;
    private static final int DEFAULT_MEMORY = 1 << 12;
    private static final int DEFAULT_ITERATIONS = 3;
    private final int hashLength;
    private final int parallelism;
    private final int memory;
    private final int iterations;
    private final BytesKeyGenerator saltGenerator;
    private argon2 Argon2EncodingUtils;

    public argon2(int saltLength, int hashLength,
                                 int parallelism, int memory, int iterations) {

        this.hashLength = hashLength;
        this.parallelism = parallelism;
        this.memory = memory;
        this.iterations = iterations;

        this.saltGenerator = KeyGenerators.secureRandom(saltLength);
    }

    public argon2() {
        this(DEFAULT_SALT_LENGTH, DEFAULT_HASH_LENGTH,
                DEFAULT_PARALLELISM, DEFAULT_MEMORY, DEFAULT_ITERATIONS);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        byte[] salt = saltGenerator.generateKey();
        byte[] hash = new byte[hashLength];

        Argon2Parameters params = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id).
                withSalt(salt).
                withParallelism(parallelism).
                withMemoryAsKB(memory).
                withIterations(iterations).
                build();
        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(params);
        generator.generateBytes(rawPassword.toString().toCharArray(), hash);

        return Argon2EncodingUtils.encode(hash, params);
    }

    private String encode(byte[] hash, Argon2Parameters params) {
        return null;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return false;
    }

    public static class Usuario {

        private String nombre;
        private String password;

        public Usuario(String nombre, String password) {
            this.nombre = nombre;
            this.password = password;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "{" +
                    "nombre='" + nombre + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    //Clase Principal
    public static class Menu {

        // Se inicia el scanner
        static Scanner input = new Scanner(System.in);

        // Se crea un LinkedHashmap

        static LinkedHashMap<String, Usuario> registros = new LinkedHashMap<>();

        static {
            registros.put("david@gmail.com", new Usuario("David", "david"));
            registros.put("ana@gmail.com", new Usuario("Ana", "ana"));
            registros.put("pepe@gmail.com", new Usuario("Pepe", "pepe"));
        }

        // Metodo para mostrar el array del usuario
        public static void mostrarUsuarios() {

            for (String key : registros.keySet()) {
                System.out.println(key + " = " + registros.get(key));
            }

        }

        // Metodo Main
        public static void main(String[] args) throws GeneralSecurityException, IOException {

            String opcion;

            System.out.println("Introduce (r)egistro o (i)nicio de sesion:\n");
            opcion = input.nextLine();

            if (opcion.contentEquals("r")) {
                registrarUsuarios();
                mostrarUsuarios();
            }
            if (opcion.contentEquals("i")) {
                iniciarSesion();
            }

        }

        public static void iniciarSesion() {

            String emaillogin;
            String passlogin;

            System.out.println("Introduce tu email:\n");
            emaillogin = input.nextLine();
            System.out.println("Introduce tu contraseña:\n");
            passlogin = input.nextLine();

            if (registros.containsKey(emaillogin)) {
                System.out.println("\nSi que existe");

                if (Objects.equals(registros.get(emaillogin).password, passlogin)) {
                    System.out.println("Contraseña correcta");

                    Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();

                    Instant start = Instant.now();  // start timer

                    String hash1 = encoder.encode(registros.get(emaillogin).password);
                    String hash = encoder.encode(passlogin);
                    System.out.println(registros.get(emaillogin).password + hash1);
                    System.out.println(passlogin + hash);

                    Instant end = Instant.now();    // end timer

                    System.out.println(String.format(
                            "Hashing took %s ms",
                            ChronoUnit.MILLIS.between(start, end)
                    ));

                } else {
                    System.out.println("Contraseña Incorrecta");
                }

            } else {
                System.out.println("\nEl email indicado no existe en el sistema");
            }

        }

        // Metodo para leer usuarios e introducirlos en el Array
        public static void registrarUsuarios() {

            // Declaramos las variables para leer los datos del usuario
            String email;
            String nombre;
            String password;

            // Variable auxiliar que contendra la referencia de cada usuario nuevo
            Usuario nuevo;
            int i, N;

            // Se pide por teclado los datos de registro

            System.out.println("Introduce tu email:\n");
            email = input.nextLine();
            System.out.println("Introduce tu nombre:\n");
            nombre = input.nextLine();
            System.out.println("Introduce tu contraseña:\n");
            password = input.nextLine();

            Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();

            Instant start = Instant.now();  // start timer

            String hash = encoder.encode(password);
            System.out.println(password + hash);

            Instant end = Instant.now();    // end timer

            System.out.println(String.format(
                    "Hashing took %s ms",
                    ChronoUnit.MILLIS.between(start, end)
            ));

            // Comprobamos que el email no este repetido
            if (registros.containsKey(email)) {
                System.out.println("\nEl email ya existe en el sistema, volviendo al registro...");
                registrarUsuarios();
            } else {

                // Se crea el objeto Usuario y se asigna su referencia
                nuevo = new Usuario(nombre, password);

                // Se asignan los valores a los atributos del nuevo usuario
                nuevo.setNombre(nombre);
                nuevo.setPassword(hash);

                // Se añade el nuevo usuario al final del array
                registros.put(email, nuevo);

                // Se muestra por pantalla, el usuario que se ha creado
                System.out.println("\nUsuario creado: " + email + "," + nombre + "," + hash + "\n");
            }
        }

    }
}
