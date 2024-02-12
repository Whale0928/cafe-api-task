package app.practice.cafeapitask.global.util.sound;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InitialSoundTest {

    @Test
    @DisplayName("초성으로만 구성되어 있는지 확인할수 있다.")
    void testIsAlreadyInitialSound() {
        assertTrue(InitialSound.isAlreadyInitialSound("ㄱㄴㄷ"), "초성만 있는 경우");
        assertFalse(InitialSound.isAlreadyInitialSound("가나다"), "초성이 아닌 경우");
        assertTrue(InitialSound.isAlreadyInitialSound("ㅋㄹ"), "초성만 있는 경우");
        assertFalse(InitialSound.isAlreadyInitialSound(null), "입력이 null인 경우");
    }

    @Test
    @DisplayName("단어를 초성으로 변환할 수 있다.")
    void testGetInitialSound() {
        assertEquals("ㅋㄹㅍㅅㅌ", InitialSound.getInitialSound("크림파스타"), "크림파스타의 초성 변환");
        assertEquals("ㄱㄴㄷ", InitialSound.getInitialSound("가나다"), "가나다의 초성 변환");
        assertEquals("", InitialSound.getInitialSound(null), "입력이 null인 경우");
    }

    @Test
    @DisplayName("두 문자열의 초성이 일치하는지 비교할 수 잇다.")
    void testMatchInitialSound() {
        assertTrue(InitialSound.matchInitialSound("크림파스타", "ㅋㄹㅍㅅㅌ"), "일치하는 초성");
        assertFalse(InitialSound.matchInitialSound("크림파스타", "ㄱㄴㄷ"), "일치하지 않는 초성");
        assertTrue(InitialSound.matchInitialSound("크림파스타", "크림"), "일치하는 문자열");
        assertTrue(InitialSound.matchInitialSound("크림파스타", "파스타"), "일치하는 문자열");
        assertFalse(InitialSound.matchInitialSound("크림파스타", null), "타겟 문자열이 null인 경우");
        assertFalse(InitialSound.matchInitialSound(null, "ㅋㄹ"), "입력 문자열이 null인 경우");
    }
}