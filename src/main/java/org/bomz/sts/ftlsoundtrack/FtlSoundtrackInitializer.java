package org.bomz.sts.ftlsoundtrack;

import basemod.ModPanel;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.bomz.sts.ftlsoundtrack.audio.MusicController;

import static basemod.DevConsole.logger;

public class FtlSoundtrackInitializer implements
    PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber {

  @Override
  public void receivePostInitialize() {
    ModPanel settingsPanel = new ModPanel();
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
