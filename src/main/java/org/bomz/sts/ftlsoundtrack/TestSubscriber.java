package org.bomz.sts.ftlsoundtrack;

import basemod.interfaces.PostInitializeSubscriber;
import org.bomz.sts.ftlsoundtrack.audio.MusicLoader;
import org.bomz.sts.ftlsoundtrack.audio.MusicSupplier;

import static basemod.DevConsole.logger;

public class TestSubscriber implements PostInitializeSubscriber {

  @Override
  public void receivePostInitialize() {
    logger.info("hello");

//    MusicSupplier musicSupplier = new MusicSupplier();
//    musicSupplier.playMusic(MusicSupplier.Song.TITLE);

//    MusicLoader m = new MusicLoader();
//    m.forcePlayMusic(m.loadExternalMusic());
  }
}
