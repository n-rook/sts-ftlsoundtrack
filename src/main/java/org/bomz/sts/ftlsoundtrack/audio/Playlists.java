package org.bomz.sts.ftlsoundtrack.audio;

import org.bomz.sts.ftlsoundtrack.audio.MusicSupplier.Song;
import org.bomz.sts.ftlsoundtrack.audio.MusicSupplier.SongPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Static class containing playlists.
 */
public class Playlists {
  private Playlists() {}

  public static List<SongPair> exordium() {
    return Collections.unmodifiableList(
        Arrays.asList(
            SongPair.CIVIL,
            SongPair.MILKY_WAY,
            SongPair.COSMOS,
            SongPair.ENGI,
            SongPair.LOST_SHIP
        )
    );
  }

  public static List<SongPair> city() {
    return Collections.unmodifiableList(Arrays.asList(
        SongPair.COLONIAL,
        SongPair.MANTIS,
        SongPair.ZOLTAN,
        SongPair.ROCKMEN,
        SongPair.HACKING_MALFUNCTION
    ));
  }

  public static List<SongPair> beyond() {
    return Collections.unmodifiableList(Arrays.asList(
        SongPair.DEBRIS,
        SongPair.DEEPSPACE,
        SongPair.WASTELAND,
        SongPair.VOID,
        SongPair.LANIUS,
        SongPair.SLUG
    ));
  }
}
