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
