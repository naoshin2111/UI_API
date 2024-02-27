package utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

@UtilityClass
public class RandomUtils {

    public static String generateRandomText(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String generateRandomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
}
