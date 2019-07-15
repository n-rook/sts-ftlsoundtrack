package org.bomz.sts.ftlsoundtrack.audio;

import com.badlogic.gdx.audio.Music;

import static basemod.DevConsole.logger;

public class MusicSupplier {
  private static final String FTL_PATH =
      "C:\\Program Files (x86)\\Steam\\steamapps\\common\\FTL Faster Than Light";

  private static MusicSupplier instance = null;
  public static synchronized MusicSupplier getInstance() {
    if (instance == null) {
      instance = new MusicSupplier();
    }
    return instance;
  }

  public enum Song {
    TITLE(Soundtrack.MAIN, "bp_FTL_01_TitleScreen"),
    MILKY_WAY_EXPLORE(Soundtrack.MAIN, "bp_FTL_02_MilkyWayEXPLORE"),
    MILKY_WAY_BATTLE(Soundtrack.MAIN, "bp_FTL_15_MilkyWayBATTLE"),
    CIVIL_EXPLORE(Soundtrack.MAIN, "bp_FTL_03_CivilEXPLORE"),
    CIVIL_BATTLE(Soundtrack.MAIN, "bp_FTL_16_CivilBATTLE"),
    COSMOS_EXPLORE(Soundtrack.MAIN, "bp_FTL_04_CosmosEXPLORE"),
    COSMOS_BATTLE(Soundtrack.MAIN, "bp_FTL_17_CosmosBATTLE"),

    LAST_STAND(Soundtrack.MAIN, "bp_FTL_27_LastStand"),
    VICTORY(Soundtrack.MAIN, "bp_FTL_28_Victory")
    ;

    private Soundtrack soundtrack;
    private String path;
    private Song(Soundtrack soundtrack, String path) {
      this.soundtrack = soundtrack;
      this.path = path;
    }

    private String getPath() {
      return String.format("%s\\%s\\%s.mp3", FTL_PATH, soundtrack.getSubdirectory(), path);
    }
  }

  public Music get(Song song) {
    logger.info("Loading song " + song.getPath());
    return MusicLoader.loadExternalMusic(song.getPath());
  }

  public void playMusic(Song song) {
    Music music = MusicLoader.loadExternalMusic(song.getPath());
    music.play();
  }

  private enum Soundtrack {
    MAIN("FTL Soundtrack"),
    EXPANSION("FTL AE Soundtrack");

    private String subdirectory;
    Soundtrack(String subdirectory) {
      this.subdirectory = subdirectory;
    }

    private String getSubdirectory() {
      return this.subdirectory;
    }
  }


  private static String getPath(boolean isExpansion, String path) {
    if (isExpansion) {
      throw new RuntimeException("not added yet");
    }
    return String.format("%s\\FTL Soundtrack\\%s.mp3", FTL_PATH, path);
  }
}
