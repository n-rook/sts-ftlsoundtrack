package org.bomz.sts.ftlsoundtrack.audio;

import basemod.BaseMod;
import org.bomz.sts.ftlsoundtrack.audio.MusicSupplier.Song;
import org.bomz.sts.ftlsoundtrack.audio.MusicSupplier.SongPair;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static basemod.BaseMod.logger;

/**
 * A class which consists of a mapping between songs and their locations.
 */
public class MusicFileList {
  private static final Map<Song, Pattern> SONG_REGEXES;
  private static final Pattern IS_MUSIC = Pattern.compile(".*\\.mp3$", Pattern.CASE_INSENSITIVE);

  static {
    HashMap<Song, Pattern> regexes = new HashMap<>();
    addSong(regexes, Song.TITLE, "TitleScreen");
    addSong(regexes, Song.LAST_STAND, "LastStand");
    addSong(regexes, Song.VICTORY, "Victory");
    addSong(regexes, Song.FEDERATION_BONUS, "Federation");

    addSongPair(regexes, SongPair.MILKY_WAY, "MilkyWay");
    addSongPair(regexes, SongPair.CIVIL, "Civil");
    addSongPair(regexes, SongPair.COSMOS, "Cosmos");
    addSongPair(regexes, SongPair.DEEPSPACE, "Deepspace");
    addSongPair(regexes, SongPair.DEBRIS, "Debris");
    addSongPair(regexes, SongPair.MANTIS, "Mantis");
    addSongPair(regexes, SongPair.ENGI, "Engi");
    addSongPair(regexes, SongPair.COLONIAL, "Colonial");
    addSongPair(regexes, SongPair.WASTELAND, "Wasteland");
    addSongPair(regexes, SongPair.ROCKMEN, "Rockmen");
    addSongPair(regexes, SongPair.VOID, "Void");
    addSongPair(regexes, SongPair.ZOLTAN, "Zoltan");
    addSongPair(regexes, SongPair.LANIUS, "Lanius");
    addSongPair(regexes, SongPair.LOST_SHIP, "Lost Ship");
    addSongPair(regexes, SongPair.SLUG, "Slug");
    addSongPair(regexes, SongPair.HACKING_MALFUNCTION, "Hacking Malfunction");

    SONG_REGEXES = Collections.unmodifiableMap(regexes);
  }

  private static void addSong(Map<Song, Pattern> regexes, Song song, String name) {
    regexes.put(song, getSongRegex(name));
  }

  private static void addSongPair(Map<Song, Pattern> regexes, SongPair pair, String name) {
    regexes.put(pair.getRelaxed(), getSongRegex(name, false));
    regexes.put(pair.getIntense(), getSongRegex(name, true));
  }

  private static Pattern getSongRegex(String name, boolean intense) {
    String escapedName = Pattern.quote(name);
    String mode = intense ? "battle" : "explore";
    return Pattern.compile(String.format("%s.*%s", escapedName, mode), Pattern.CASE_INSENSITIVE);
  }

  private static Pattern getSongRegex(String name) {
    return Pattern.compile(name, Pattern.CASE_INSENSITIVE);
  }

  /**
   * Initialize a new MusicFileList, finding all the files in this subdirectory.
   *
   * @param subdirectory The full path to a subdirectory.
   * @throws CouldNotFindMusicException if some or all of the files can't be found.
   */
  public static MusicFileList initialize(String subdirectory) throws CouldNotFindMusicException {
    Stream<Path> fileWalk;
    try {
      fileWalk = Files.walk(Paths.get(subdirectory));
    } catch (IOException e) {
      throw new CouldNotFindMusicException(
          "Could not load directory. Check if this directory is correct.",
          subdirectory, e);
    } catch (InvalidPathException e) {
      throw new CouldNotFindMusicException("Failed to load (not a directory)", subdirectory, e);
    }

    HashMap<Song, Path> songFiles = new HashMap<>();

    try {
      fileWalk
          .filter(path -> {
            String filename = path.getFileName().toString();
            if (filename == null) {
              return false;
            }
            return IS_MUSIC.matcher(filename).matches();
          }).forEach(path -> {
        Song song = getMatchingSongOrNull(path);
        if (song == null) {
          return;
        }

        if (!Files.isReadable(path)) {
          logger.warn("Could not read file at path " + path.toString());
          return;
        }

        songFiles.put(song, path);
      });
    } catch (UncheckedIOException e) {
      if (e.getCause() instanceof AccessDeniedException) {
        throw new CouldNotFindMusicException(
            "Access denied; try using a more specific directory.", subdirectory, e.getCause());
      }
      throw new CouldNotFindMusicException("We ran into trouble finding music files", subdirectory, e);
    }

    HashSet<Song> missing = new HashSet<>(Arrays.asList(Song.values()));
    missing.removeAll(songFiles.keySet());
    if (!missing.isEmpty()) {
      if (missing.size() == Song.values().length) {
        throw new CouldNotFindMusicException(
            "Could not find any songs. Make sure the songs are MP3 files and in the given " +
                "directory.", subdirectory);
      }
      // TODO: Add troubleshooting link
      throw new CouldNotFindMusicException(
          String.format(
              "Failed to find %d out of %d songs, including %s. Make sure all songs are present",
              missing.size(), Song.values().length, missing.iterator().next()),
          subdirectory);
    }

    // Note: We already got the music files.
    return new MusicFileList(songFiles);
  }

  /**
   * Initialize a MusicFileList from a list of paths.
   * @throws IOException if a file cannot be read.
   */
  public static MusicFileList initializeFromPaths(Map<Song, Path> files) throws IOException {
    for (Path f: files.values()) {
      if (!Files.isReadable(f)) {
        throw new IOException(String.format("Cannot read file %s", f));
      }
    }
    return new MusicFileList(files);
  }

  private static Song getMatchingSongOrNull(Path path) {
    for (Song song: Song.values()) {
      if (SONG_REGEXES.get(song).matcher(path.getFileName().toString()).find()) {
        return song;
      }
    }
    return null;
  }

  public static class CouldNotFindMusicException extends Exception {

    private static String formatMessage(String message, String directory) {
      return String.format("Failed to load %s\n%s", directory, message);
    }

    CouldNotFindMusicException(String message) {
      super(message);
    }

    CouldNotFindMusicException(String message, String directory) {
      super(formatMessage(message, directory));
    }

    CouldNotFindMusicException(String message, String directory, Throwable cause) {
      super(formatMessage(message, directory), cause);
    }
  }

  private final Map<Song, Path> files;

  public MusicFileList(Map<Song, Path> files) {
    for (Song song: Song.values()) {
      if (!files.containsKey(song)) {
        throw new IllegalArgumentException("Cannot create a MusicFileList missing " + song.name());
      }
    }

    this.files = Collections.unmodifiableMap(
        files.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().toAbsolutePath())));
  }

  /**
   * Get the path of a file. Always returns successfully.
   *
   * Paths in MusicFileList are always absolute.
   */
  public Path get(Song song) {
    return files.get(song);
  }
}
