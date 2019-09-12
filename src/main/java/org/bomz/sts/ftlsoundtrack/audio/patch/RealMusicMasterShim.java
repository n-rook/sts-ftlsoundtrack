package org.bomz.sts.ftlsoundtrack.audio.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import org.bomz.sts.ftlsoundtrack.audio.DualMusicPlayer;
import org.bomz.sts.ftlsoundtrack.audio.MusicSupplier;
import org.bomz.sts.ftlsoundtrack.audio.Playlists;

import static basemod.DevConsole.logger;

class RealMusicMasterShim implements MusicMasterShim {

  private static final String KEY_MENU = "MENU";
  private static final String KEY_EXORDIUM = "Exordium";
  private static final String KEY_CITY = "TheCity";
  private static final String KEY_BEYOND = "TheBeyond";
  private static final String KEY_ENDING = "TheEnding";

  private final DualMusicPlayer player;

  public RealMusicMasterShim(DualMusicPlayer player) {
    this.player = player;
  }

  @Override
  public SpireReturn changeBGM(String key) {
    switch (key) {
      case KEY_MENU:
        player.playSingleBGM(MusicSupplier.Song.TITLE, true);
        break;
      case KEY_EXORDIUM:
        player.playBGMQueue(Playlists.exordium(), DualMusicPlayer.MusicMode.RELAXED);
        break;
      case KEY_CITY:
        player.playBGMQueue(Playlists.city(), DualMusicPlayer.MusicMode.RELAXED);
        break;
      case KEY_BEYOND:
        player.playBGMQueue(Playlists.beyond(), DualMusicPlayer.MusicMode.RELAXED);
        break;
      case KEY_ENDING:
        player.playSingleBGM(MusicSupplier.Song.LAST_STAND, true);
        break;
      default:
        logger.warn("Unknown music key, ignoring " + key);
        return SpireReturn.Return(null);
    }

    return SpireReturn.Return(null);
  }

  @Override
  public SpireReturn playTempBgmInstantlyLoopOverload(String key) {
    if (key.contains("EndingStinger")) {
      // The player won, so playing the victory stinger.
      // You won!
      player.playSingleBGM(MusicSupplier.Song.VICTORY, false);
    }

    return SpireReturn.Return(null);
  }

  @Override
  public SpireReturn update() {
    player.update();
    return SpireReturn.Return(null);
  }

  @Override
  public SpireReturn playTempBGM() {
    return SpireReturn.Return(null);
  }

  @Override
  public SpireReturn playTempBgmInstantly() {
    return SpireReturn.Return(null);
  }

  @Override
  public SpireReturn precacheTempBgm() {
    return SpireReturn.Return(null);
  }

  @Override
  public SpireReturn playPrecachedTempBgm() {
    return SpireReturn.Return(null);
  }
}
