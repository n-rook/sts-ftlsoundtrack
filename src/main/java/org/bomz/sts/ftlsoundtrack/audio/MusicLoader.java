package org.bomz.sts.ftlsoundtrack.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.megacrit.cardcrawl.audio.MockMusic;

import java.nio.file.Path;

import static basemod.DevConsole.logger;

public class MusicLoader {
  private static final String FTL_PATH = "C:\\Program Files (x86)\\Steam\\steamapps\\common\\FTL Faster Than Light";

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

//  public void forcePlayMusic(Music m) {
//    m.play();
//  }
//
//  private static String getPath(boolean isExpansion, String path) {
//    if (isExpansion) {
//      throw new RuntimeException("not added yet");
//    }
//    return String.format("%s\\FTL Soundtrack\\%s.mp3", FTL_PATH, path);
//  }
}

