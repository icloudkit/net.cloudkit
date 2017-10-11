package net.cloudkit.enterprises.infrastructure.shiro;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 重新定义密码校验
 * The Class PasswordHashedCredentialsMatcher
 */
public class PasswordHashedCredentialsMatcher extends HashedCredentialsMatcher {
    private static final Logger logger = LoggerFactory.getLogger(PasswordHashedCredentialsMatcher.class);

    private static final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

    private static final int HASH_ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private static byte[] PBE_SALT = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF};

    private static byte[] IV = {0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC, 0xD, 91};


    /**
     * 密钥算法 java6支持56位密钥，bouncycastle支持64位
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法/工作模式/填充方式
     * JAVA6 支持PKCS5PADDING填充方式
     * Bouncy castle支持PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    /**
     * 验证密码是否正确
     *
     * @param authcToken 登录用户信息（传过来的值）
     * @param authcInfo  数据库信息
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo authcInfo) {
        try {
            AES256Encryption aes256 = new AES256Encryption();
            UsernamePasswordExpandToken token = (UsernamePasswordExpandToken) authcToken;
            ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) authcInfo.getPrincipals().getPrimaryPrincipal();
            char[] password = token.getPassword();
            String loginTime = token.getLoginTime();
            String salt = shiroUser.getSalt();
            String key = loginTime + salt;
            //huangzhigang 2016-01-20 update
            String decryptResult = aes256.decrypt(new String(password),key);
            decryptResult = decryptResult.substring(0,decryptResult.length() - 8);
            token.setPassword(decryptResult.toCharArray());
            return super.doCredentialsMatch(token, authcInfo);
        } catch (Exception e) {
            logger.error("ASE256密码解密错误！"+ e.getMessage(), e);
            throw new AccountException();
        }
    }


}



class AES256Encryption {

    private static final Logger logger = LoggerFactory.getLogger(AES256Encryption.class);

    private static final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

    private static final int HASH_ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private static byte[] PBE_SALT = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF};

    private static byte[] IV = {0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC, 0xD, 91};


    /**
     * 密钥算法 java6支持56位密钥，bouncycastle支持64位
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法/工作模式/填充方式
     * JAVA6 支持PKCS5PADDING填充方式
     * Bouncy castle支持PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    /**
     * 解密
     *
     * @param decrypt 需要解密的内容
     * @param key     密钥
     * @return
     */
    public String decrypt(String decrypt, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Security.addProvider(new BouncyCastleProvider());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
        PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray(), PBE_SALT, HASH_ITERATIONS, KEY_LENGTH);
        SecretKey secretKey = keyfactory.generateSecret(keySpec);
        byte[] encoded = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(encoded, KEY_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
        Cipher c = Cipher.getInstance(CIPHER_ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptResult = c.doFinal(Base64.decode(decrypt));

        return new String(decryptResult, "UTF-8");
    }

    /**
     *
     * @param cipherText 密码加密的密文
     * @param timeSlot 加密时间段
     * @param telSerialNo 手机序列号
     * @return
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeySpecException
     */
    public String decryptPassword(String cipherText, String timeSlot,String telSerialNo) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        String dateTime = DateTimeUtility.convertLongToDateString(DateTimeUtility.YYYYMMDDHHMMSS, Long.parseLong(timeSlot));
        String second = dateTime.substring(dateTime.length() - 2);
        String time = dateTime.substring(0, dateTime.length() - 2);
        String serialNo = telSerialNo.substring(0, 8);
        String key = serialNo + second;
        /** 密码明文  */
        String pwd = decrypt(cipherText, key);     //
        String password = pwd.substring(0, pwd.length() - time.length());
        return password;
    }


    public static void main(String[] args) throws Exception {
        String key = "D7C7B9D729";
        String pwd = "138JPRbQwJds7buylNK3k2IeYgfNcUn3Opj4Nyjd21U=";
        AES256Encryption aes = new AES256Encryption();

        String deString = aes.decrypt(pwd, key);
        System.out.println(deString);
    }

}
