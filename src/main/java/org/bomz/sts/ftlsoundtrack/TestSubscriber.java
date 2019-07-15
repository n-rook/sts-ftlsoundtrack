package org.bomz.sts.ftlsoundtrack;

import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.bomz.sts.ftlsoundtrack.audio.MusicController;

import static basemod.DevConsole.logger;

public class TestSubscriber implements
    PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber {

  @Override
  public void receivePostInitialize() {
    logger.info("hello");

//    MusicSupplier musicSupplier = new MusicSupplier();
//    musicSupplier.playMusic(MusicSupplier.Song.TITLE);

//    MusicLoader m = new MusicLoader();
//    m.forcePlayMusic(m.loadExternalMusic());
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
