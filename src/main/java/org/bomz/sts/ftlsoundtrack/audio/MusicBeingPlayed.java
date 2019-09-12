package org.bomz.sts.ftlsoundtrack.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;

/**
 * A song being played right now.
 *
 * This is the equivalent of the built-in class "MainMusic".
 */
public class MusicBeingPlayed {

  /**
   * If a single song is being played, that song.
   *
   * If more than one song is being played, the "relaxed" version of that song.
   */
  private final Music relaxed;

  /**
   * If a single song is being played, null.
   *
   * If more than one song is being played, the "intense" version of that song.
   */
  private final Music intense;

  // The current mode. If crossfading, this is the new mode.
  private DualMusicPlayer.MusicMode currentMode;

  // Game volume.
  private float volume;

  // How far we are into the crossfade.
  private float crossFadeTimer = 0.0F;

  // How long the crossfade should last in seconds.
  private static final float CROSS_FADE_LENGTH = 2.0F;

  private MusicBeingPlayed(Music relaxedMusic, Music intenseMusic) {
    this.relaxed = relaxedMusic;
    this.intense = intenseMusic;
    this.volume = getGameVolume();
    this.currentMode = DualMusicPlayer.MusicMode.RELAXED;
  }

  public static MusicBeingPlayed single(Music music) {
    return new MusicBeingPlayed(music, null);
  }

  public static MusicBeingPlayed joint(Music relaxed, Music intense) {
    return new MusicBeingPlayed(relaxed, intense);
  }

  public static MusicBeingPlayed joint(MusicSupplier.MusicPair pair) {
    return new MusicBeingPlayed(pair.getRelaxed(), pair.getIntense());
  }

  private static float getGameVolume() {
    return Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME;
  }

  /**
   * Get the current mode.
   *
   * Notes:
   * 1. If this is a single-mode song, the current mode is always RELAXED.
   * 2. If we are transitioning to a new mode, the current mode is always the DESTINATION MODE.
   */
  public DualMusicPlayer.MusicMode getCurrentMode() {
    return currentMode;
  }

  public void start() {
    start(DualMusicPlayer.MusicMode.RELAXED);
  }

  public void start(DualMusicPlayer.MusicMode mode) {
    // TODO: fade in
    if (intense == null) {
      relaxed.setVolume(volume);
      relaxed.play();
      return;
    }

    Music active = mode == DualMusicPlayer.MusicMode.RELAXED ? relaxed : intense;
    Music inactive = mode == DualMusicPlayer.MusicMode.RELAXED ? intense : relaxed;

    this.currentMode = mode;

    active.setVolume(volume);
    inactive.setVolume(0.0F);
    active.play();
    inactive.play();
  }

  public void stop() {
    // TODO: fade out
    relaxed.stop();
    if (intense != null) {
      intense.stop();
    }
  }

  public void dispose() {
    relaxed.dispose();
    if (intense != null) {
      intense.dispose();
    }
  }

  /**
   * Switch to a new mode, fading in and out if necessary.
   *
   * If this song only has one mode, or if we are already in the desired mode, does nothing.
   */
  public void switchMode(DualMusicPlayer.MusicMode newMode) {
    if (this.intense == null || this.currentMode == newMode) {
      return;
    }

    this.currentMode = newMode;
    this.crossFadeTimer = CROSS_FADE_LENGTH;
  }

  /**
   * Set whether this song should loop.
   */
  public void setLooping(boolean shouldLoop) {
    relaxed.setLooping(shouldLoop);
    if (this.intense != null) {
      intense.setLooping(shouldLoop);
    }
  }

  /**
   * Set an OnCompletionListener.
   *
   * Note that this is implemented by setting the listener on the relaxed song. If the intense song
   * is longer or shorter this is going to wind up being really weird. But it doesn't make sense
   * for the two songs to be different lengths to begin with, so whatever :)
   */
  public void setOnCompletionListener(Music.OnCompletionListener listener) {
    relaxed.setOnCompletionListener(listener);
  }

  // Called most frames. Updates music effects.
  public void update() {
    if (crossFadeTimer == 0.0F) {
      return;  // no crossfade
    }

    crossFadeTimer -= Gdx.graphics.getDeltaTime();
    if (crossFadeTimer < 0.0F) {
      crossFadeTimer = 0.0F;
    }

    // 0.0F when fade begins, 1.0F when it ends.
    float fadeProportion = (1.0F - (crossFadeTimer / CROSS_FADE_LENGTH));
    float fadingInVolume = Interpolation.fade.apply(
        0.0F, this.volume, fadeProportion);
    float fadingOutVolume = Interpolation.fade.apply(
        this.volume, 0.0F, fadeProportion);

    if (this.currentMode == DualMusicPlayer.MusicMode.RELAXED) {
      this.relaxed.setVolume(fadingInVolume);
      this.intense.setVolume(fadingOutVolume);
    } else {
      this.relaxed.setVolume(fadingOutVolume);
      this.intense.setVolume(fadingInVolume);
    }
  }
}
