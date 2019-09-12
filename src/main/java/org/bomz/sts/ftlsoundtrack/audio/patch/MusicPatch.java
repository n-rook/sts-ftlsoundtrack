package org.bomz.sts.ftlsoundtrack.audio.patch;

import org.bomz.sts.ftlsoundtrack.audio.DualMusicPlayer;

import static basemod.DevConsole.logger;

/**
 * This class provides a public interface to patch music.
 */
public class MusicPatch {

  private MusicPatch() {}  // static

  public static void loadRealMusicMaster(DualMusicPlayer player) {
    logger.info("Installing RealMusicMaster");
    MusicMasterPatch.setShim(new RealMusicMasterShim(player));
  }

  public static void loadNoOpMusicMaster() {
    logger.info("Installing NoOpMusicMaster");
    MusicMasterPatch.setShim(new NoOpMusicMasterShim());
  }
}
