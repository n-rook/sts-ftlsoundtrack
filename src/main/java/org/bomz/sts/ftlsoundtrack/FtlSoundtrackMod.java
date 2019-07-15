package org.bomz.sts.ftlsoundtrack;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class FtlSoundtrackMod {

  public FtlSoundtrackMod() {
  }

    public static void initialize() {
      BaseMod.subscribe(new StringSubscriber());
      BaseMod.subscribe(new TestSubscriber());

//        new FtlSoundtrackMod();
//        BaseMod.subscribe(new StringSubscriber());
    }
}
