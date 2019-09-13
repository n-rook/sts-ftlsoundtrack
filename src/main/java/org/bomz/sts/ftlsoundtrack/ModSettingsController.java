package org.bomz.sts.ftlsoundtrack;

import basemod.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.bomz.sts.ftlsoundtrack.audio.MusicFileList;
import org.bomz.sts.ftlsoundtrack.audio.MusicScanners;
import org.bomz.sts.ftlsoundtrack.audio.MusicSupplier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static basemod.DevConsole.logger;

/**
 * Contains state for the mod configuration page.
 */
public class ModSettingsController {

  private static final String INITIAL_MESSAGE = "";

  private static final float BUTTON_X = 350.0f;
  private static final float X_SPACER_BETWEEN_BUTTON_AND_LABEL = 200.0f;
  private static final float LABEL_X = 500.0f;
  private static final float FIRST_BUTTON_Y = 675.0f;
  private static final float LABEL_Y = 400.0f;
  private static final float Y_DIFFERENCE_BETWEEN_BUTTON_AND_LABEL = 45.0f;
  private static final float SPACER_Y = -100.0f;

  private boolean initialized = false;
  private ModLabel statusLabel;
  private final ModSettings settings;

  public ModSettingsController(ModSettings settings) {
    this.settings = settings;
  }

  private void initialize(ModLabel statusLabel) {
    this.statusLabel = statusLabel;
    initialized = true;
  }

  private void checkInitialized() {
    if (!initialized) {
      throw new RuntimeException("Class not initialized");
    }
  }

  private void scanMusicLibrary() {
    checkInitialized();

    MusicFileList list;
    try {
      list = MusicScanners.scanMusicLibrary();
    } catch (MusicFileList.CouldNotFindMusicException e) {
      recordFailure(e);
      return;
    }

    recordSuccess(list);
  }

  private void scanSteamLibrary() {
    checkInitialized();

    MusicFileList list;
    try {
      list = MusicScanners.scanSteamLibrary();
    } catch (MusicFileList.CouldNotFindMusicException e) {
      recordFailure(e);
      return;
    }

    recordSuccess(list);
  }

  private void scanClipboard() {
    checkInitialized();

    MusicFileList list;
    try {
      list = MusicScanners.scanClipboard();
    } catch (MusicFileList.CouldNotFindMusicException e) {
      recordFailure(e);
      return;
    }

    recordSuccess(list);
  }

  private void recordFailure(MusicFileList.CouldNotFindMusicException e) {
    logger.error("Failed to find music", e);
    this.setStatus(e.getMessage());
  }

  private void recordSuccess(MusicFileList list) {
    settings.save(list);
    String firstSong = list.get(MusicSupplier.Song.TITLE).toString();
    this.setStatus("Music files successfully loaded:\n" + firstSong);
  }

  private void setStatus(String newStatus) {
    this.statusLabel.text = newStatus;
    logger.info("Set status to " + newStatus);
  }

  private static List<IUIElement> addLabeledScanButton(
      int index, ModPanel panel, String label, Consumer<ModButton> onPress) {
    float buttonY = FIRST_BUTTON_Y + index * SPACER_Y;
    ModButton button = new ModButton(BUTTON_X, buttonY, panel, onPress);
    ModLabel modLabel = new ModLabel(
        label,
        BUTTON_X + X_SPACER_BETWEEN_BUTTON_AND_LABEL,
        buttonY + Y_DIFFERENCE_BETWEEN_BUTTON_AND_LABEL,
        panel,
        (me) -> {});
    return Arrays.asList(button, modLabel);
  }

  /**
   * Creates and registers a mod settings page for this mod.
   *
   * Requires ModSettings be initialized.
   */
  public static ModSettingsController create() {
    ModSettingsController controller = new ModSettingsController(ModSettings.getInstance());

    ModPanel constructedPanel = new ModPanel((panel) -> {
      ArrayList<IUIElement> elements = new ArrayList<>();
      elements.addAll(addLabeledScanButton(
          0,
          panel,
          "Scan for FTL soundtrack in home Music Library",
          button -> controller.scanMusicLibrary()));
      elements.addAll(addLabeledScanButton(
          1,
          panel,
          "Scan for FTL soundtrack in Steam Library (same library only)",
          button -> controller.scanSteamLibrary()
      ));
      elements.addAll(addLabeledScanButton(
          2,
          panel,
          "Scan for FTL soundtrack in path currently in clipboard",
          button -> controller.scanClipboard()
      ));
      ModLabel statusLabel = new ModLabel(INITIAL_MESSAGE, LABEL_X, LABEL_Y, panel, (me) -> {});
      elements.add(statusLabel);

      for (IUIElement e: elements) {
        panel.addUIElement(e);
      }

      controller.initialize(statusLabel);
    });

    // TODO: don't use base mod badge for no reason
    Texture badgeTexture = new Texture(Gdx.files.internal("img/BaseModBadge.png"));
    BaseMod.registerModBadge(
        badgeTexture, "FTL Soundtrack", "Nathaniel Rook", "FTL!!!", constructedPanel);
    return controller;
  }
}
