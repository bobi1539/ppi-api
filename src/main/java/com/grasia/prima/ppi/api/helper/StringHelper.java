package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

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

    public static boolean isContainUpperCaseLetter(String value) {
        for (char c : value.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContainNumber(String value) {
        for (char c : value.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static String createSlug(String value) {
        String normalizedTitle = Normalizer.normalize(value, Normalizer.Form.NFD);
        String slug = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                .matcher(normalizedTitle)
                .replaceAll("");

        slug = slug.replaceAll("[^\\w\\s-]", "").toLowerCase(Locale.ROOT);
        slug = slug.replaceAll("[-\\s]+", "-");
        slug = slug.replaceAll("^-|-$", "");
        return slug;
    }
}
