package org.bomz.sts.dragon;

import basemod.BaseMod;
import basemod.interfaces.EditCharactersSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import org.bomz.sts.dragon.characters.TheDragon;
import org.bomz.sts.dragon.patches.AbstractCardEnum;
import org.bomz.sts.dragon.patches.DragonEnum;

@SpireInitializer
public class FtlSoundtrackMod {

    public static void initialize() {
        new FtlSoundtrackMod();
        BaseMod.subscribe(new StringSubscriber());
    }
}