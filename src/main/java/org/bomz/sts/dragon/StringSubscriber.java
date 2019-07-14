package org.bomz.sts.dragon;

import basemod.interfaces.EditStringsSubscriber;

import static basemod.BaseMod.logger;

public class StringSubscriber implements EditStringsSubscriber {
    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings");
//        BaseMod.loadCustomStringsFile(CardStrings.class, "cards.json");
//        BaseMod.loadCustomStringsFile(RelicStrings.class, "relics.json");
        logger.info("Edited strings");
    }
}
