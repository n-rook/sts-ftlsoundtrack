package org.bomz.sts.ftlsoundtrack.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.megacrit.cardcrawl.audio.MockMusic;

import static basemod.DevConsole.logger;

public class MusicLoader {
  private static final String FTL_PATH = "C:\\Program Files (x86)\\Steam\\steamapps\\common\\FTL Faster Than Light";

  public static Music loadExternalMusic(String path) {
    if (Gdx.audio == null) {
      logger.info("Gdx.audio is null -- no music can be loaded");
      return new MockMusic();
    }

    return Gdx.audio.newMusic(Gdx.files.absolute(path));
//    return Gdx.audio.newMusic(Gdx.files.absolute(getPath(false, "bp_FTL_01_TitleScreen")));
  }


  public void forcePlayMusic(Music m) {
    m.play();
  }

  private static String getPath(boolean isExpansion, String path) {
    if (isExpansion) {
      throw new RuntimeException("not added yet");
    }
    return String.format("%s\\FTL Soundtrack\\%s.mp3", FTL_PATH, path);
  }
}

