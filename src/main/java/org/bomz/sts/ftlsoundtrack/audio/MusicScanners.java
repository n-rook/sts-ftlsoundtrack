package org.bomz.sts.ftlsoundtrack.audio;

import basemod.BaseMod;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static basemod.BaseMod.logger;

public class MusicScanners {
  // static
  private MusicScanners() {}

  private static final String SLAY_THE_SPIRE_DIRECTORY = "SlayTheSpire";

  public static MusicFileList scanMusicLibrary() throws MusicFileList.CouldNotFindMusicException {
    String home = System.getProperty("user.home");

    if (home == null) {
      throw new MusicFileList.CouldNotFindMusicException(
          "Could not find home directory. Try loading from the clipboard.");
    }

    logger.info(String.format("Home directory is %s", home));
    Path musicDirectory = Paths.get(home).resolve("Music");

    return MusicFileList.initialize(musicDirectory);
  }

  private static Path tryGetStsRoot() {
    String stsRootString = System.getProperty("user.dir");
    if (stsRootString == null) {
      return null;
    }

    // On Windows stsRootString is just what we want. On a Mac, though, it's
    // something like .../Steam/steamapps/common/SlayTheSpire/SlayTheSpire.app/Contents.
    // So we go up the directory tree until we find "SlayTheSpire".
    Path stsRoot = Paths.get(stsRootString).toAbsolutePath();
    for (
        Path current = stsRoot;
        current != null;
        current = current.getParent()) {
      if (current.getFileName().toString().equalsIgnoreCase(SLAY_THE_SPIRE_DIRECTORY)) {
        return current;
      }
    }

    logger.warn("Could not find SlayTheSpire in ancestry of {}", stsRootString);
    return null;
  }

  public static MusicFileList scanSteamLibrary() throws MusicFileList.CouldNotFindMusicException {
    String stsRoot = System.getProperty("user.dir");

    if (stsRoot == null) {
      throw new MusicFileList.CouldNotFindMusicException(
          "Could not find Slay the Spire root directory. Try loading from the clipboard.");
    }
    logger.info(String.format("STS root is %s", stsRoot));
    Path ftlPath = Paths.get(stsRoot).getParent().resolve("FTL Faster Than Light").normalize();
    logger.info(String.format("Expected FTL path is %s", ftlPath.toString()));

    return MusicFileList.initialize(ftlPath);
  }

  public static MusicFileList scanClipboard() throws MusicFileList.CouldNotFindMusicException {
    String clipboard;

    try {
      clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
          .getData(DataFlavor.stringFlavor);
    } catch (IOException e) {
      throw new MusicFileList.CouldNotFindMusicException("Could not access clipboard");
    } catch (UnsupportedFlavorException e) {
      throw new MusicFileList.CouldNotFindMusicException("The clipboard does not contain text.");
    }

    logger.info(String.format("Path in clipboard is %s", clipboard));
    return MusicFileList.initialize(clipboard);
  }
}
