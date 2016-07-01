package net.cloudkit.enterprises.security;

import net.cloudkit.enterprises.infrastructure.utilities.DigestsHelper;
import net.cloudkit.enterprises.infrastructure.utilities.EncodeHelper;
import org.apache.commons.lang3.StringUtils;

public class SecurityTest {

    private final static int SALT_SIZE = 8;
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;

    /**
     * @param args
     */
    public static void main(String[] args) {
        String password = "123456";
        // 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
        if (StringUtils.isNotBlank(password)) {
            byte[] salt = DigestsHelper.generateSalt(SALT_SIZE);
            String saltValue = EncodeHelper.encodeHex(salt);

            byte[] hashPassword = DigestsHelper.sha1(password.getBytes(), salt, HASH_INTERATIONS);
            System.out.println("hashPassowrd:" + EncodeHelper.encodeHex(hashPassword) + ", saltValue:" + saltValue);
        }
    }
}
