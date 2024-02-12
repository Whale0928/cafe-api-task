package app.practice.cafeapitask.global.util.sound;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class InitialSound {
    private final static String[] CHO = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};


    /**
     * 이미 초성인지 확인한다.
     *
     * @param input the input
     * @return the boolean
     */
    public static boolean isAlreadyInitialSound(String input) {
        if (input == null)
            return false;
        //한글 초성의 범위 0x3131(ㄱ) ~ 0x314E(ㅎ)
        return input.chars().allMatch(c -> c >= 0x3131 && c <= 0x314E);
    }

    /**
     * 문자열을 받아 초성으로 변환한다.
     *
     * @param word the word
     * @return the initial sound
     */
    public static String getInitialSound(String word) {
        if (word == null)
            return "";

        log.info("초성으로 변환합니다 : {}", word);
        return Arrays.stream(word.split("")).map(x -> {
            char cho = (char) ((x.charAt(0) - 0xAC00) / 28 / 21);
            return ((int) cho > 19 || (int) cho < 0) ? "" : CHO[cho];
        }).collect(Collectors.joining(""));
    }

    /**
     * 문자열을 받아 초성인지 아닌지 여부를 확인 후 두 문자열을 비교한다.
     *
     * @param name   the name
     * @param target the target
     * @return the boolean
     */
    public static boolean matchInitialSound(String name, String target) {
        if (name == null || target == null)
            return false;

        if (isAlreadyInitialSound(target)) {
            log.info("문자열로 비교합니다 : {}({}) {}({})", getInitialSound(name), name, target, getInitialSound(target));
            String initialSound = getInitialSound(name);
            return initialSound.contains(target);
        } else {
            log.info("초성으로 비교합니다 : {}({}) {}({})", getInitialSound(name), name, target, getInitialSound(target));
            String initialSound = getInitialSound(name);
            String targetInitialSound = getInitialSound(target);
            return initialSound.contains(targetInitialSound);
        }
    }
}
