package org.bomz.sts.ftlsoundtrack.audio;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MusicMaster;
import org.bomz.sts.ftlsoundtrack.audio.MusicSupplier.Song;

import static basemod.DevConsole.logger;

public class MusicMasterPatch {

  private static final String KEY_MENU = "MENU";
  private static final String KEY_EXORDIUM = "Exordium";
  private static final String KEY_CITY = "TheCity";
  private static final String KEY_BEYOND = "TheBeyond";
  private static final String KEY_ENDING = "TheEnding";

  @SpirePatch(clz = MusicMaster.class, method = "changeBGM", paramtypez = String.class)
  public static class ChangeBGM {

    @SpirePrefixPatch
    public static SpireReturn changeBGM(MusicMaster __instance, String key) {
      MusicController controller = MusicController.instance();
      switch (key) {
        case KEY_MENU:
          controller.playSingleBGM(Song.TITLE);
          break;
        case KEY_EXORDIUM:
          controller.playModalBGM(Song.CIVIL_EXPLORE, Song.CIVIL_BATTLE);
          break;
        case KEY_CITY:
          controller.playModalBGM(Song.MILKY_WAY_EXPLORE, Song.MILKY_WAY_BATTLE);
          break;
        case KEY_BEYOND:
          controller.playModalBGM(Song.COSMOS_EXPLORE, Song.COSMOS_BATTLE);
          break;
        case KEY_ENDING:
          controller.playSingleBGM(Song.LAST_STAND);
          break;
        default:
          logger.warn("Unknown music key, ignoring " + key);
          return SpireReturn.Return(null);
      }

      return SpireReturn.Return(null);
    }
  }
}
