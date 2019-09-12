package org.bomz.sts.ftlsoundtrack.audio.patch;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MusicMaster;

public class MusicMasterPatch {

  // Actual music class to use.
  // If MusicMasterPatch is loaded, we expect setShim will be called before any of these
  // music methods are called! Otherwise we throw an error.
  private static MusicMasterShim _shim = null;

  private static final String KEY_MENU = "MENU";
  private static final String KEY_EXORDIUM = "Exordium";
  private static final String KEY_CITY = "TheCity";
  private static final String KEY_BEYOND = "TheBeyond";
  private static final String KEY_ENDING = "TheEnding";

  static void setShim(MusicMasterShim shim) {
    MusicMasterPatch._shim = shim;
  }

  static MusicMasterShim shim() {
    if (_shim == null) {
      throw new RuntimeException("MusicMasterShim not initialized. This is a bug");
    }
    return _shim;
  }

  @SpirePatch(clz = MusicMaster.class, method = "changeBGM", paramtypez = String.class)
  public static class ChangeBGM {

    @SpirePrefixPatch
    public static SpireReturn changeBGM(MusicMaster __instance, String key) {
      return shim().changeBGM(key);
    }
  }

  @SpirePatch(clz = MusicMaster.class, method = "playTempBgmInstantly", paramtypez = {
      String.class, boolean.class
  } )
  public static class PlayTempBgmInstantlyLoopOverload {
    @SpirePrefixPatch
    public static SpireReturn playTempBgmInstantly(MusicMaster __instance, String key, boolean loop) {
      return shim().playTempBgmInstantlyLoopOverload(key);
    }
  }

  @SpirePatch(clz = MusicMaster.class, method = "update")
  public static class Update {
    @SpirePrefixPatch
    public static SpireReturn update(MusicMaster __instance) {
      return shim().update();
    }
  }

  // Ignore all temp BGM in this mod. Sorry folks.
  @SpirePatch(clz = MusicMaster.class, method = "playTempBGM", paramtypez = String.class)
  public static class PlayTempBGM {
    @SpirePrefixPatch
    public static SpireReturn playTempBGM(MusicMaster __instance) {
      return shim().playTempBGM();
    }
  }

  @SpirePatch(clz = MusicMaster.class, method = "playTempBgmInstantly", paramtypez = String.class)
  public static class PlayTempBgmInstantly {
    @SpirePrefixPatch
    public static SpireReturn playTempBgmInstantly(MusicMaster __instance) {
      return shim().playTempBgmInstantly();
    }
  }

  @SpirePatch(clz = MusicMaster.class, method = "precacheTempBgm", paramtypez = String.class)
  public static class PrecacheTempBgm {
    @SpirePrefixPatch
    public static SpireReturn precacheTempBgm(MusicMaster __instance) {
      return shim().precacheTempBgm();
    }
  }

  @SpirePatch(clz = MusicMaster.class, method = "playPrecachedTempBgm")
  public static class PlayPrecachedTempBgm {
    @SpirePrefixPatch
    public static SpireReturn playPrecachedTempBgm(MusicMaster __instance) {
      return shim().playPrecachedTempBgm();
    }
  }
}
