package org.bomz.sts.ftlsoundtrack;

import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.bomz.sts.ftlsoundtrack.audio.MusicController;

public class FtlSoundtrackInitializer implements
    PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber {

  @Override
  public void receivePostInitialize() {
    ModSettings.initialize();
    ModSettings.getInstance().load();
    // TODO: Actually use ModSettings to play music
    // TODO: More gracefully handle failing to load music than just crashing utterly
    // Probably log an error (since this is happening on initialization you get to see the error)
    // and continue

    ModSettingsController.create();
  }

  @Override
  public void receiveOnBattleStart(AbstractRoom abstractRoom) {
    MusicController.instance().setMode(MusicController.MusicMode.INTENSE);
  }

  @Override
  public void receivePostBattle(AbstractRoom abstractRoom) {
    MusicController.instance().setMode(MusicController.MusicMode.RELAXED);
  }
}
