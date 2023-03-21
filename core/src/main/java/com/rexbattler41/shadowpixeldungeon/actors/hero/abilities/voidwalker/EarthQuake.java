package com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.voidwalker;

import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Talent;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.rexbattler41.shadowpixeldungeon.items.armor.ClassArmor;
import com.rexbattler41.shadowpixeldungeon.ui.HeroIcon;

public class EarthQuake extends ArmorAbility {
    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {

    }

    @Override
    public int icon() {
        return HeroIcon.ELEMENTAL_BLAST;
    }

    @Override
    public Talent[] talents() {
        return new Talent[0];
    }
}
