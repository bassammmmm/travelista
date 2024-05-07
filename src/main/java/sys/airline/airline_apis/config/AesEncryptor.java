package sys.airline.airline_apis.config;

import jakarta.persistence.AttributeConverter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Configuration
public class AesEncryptor implements AttributeConverter<Object,String> {  //converts attributes to db column and vice versa


    @Value("${aes.encryption.key}") //injected from application.properties
    private String encryptionkey;

    private final String encryptionCipher = "AES"; //used algorithm
    private Key key;
    private Cipher cipher;

    private Key getKey(){
        if(key == null){
            key = new SecretKeySpec(encryptionkey.getBytes(), encryptionCipher); //to use the key and cipher provided
        }
        return key;
    }

    private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if(cipher == null){
            cipher = Cipher.getInstance(encryptionCipher); //if null it creates instance of the cipher
        }
        return cipher;
    }

    private void initCipher(int encryptionMode) throws GeneralSecurityException { //to initialize whether i'm encrypting or decrypting based on mode
        getCipher().init(encryptionMode,getKey());
    }

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Object attribute) {
        if(attribute == null){
            return null;
        }
        initCipher(Cipher.ENCRYPT_MODE); //mode is encryption
        byte[] bytes = SerializationUtils.serialize(attribute); //converting the attribute to db col.
        return Base64.getEncoder().encodeToString(getCipher().doFinal(bytes)); //encrypts using the cipher, and encodes bytes to base64 string(64 character) from binary to text
    }

    @SneakyThrows
    @Override
    public Object convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }
        initCipher(Cipher.DECRYPT_MODE);// mode is decryption
        byte[] bytes = getCipher().doFinal(Base64.getDecoder().decode(dbData)); //return the base64 string to bytes again
        return SerializationUtils.deserialize(bytes);//converting the db col. to attribute (original object)
    }
}
