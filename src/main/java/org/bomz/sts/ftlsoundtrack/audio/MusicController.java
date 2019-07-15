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
 *
 * Notes on FTL's soundtrack:
 *
 * When "new" songs come in, they just play after a short moment of silence.
 */
public class MusicController {

  private static MusicController _instance;
  private MusicBeingPlayed currentAudio;

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
    MusicBeingPlayed newAudio = MusicBeingPlayed.single(supplier.get(song));
    this.stopExistingAudio();
    this.currentAudio = newAudio;
    this.currentAudio.start();
  }

  /**
   * Play one song in "intense" settings and the other in "relaxed" settings.
   */
  public void playModalBGM(MusicSupplier.Song relaxed, MusicSupplier.Song intense) {
    MusicBeingPlayed newAudio =
        MusicBeingPlayed.joint(supplier.get(relaxed), supplier.get(intense));
    this.stopExistingAudio();
    this.currentAudio = newAudio;
    this.currentAudio.start();
  }

  /**
   * Called every frame.
   */
  public void update() {
    if (this.currentAudio != null) {
      this.currentAudio.update();
    }
  }

  private void stopExistingAudio() {
    // TODO: Figure out how to fade out gracefully here
    if (this.currentAudio != null) {
      this.currentAudio.stop();
    }
  }

  /**
   * Switch from a "relaxed" to an "intense" setting.
   */
  public void setMode(MusicMode newMode) {
    if (this.currentAudio != null) {
      this.currentAudio.switchMode(newMode);
    }
  }

  public enum MusicMode {
    RELAXED,
    INTENSE
  }
}
