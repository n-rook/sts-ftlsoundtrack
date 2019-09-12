package org.bomz.sts.ftlsoundtrack.audio.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

/**
 * Serves as a shim for STS's MusicMaster class.
 *
 * A no-op version of this class will be used if we don't load up properly.
 */
interface MusicMasterShim {
  SpireReturn changeBGM(String key);

  SpireReturn playTempBgmInstantlyLoopOverload(String key);

  SpireReturn update();

  SpireReturn playTempBGM();

  SpireReturn playTempBgmInstantly();

  SpireReturn precacheTempBgm();

  SpireReturn playPrecachedTempBgm();
}
