package org.bomz.sts.ftlsoundtrack.audio.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

/**
* This "override" class just tells ModTheSpire to ignore the patch class.
 */
class NoOpMusicMasterShim implements MusicMasterShim {
  @Override
  public SpireReturn changeBGM(String key) {
    return SpireReturn.Continue();
  }

  @Override
  public SpireReturn playTempBgmInstantlyLoopOverload(String key) {
    return SpireReturn.Continue();
  }

  @Override
  public SpireReturn update() {
    return SpireReturn.Continue();
  }

  @Override
  public SpireReturn playTempBGM() {
    return SpireReturn.Continue();
  }

  @Override
  public SpireReturn playTempBgmInstantly() {
    return SpireReturn.Continue();
  }

  @Override
  public SpireReturn precacheTempBgm() {
    return SpireReturn.Continue();
  }

  @Override
  public SpireReturn playPrecachedTempBgm() {
    return SpireReturn.Continue();
  }
}
