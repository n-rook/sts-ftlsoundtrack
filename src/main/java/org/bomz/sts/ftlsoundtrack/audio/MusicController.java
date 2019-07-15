package org.bomz.sts.ftlsoundtrack.audio;

import com.badlogic.gdx.audio.Music;

/**
 * Allows for playing music, stopping it, etc.
 *
 * Note!
 *
 * The way Slay the Spire actually works is there's a "Music Master" class that does all this stuff.
 * To be frank, don't steal this class if you want to play custom music, instead just hook into
 * MusicMaster.
 *
 * But, if you want to do the awesome dual fade thing, you have to use this class.
 */
public class MusicController {

  private static MusicController _instance;
  private MusicSupplier.Song currentSong;
  private Music currentAudio;

  /**
   * Get the singleton instance.
   */
  public static synchronized MusicController instance() {
    if (_instance == null) {
      _instance = new MusicController(MusicSupplier.getInstance());
    }
    return _instance;
  }

  private final MusicSupplier supplier;

  public MusicController(MusicSupplier supplier) {
    this.supplier = supplier;
  }

  /**
   * Play a single song, in both "intense" and "relaxed" settings.
   */
  public void playSingleBGM(MusicSupplier.Song song) {
    Music wasPlaying = currentAudio;

    // TODO consider fading out

    currentAudio = supplier.get(song);
    // TODO volume
    if (wasPlaying != null) {
      wasPlaying.stop();
    }
    currentAudio.play();
    if (wasPlaying != null) {
      wasPlaying.dispose();
    }

  }

  /**
   * Play one song in "intense" settings and the other in "relaxed" settings.
   */
  public void playModalBGM(MusicSupplier.Song relaxed, MusicSupplier.Song intense) {
    // TODO
    this.playSingleBGM(relaxed);
  }

  /**
   * Switch from a "relaxed" to an "intense" setting.
   */
  public void setMode() {
    // TODO
  }

  public enum MusicMode {
    RELAXED,
    INTENSE;
  }
}
