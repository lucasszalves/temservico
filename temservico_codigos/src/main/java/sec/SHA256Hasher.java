package sec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Hasher {

    // faz o hash de uma string e devolve a string em hex
    public static String hashString(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] rawHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hash = new StringBuilder();
        for(byte b : rawHash){
            String hex = Integer.toHexString(b & 0xff);
            if(hex.length() == 1){
                hash.append('0');
            }
            hash.append(hex);
        }
        return hash.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(hashString("1234"));
    }
}
