package org.bomz.sts.ftlsoundtrack;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import org.bomz.sts.ftlsoundtrack.audio.MusicFileList;
import org.bomz.sts.ftlsoundtrack.audio.MusicScanners;
import org.bomz.sts.ftlsoundtrack.audio.MusicSupplier;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;

import static basemod.DevConsole.logger;

/**
 * A singleton containing current settings.
 */
public class ModSettings {

  private static final String CONFIG_DIRECTORY = "ftlsoundtrack";
  private static final String CONFIG_FILE = "songs";
  private static ModSettings instance = null;

  /**
   * Initialize mod settings.
   */
  public static void initialize() {
    instance = new ModSettings();
  }

  public static synchronized ModSettings getInstance() {
    if (instance == null) {
      throw new RuntimeException("ModSettings not yet initialized");
    }
    return instance;
  }

  // Current music files. Null if no music files are successfully seen.
  private MusicFileList songs;

  // SpireConfig schema:
  // If songs are loaded, the config consists of a series of properties mapping the name of every
  // song to its canonical path.
  // If songs are not loaded, the config is empty.
  private SpireConfig config;

  private ModSettings() {
    try {
      config = new SpireConfig(CONFIG_DIRECTORY, CONFIG_FILE);
    } catch (IOException e) {
      throw new RuntimeException("Could not open configuration file", e);
    }
  }

  private void checkLoaded() {
    if (songs == null) {
      throw new IllegalStateException("Song files not loaded yet!");
    }
  }

  private boolean configHasSongPaths() {
    // We only save song paths to the configuration if we know where every path is.
    // As such, the presence of a single song path serves to indicate if all songs are located.
    return config.has(MusicSupplier.Song.CIVIL_BATTLE.name());
  }

  /**
   * Load data from SpireConfig.
   */
  public void load() {
    try {
      if (configHasSongPaths()) {
        EnumMap<MusicSupplier.Song, Path> songFiles = new EnumMap<>(MusicSupplier.Song.class);
        for (MusicSupplier.Song s: MusicSupplier.Song.values()) {
          songFiles.put(s, Paths.get(config.getString(s.name())));
        }

        this.songs = MusicFileList.initializeFromPaths(songFiles);
      }
    } catch (IOException e) {
      throw new RuntimeException("Could not load saved configuration", e);
    }
  }

  /**
   * If we don't successfully load files, we try to scan the Steam Library for the soundtrack.
   * It seems like the most likely place, after all.
   */
  public void tryFirstTimeScan() {
    MusicFileList files;
    try {
      files = MusicScanners.scanSteamLibrary();
    } catch (MusicFileList.CouldNotFindMusicException e) {
      logger.warn(
          "First time Steam Library scan failed; you'll have to set up the scan manually", e);
      return;
    }

    logger.info("First-time Steam Library scan succeeded!");
    this.save(files);
  }

  /**
   * Save data to SpireConfig.
   */
  public void save(MusicFileList newSongs) {
    songs = newSongs;

    try {
      for (MusicSupplier.Song s : MusicSupplier.Song.values()) {
        config.setString(s.name(), songs.get(s).toString());
      }
      config.save();
    } catch (IOException e) {
      throw new RuntimeException("Error saving configuration", e);
    }
  }

  /**
   * Clear saved data.
   */
  public void clear() {
    songs = null;
    try {
      config.clear();
      config.save();
    } catch (IOException e) {
      throw new RuntimeException("Error clearing saved configuration", e);
    }
  }

  public boolean areSongsLoaded() {
    return songs != null;
  }

  /**
   * Returns the currently loaded songs. Throws if no songs loaded.
   */
  public MusicFileList getSongs() {
    if (songs == null) {
      throw new RuntimeException("No songs loaded yet!");
    }
    return songs;
  }
}
