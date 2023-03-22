/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2020 Trashbox Bobylev
 *
 * Shadow Pixel Dungeon
 * Copyright (C) 2023 Rexbattler41
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.voidwalker;

import static com.rexbattler41.shadowpixeldungeon.actors.Actor.TICK;

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
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (Dungeon.level.heroFOV[mob.pos]) {
                //deals 10%HT, plus 0-90%HP based on scaling
                mob.damage(Math.round(mob.HT/50f + (mob.HP * 0.225f)), this);
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
        hero.spend(TICK);

        armor.charge -= chargeUse( hero );
        armor.updateQuickslot();
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
