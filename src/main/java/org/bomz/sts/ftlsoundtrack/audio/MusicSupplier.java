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
    CIVIL_EXPLORE(Soundtrack.MAIN, "bp_FTL_03_CivilEXPLORE"),
    COSMOS_EXPLORE(Soundtrack.MAIN, "bp_FTL_04_CosmosEXPLORE"),
    DEEPSPACE_EXPLORE(Soundtrack.MAIN, "bp_FTL_05_DeepspaceEXPLORE"),
    DEBRIS_EXPLORE(Soundtrack.MAIN, "bp_FTL_06_DebrisEXPLORE"),
    MANTIS_EXPLORE(Soundtrack.MAIN, "bp_FTL_07_MantisEXPLORE"),
    ENGI_EXPLORE(Soundtrack.MAIN, "bp_FTL_08_EngiEXPLORE"),
    COLONIAL_EXPLORE(Soundtrack.MAIN, "bp_FTL_09_ColonialEXPLORE"),
    WASTELAND_EXPLORE(Soundtrack.MAIN, "bp_FTL_10_WastelandEXPLORE"),
    ROCKMEN_EXPLORE(Soundtrack.MAIN, "bp_FTL_11_RockmenEXPLORE"),
    VOID_EXPLORE(Soundtrack.MAIN, "bp_FTL_12_VoidEXPLORE"),
    ZOLTAN_EXPLORE(Soundtrack.MAIN, "bp_FTL_13_ZoltanEXPLORE"),

    FEDERATION_BONUS(Soundtrack.MAIN, "bp_FTL_14_BONUS_Federation"),

    MILKY_WAY_BATTLE(Soundtrack.MAIN, "bp_FTL_15_MilkyWayBATTLE"),
    CIVIL_BATTLE(Soundtrack.MAIN, "bp_FTL_16_CivilBATTLE"),
    COSMOS_BATTLE(Soundtrack.MAIN, "bp_FTL_17_CosmosBATTLE"),
    DEEPSPACE_BATTLE(Soundtrack.MAIN, "bp_FTL_18_DeepspaceBATTLE"),
    DEBRIS_BATTLE(Soundtrack.MAIN, "bp_FTL_19_DebrisBATTLE"),
    MANTIS_BATTLE(Soundtrack.MAIN, "bp_FTL_20_MantisBATTLE"),
    ENGI_BATTLE(Soundtrack.MAIN, "bp_FTL_21_EngiBATTLE"),
    COLONIAL_BATTLE(Soundtrack.MAIN, "bp_FTL_22_ColonialBATTLE"),
    WASTELAND_BATTLE(Soundtrack.MAIN, "bp_FTL_23_WastelandBATTLE"),
    ROCKMEN_BATTLE(Soundtrack.MAIN, "bp_FTL_24_RockmenBATTLE"),
    VOID_BATTLE(Soundtrack.MAIN, "bp_FTL_25_VoidBATTLE"),
    ZOLTAN_BATTLE(Soundtrack.MAIN, "bp_FTL_26_ZoltanBATTLE"),

    LAST_STAND(Soundtrack.MAIN, "bp_FTL_27_LastStand"),
    VICTORY(Soundtrack.MAIN, "bp_FTL_28_Victory"),

    LANIUS_BATTLE(Soundtrack.EXPANSION, "Lanius (Battle)"),
    LOST_SHIP_BATTLE(Soundtrack.EXPANSION, "Lost Ship (Battle)"),
    SLUG_BATTLE(Soundtrack.EXPANSION, "Slug (Battle)"),
    HACKING_MALFUNCTION_BATTLE(Soundtrack.EXPANSION, "Hacking Malfunction (Battle)"),

    LANIUS_EXPLORE(Soundtrack.EXPANSION, "Lanius (Explore)"),
    LOST_SHIP_EXPLORE(Soundtrack.EXPANSION, "Lost Ship (Explore)"),
    SLUG_EXPLORE(Soundtrack.EXPANSION, "Slug (Explore)"),
    HACKING_MALFUNCTION_EXPLORE(Soundtrack.EXPANSION, "Hacking Malfunction (Explore)"),
    ;

    private final Soundtrack soundtrack;
    private final String path;
    Song(Soundtrack soundtrack, String path) {
      this.soundtrack = soundtrack;
      this.path = path;
    }

    private String getPath() {
      return String.format("%s\\%s\\%s.mp3", FTL_PATH, soundtrack.getSubdirectory(), path);
    }
  }

  /**
   * A pair of songs intended to be played in concert.
   *
   * In general, the two fade into each other in various situations.
   */
  public enum SongPair {
    MILKY_WAY(Song.MILKY_WAY_EXPLORE, Song.MILKY_WAY_BATTLE),
    CIVIL(Song.CIVIL_EXPLORE, Song.CIVIL_BATTLE),
    COSMOS(Song.COSMOS_EXPLORE, Song.COSMOS_BATTLE),
    DEEPSPACE(Song.DEEPSPACE_EXPLORE, Song.DEEPSPACE_BATTLE),
    DEBRIS(Song.DEBRIS_EXPLORE, Song.DEBRIS_BATTLE),
    MANTIS(Song.MANTIS_EXPLORE, Song.MANTIS_BATTLE),
    ENGI(Song.ENGI_EXPLORE, Song.ENGI_BATTLE),
    COLONIAL(Song.COLONIAL_EXPLORE, Song.COLONIAL_BATTLE),
    WASTELAND(Song.WASTELAND_EXPLORE, Song.WASTELAND_BATTLE),
    ROCKMEN(Song.ROCKMEN_EXPLORE, Song.ROCKMEN_BATTLE),
    VOID(Song.VOID_EXPLORE, Song.VOID_BATTLE),
    ZOLTAN(Song.ZOLTAN_EXPLORE, Song.ZOLTAN_BATTLE),
    LANIUS(Song.LANIUS_EXPLORE, Song.LANIUS_BATTLE),
    LOST_SHIP(Song.LOST_SHIP_EXPLORE, Song.LOST_SHIP_BATTLE),
    SLUG(Song.SLUG_EXPLORE, Song.SLUG_BATTLE),
    HACKING_MALFUNCTION(Song.HACKING_MALFUNCTION_EXPLORE, Song.HACKING_MALFUNCTION_BATTLE)
    ;

    private final Song relaxed;
    private final Song intense;

    SongPair(Song relaxed, Song intense) {
      this.relaxed = relaxed;
      this.intense = intense;
    }

    public Song getRelaxed() {
      return relaxed;
    }

    public Song getIntense() {
      return intense;
    }
  }

  public static class MusicPair {
    private final Music relaxed;
    private final Music intense;

    public MusicPair(Music relaxed, Music intense) {
      this.relaxed = relaxed;
      this.intense = intense;
    }


    public Music getRelaxed() {
      return relaxed;
    }

    public Music getIntense() {
      return intense;
    }
  }

  /**
   * Start streaming a song.
   */
  public Music get(Song song) {
    logger.info("Loading song " + song.getPath());
    return MusicLoader.loadExternalMusic(song.getPath());
  }

  /**
   * Start streaming a pair of songs.
   */
  public MusicPair get(SongPair pair) {
    return new MusicPair(get(pair.getRelaxed()), get(pair.getIntense()));
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
