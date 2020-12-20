package ru.ifmo.web.JavaEELab4.util;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.security.Key;

@Named("util.KeyGenerator")
@RequestScoped
public class KeyGeneratorImpl implements KeyGenerator {

    @Override
    public Key generateKey() {
        String keyString = "mysuperpassword";
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
    }
}