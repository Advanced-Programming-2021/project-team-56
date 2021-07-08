package model.enums;

import java.util.ArrayList;

public enum AvatarURL {

    AKIZA_IZINSKI("/images/Avatars/Akiza-Izinski.png"),
    ALEXIS_RHODE("/images/Avatars/Alexis-Rhode.png"),
    CHAZZ_PRINCETON("/images/Avatars/Chazz-Princeton.png"),
    DUELIST_OF_THE_ROSES("/images/Avatars/Duelist-Of-The-Roses.png"),
    JADEN_YUKI("/images/Avatars/Jaden-Yuki.png"),
    JESSE_ANDERSON("/images/Avatars/Jesse-Anderson.png"),
    JOEY_WHEELER("/images/Avatars/Joey-Wheeler.png"),
    MAXIMILLION_PEGASUS("/images/Avatars/Maximillion-Pegasus.png"),
    SETU_KAIBA("/images/Avatars/Setu-Kaiba.png"),
    YUGI_MUTOU("/images/Avatars/Yugi-Mutou.png");

    public String value;

    AvatarURL(String value) {
        this.value = value;
    }
}
