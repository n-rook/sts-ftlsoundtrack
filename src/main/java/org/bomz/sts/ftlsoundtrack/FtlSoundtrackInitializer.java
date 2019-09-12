package org.bomz.sts.ftlsoundtrack;

import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.bomz.sts.ftlsoundtrack.audio.DualMusicPlayer;
import org.bomz.sts.ftlsoundtrack.audio.DualMusicPlayer.MusicMode;
import org.bomz.sts.ftlsoundtrack.audio.MusicSupplier;
import org.bomz.sts.ftlsoundtrack.audio.patch.MusicPatch;

public class FtlSoundtrackInitializer implements
    PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber {
  private static DualMusicPlayer player;

  @Override
  public void receivePostInitialize() {
    ModSettings.initialize();
    ModSettings settings = ModSettings.getInstance();
    settings.load();
    // TODO: More gracefully handle failing to load music than just crashing utterly
    // Probably log an error (since this is happening on initialization you get to see the error)
    // and continue

    if (settings.areSongsLoaded()) {
      player = new DualMusicPlayer(new MusicSupplier(settings.getSongs()));
      MusicPatch.loadRealMusicMaster(player);
    } else {
      MusicPatch.loadNoOpMusicMaster();
    }

    ModSettingsController.create();
  }

  @Override
  public void receiveOnBattleStart(AbstractRoom abstractRoom) {
    if (player != null) {
      player.setMode(MusicMode.INTENSE);
    }
  }

  @Override
  public void receivePostBattle(AbstractRoom abstractRoom) {
    if (player != null) {
      player.setMode(MusicMode.RELAXED);
    }
  }
}
