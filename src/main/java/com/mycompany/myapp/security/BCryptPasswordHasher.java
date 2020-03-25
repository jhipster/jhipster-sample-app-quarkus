package com.mycompany.myapp.security;

import org.wildfly.security.credential.PasswordCredential;
import org.wildfly.security.evidence.PasswordGuessEvidence;
import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.WildFlyElytronPasswordProvider;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.spec.EncryptablePasswordSpec;
import org.wildfly.security.password.spec.IteratedSaltedPasswordAlgorithmSpec;
import org.wildfly.security.password.util.ModularCrypt;

import javax.enterprise.context.ApplicationScoped;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

@ApplicationScoped
public class BCryptPasswordHasher {

    private static final WildFlyElytronPasswordProvider provider = new WildFlyElytronPasswordProvider();
    public static final int DEFAULT_ITERATION_COUNT = 10;

    private final int iterationCount;
    private final SecureRandom random;

    public BCryptPasswordHasher() {
        this(DEFAULT_ITERATION_COUNT);
    }

    public BCryptPasswordHasher(int iterationCount) {
        this(iterationCount, null);
    }

    public BCryptPasswordHasher(int iterationCount, SecureRandom random) {
        this.iterationCount = iterationCount;
        this.random = random;
    }

    public boolean checkPassword(String plaintextPassword, String hashedPassword) {
        Objects.requireNonNull(plaintextPassword, "plaintext password is required");
        Objects.requireNonNull(hashedPassword, "hashed password is required");
        PasswordGuessEvidence evidence = new PasswordGuessEvidence(plaintextPassword.toCharArray());
        PasswordCredential credential = new PasswordCredential(decode(hashedPassword));
        return credential.verify(evidence);
    }

    private Password decode(String password) {
        try {
            return ModularCrypt.decode(password);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public String hash(String password) {
        Objects.requireNonNull(password, "password is required");

        if (iterationCount <= 0)
            throw new IllegalArgumentException("Iteration count must be greater than zero");

        byte[] salt = new byte[BCryptPassword.BCRYPT_SALT_SIZE];
        if (random != null) {
            random.nextBytes(salt);
        } else {
            new SecureRandom().nextBytes(salt);
        }

        PasswordFactory passwordFactory;
        try {
            passwordFactory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT, provider);
        } catch (NoSuchAlgorithmException e) {
            // can't really happen
            throw new RuntimeException(e);
        }

        IteratedSaltedPasswordAlgorithmSpec iteratedAlgorithmSpec = new IteratedSaltedPasswordAlgorithmSpec(iterationCount,
            salt);
        EncryptablePasswordSpec encryptableSpec = new EncryptablePasswordSpec(password.toCharArray(), iteratedAlgorithmSpec);

        try {
            BCryptPassword original = (BCryptPassword) passwordFactory.generatePassword(encryptableSpec);
            return ModularCrypt.encodeAsString(original);
        } catch (InvalidKeySpecException e) {
            // can't really happen
            throw new RuntimeException(e);
        }
    }


}
