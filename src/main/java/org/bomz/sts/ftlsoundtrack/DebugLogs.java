package org.bomz.sts.ftlsoundtrack;

import basemod.interfaces.PostDrawSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static basemod.BaseMod.logger;

public class DebugLogs implements PostDrawSubscriber {

  public void receivePostDraw(AbstractCard abstractCard) {
    logger.info("Hello we drew " + abstractCard.name);
  }
}
