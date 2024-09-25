package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Objects;

public final class StringHelper {

    private StringHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static boolean isEmpty(String string) {
        return Objects.isNull(string) || string.isEmpty();
    }

    public static String queryLike(String string) {
        return MessageFormat.format("%{0}%", string);
    }

    public static String random() {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int length = 20;
        final SecureRandom random = new SecureRandom();

        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }
}
