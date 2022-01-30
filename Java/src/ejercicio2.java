import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class ejercicio2 {

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

        public static void iniciarSesion() throws GeneralSecurityException, IOException {

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

                    byte[] salt = new String("12345678").getBytes();
                    int iterationCount = 40000;
                    int keyLength = 128;
                    SecretKeySpec key = createSecretKey(passlogin.toCharArray(), salt, iterationCount, keyLength);
                    SecretKeySpec key2 = createSecretKey(registros.get(emaillogin).password.toCharArray(), salt, iterationCount, keyLength);

                    //System.out.println("Original password: " + originalPassword);
                    String encryptedPassword = encrypt(passlogin, key2);
                    String encryptedPassword2 = encrypt(passlogin, key);

                    String decryptedPassword = decrypt(encryptedPassword, key2);
                    String decryptedPassword2 = decrypt(encryptedPassword2, key);


                    System.out.println(encryptedPassword + "\n" + encryptedPassword2);
                    System.out.println(decryptedPassword + "\n" + decryptedPassword2);

                } else {
                    System.out.println("Contraseña Incorrecta");
                }

            } else {
                System.out.println("\nEl email indicado no existe en el sistema");
            }

        }

        // Metodo para leer usuarios e introducirlos en el Array
        public static void registrarUsuarios() throws GeneralSecurityException, IOException {

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

            byte[] salt = new String("12345678").getBytes();
            int iterationCount = 40000;
            int keyLength = 128;
            SecretKeySpec key = createSecretKey(password.toCharArray(), salt, iterationCount, keyLength);

            String originalPassword = password;
            //System.out.println("Original password: " + originalPassword);
            String encryptedPassword = encrypt(originalPassword, key);
            //System.out.println("Encrypted password: " + encryptedPassword);
            String decryptedPassword = decrypt(encryptedPassword, key);
            //System.out.println("Decrypted password: " + decryptedPassword);


            // Comprobamos que el email no este repetido
            if (registros.containsKey(email)) {
                System.out.println("\nEl email ya existe en el sistema, volviendo al registro...");
                registrarUsuarios();
            } else {

                // Se crea el objeto Usuario y se asigna su referencia
                nuevo = new Usuario(nombre, password);

                // Se asignan los valores a los atributos del nuevo usuario
                nuevo.setNombre(nombre);
                nuevo.setPassword(encryptedPassword);

                // Se añade el nuevo usuario al final del array
                registros.put(email, nuevo);

                // Se muestra por pantalla, el usuario que se ha creado
                System.out.println("\nUsuario creado: " + email + "," + nombre + "," + encryptedPassword + "\n");
            }
        }

        public static String decrypt(String string, SecretKeySpec key) throws GeneralSecurityException, IOException {
            String iv = string.split(":")[0];
            String property = string.split(":")[1];
            Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
            return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
        }

        private static byte[] base64Decode(String property) throws IOException {
            return Base64.getDecoder().decode(property);
        }

        public static String encrypt(String dataToEncrypt, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
            Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.ENCRYPT_MODE, key);
            AlgorithmParameters parameters = pbeCipher.getParameters();
            IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
            byte[] cryptoText = pbeCipher.doFinal(dataToEncrypt.getBytes("UTF-8"));
            byte[] iv = ivParameterSpec.getIV();
            return base64Encode(iv) + ":" + base64Encode(cryptoText);
        }

        private static String base64Encode(byte[] bytes) {
            return Base64.getEncoder().encodeToString(bytes);
        }

        public static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
            SecretKey keyTmp = keyFactory.generateSecret(keySpec);
            return new SecretKeySpec(keyTmp.getEncoded(), "AES");
        }


    }
}

