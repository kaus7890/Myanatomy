import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

class UserProfile implements Serializable {
    String username;
    String email;

    public UserProfile(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String toString() {
        return "UserProfile{username='" + username + "', email='" + email + "'}";
    }
}

public class SimpleEncryption {
    public static void main(String[] args) throws Exception {
        UserProfile user = new UserProfile("kaustav", "kaustavyadav@gmail.com");

        SecretKey key = new SecretKeySpec("1234567890123456".getBytes(), "AES");

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new CipherOutputStream(new FileOutputStream("user.enc"),
                        Cipher.getInstance("AES").init(Cipher.ENCRYPT_MODE, key)))) {
            oos.writeObject(user);
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new CipherInputStream(new FileInputStream("user.enc"),
                        Cipher.getInstance("AES").init(Cipher.DECRYPT_MODE, key)))) {
            UserProfile decryptedUser = (UserProfile) ois.readObject();
            System.out.println(decryptedUser);
        }
    }
}
