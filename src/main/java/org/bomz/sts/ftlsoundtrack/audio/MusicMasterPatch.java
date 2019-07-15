package org.bomz.sts.ftlsoundtrack.audio;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MusicMaster;
import org.bomz.sts.ftlsoundtrack.audio.MusicController.MusicMode;
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
          controller.playSingleBGM(Song.TITLE, true);
          break;
        case KEY_EXORDIUM:
          controller.playBGMQueue(Playlists.exordium(), MusicMode.RELAXED);
          break;
        case KEY_CITY:
          controller.playBGMQueue(Playlists.city(), MusicMode.RELAXED);
          break;
        case KEY_BEYOND:
          controller.playBGMQueue(Playlists.beyond(), MusicMode.RELAXED);
          break;
        case KEY_ENDING:
          controller.playSingleBGM(Song.LAST_STAND, true);
          break;
        default:
          logger.warn("Unknown music key, ignoring " + key);
          return SpireReturn.Return(null);
      }

      return SpireReturn.Return(null);
    }
  }


  @SpirePatch(clz = MusicMaster.class, method = "playTempBgmInstantly", paramtypez = {
      String.class, boolean.class
  } )
  public static class PlayTempBgmInstantlyLoopOverload {
    @SpirePrefixPatch
    public static SpireReturn playTempBgmInstantly(MusicMaster __instance, String key, boolean loop) {
      if (key.contains("EndingStinger")) {
        // The player won, so playing the victory stinger.
        // You won!
        MusicController.instance().playSingleBGM(Song.VICTORY, false);
      }

      return SpireReturn.Return(null);
    }
  }

  @SpirePatch(clz = MusicMaster.class, method = "update")
  public static class Update {
    @SpirePrefixPatch
    public static void update(MusicMaster __instance) {
      MusicController.instance().update();
    }
  }

  // Ignore all temp BGM in this mod. Sorry folks.
  @SpirePatch(clz = MusicMaster.class, method = "playTempBGM", paramtypez = String.class)
  public static class PlayTempBGM {
    @SpirePrefixPatch
    public static SpireReturn playTempBGM(MusicMaster __instance) {
      return SpireReturn.Return(null);
    }
  }

  @SpirePatch(clz = MusicMaster.class, method = "playTempBgmInstantly", paramtypez = String.class)
  public static class PlayTempBgmInstantly {
    @SpirePrefixPatch
    public static SpireReturn playTempBgmInstantly(MusicMaster __instance) {
      return SpireReturn.Return(null);
    }
  }

  @SpirePatch(clz = MusicMaster.class, method = "precacheTempBgm", paramtypez = String.class)
  public static class PrecacheTempBgm {
    @SpirePrefixPatch
    public static SpireReturn precacheTempBgm(MusicMaster __instance) {
      return SpireReturn.Return(null);
    }
  }

  @SpirePatch(clz = MusicMaster.class, method = "playPrecachedTempBgm")
  public static class PlayPrecachedTempBgm {
    @SpirePrefixPatch
    public static SpireReturn playPrecachedTempBgm(MusicMaster __instance) {
      return SpireReturn.Return(null);
    }
  }
}
