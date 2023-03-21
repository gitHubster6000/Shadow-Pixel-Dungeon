package com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.voidwalker;

import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Levitation;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Paralysis;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Talent;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.rexbattler41.shadowpixeldungeon.actors.mobs.Mob;
import com.rexbattler41.shadowpixeldungeon.items.armor.ClassArmor;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.ui.HeroIcon;
import com.rexbattler41.shadowpixeldungeon.utils.GLog;

public class EarthQuake extends ArmorAbility {

    {
        baseChargeUse = 35f;
    }
    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {
        for (int i = 0; i < 10; i++) {
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (Dungeon.level.heroFOV[mob.pos]) {
                    //deals 10%HT, plus 0-90%HP based on scaling
                    mob.damage(Math.round(mob.HT/10f + (mob.HP * 0.225f)), this);
                    if (mob.isAlive()) {
                        Buff.prolong(mob, Paralysis.class, Paralysis.DURATION);
                    }
                }
            }
            if (hero.buff(Levitation.class) == null){
                hero.damage(10, this);
                if (!hero.isAlive()) {
                    Dungeon.fail( getClass() );
                    GLog.n( Messages.get(this, "ondeath") );
                }
            }
        }
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
