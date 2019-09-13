package org.bomz.sts.ftlsoundtrack.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.megacrit.cardcrawl.audio.MockMusic;

import java.nio.file.Path;

import static basemod.DevConsole.logger;

public class MusicLoader {

  private static Music loadExternalMusic(String path) {
    if (Gdx.audio == null) {
      logger.info("Gdx.audio is null -- no music can be loaded");
      return new MockMusic();
    }

    logger.info("Loading file " + path);
    return Gdx.audio.newMusic(Gdx.files.absolute(path));
  }

  /**
   * Load music.
   * @param path A path. Must be absolute.
   */
  public static Music loadExternalMusic(Path path) {
    return loadExternalMusic(path.toString());
  }
}
