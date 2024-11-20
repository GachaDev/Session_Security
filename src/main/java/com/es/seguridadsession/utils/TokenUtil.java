package com.es.seguridadsession.utils;

import org.mindrot.jbcrypt.BCrypt;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;
/**
 * CLASE ENCARGADA DE GENERAR TOKENS
 */
public class TokenUtil {
    // PARTE PARA HASHEAR CONTRASEÑAS Y COMPROBAR SI SON IGUALES
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    // PARTE PARA CIFRAR SIMÉTRICAMENTE UNA CADENA -> GENERAR UN TOKEN
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "GzftSdqL95nhi6DC"; // 16 caracteres

    public static String encrypt(String nombreUsuario) throws Exception {
        // EL TOKEN A GENERAR ES nombreUsuario+clave_secreta
        String tokenSinCifrar = nombreUsuario+SECRET_KEY;

        SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(SECRET_KEY.getBytes(),16), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] tokenCifrado = cipher.doFinal(tokenSinCifrar.getBytes());
        return Base64.getEncoder().encodeToString(tokenCifrado);
    }

    public String decrypt(String tokenCifrado) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(SECRET_KEY.getBytes(),16), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] tokenDescifrado = cipher.doFinal(Base64.getDecoder().decode(tokenCifrado));
        return new String(tokenDescifrado);
    }
}
