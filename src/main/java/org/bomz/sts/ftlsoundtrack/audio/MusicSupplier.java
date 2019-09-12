package org.bomz.sts.ftlsoundtrack.audio;

import com.badlogic.gdx.audio.Music;

public class MusicSupplier {

  public enum Song {
    // Note that the names of these songs are used in the serialized configuration files.
    // So don't change them!
    TITLE(Soundtrack.MAIN),
    MILKY_WAY_EXPLORE(Soundtrack.MAIN),
    CIVIL_EXPLORE(Soundtrack.MAIN),
    COSMOS_EXPLORE(Soundtrack.MAIN),
    DEEPSPACE_EXPLORE(Soundtrack.MAIN),
    DEBRIS_EXPLORE(Soundtrack.MAIN),
    MANTIS_EXPLORE(Soundtrack.MAIN),
    ENGI_EXPLORE(Soundtrack.MAIN),
    COLONIAL_EXPLORE(Soundtrack.MAIN),
    WASTELAND_EXPLORE(Soundtrack.MAIN),
    ROCKMEN_EXPLORE(Soundtrack.MAIN),
    VOID_EXPLORE(Soundtrack.MAIN),
    ZOLTAN_EXPLORE(Soundtrack.MAIN),

    FEDERATION_BONUS(Soundtrack.MAIN),

    MILKY_WAY_BATTLE(Soundtrack.MAIN),
    CIVIL_BATTLE(Soundtrack.MAIN),
    COSMOS_BATTLE(Soundtrack.MAIN),
    DEEPSPACE_BATTLE(Soundtrack.MAIN),
    DEBRIS_BATTLE(Soundtrack.MAIN),
    MANTIS_BATTLE(Soundtrack.MAIN),
    ENGI_BATTLE(Soundtrack.MAIN),
    COLONIAL_BATTLE(Soundtrack.MAIN),
    WASTELAND_BATTLE(Soundtrack.MAIN),
    ROCKMEN_BATTLE(Soundtrack.MAIN),
    VOID_BATTLE(Soundtrack.MAIN),
    ZOLTAN_BATTLE(Soundtrack.MAIN),

    LAST_STAND(Soundtrack.MAIN),
    VICTORY(Soundtrack.MAIN),

    LANIUS_BATTLE(Soundtrack.EXPANSION),
    LOST_SHIP_BATTLE(Soundtrack.EXPANSION),
    SLUG_BATTLE(Soundtrack.EXPANSION),
    HACKING_MALFUNCTION_BATTLE(Soundtrack.EXPANSION),

    LANIUS_EXPLORE(Soundtrack.EXPANSION),
    LOST_SHIP_EXPLORE(Soundtrack.EXPANSION),
    SLUG_EXPLORE(Soundtrack.EXPANSION),
    HACKING_MALFUNCTION_EXPLORE(Soundtrack.EXPANSION),
    ;

    private final Soundtrack soundtrack;

    Song(Soundtrack soundtrack) {
      this.soundtrack = soundtrack;
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

  private final MusicFileList musicFiles;
  public MusicSupplier(MusicFileList musicFiles) {
    this.musicFiles = musicFiles;
  }

  /**
   * Load and return a song to play.
   */
  public Music get(Song song) {
    return MusicLoader.loadExternalMusic(musicFiles.get(song));
  }

  /**
   * Load and return a pair of songs.
   */
  public MusicPair get(SongPair pair) {
    return new MusicPair(get(pair.getRelaxed()), get(pair.getIntense()));
  }

  private enum Soundtrack {
    MAIN,
    EXPANSION
  }
}
