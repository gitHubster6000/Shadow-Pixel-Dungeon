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

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.actors.Actor;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.FlavourBuff;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Invisibility;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Talent;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.rexbattler41.shadowpixeldungeon.effects.particles.LeafParticle;
import com.rexbattler41.shadowpixeldungeon.items.armor.ClassArmor;
import com.rexbattler41.shadowpixeldungeon.ui.BuffIndicator;
import com.rexbattler41.shadowpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.audio.Sample;

public class AnimalsTrans extends ArmorAbility {
    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {

        Buff.prolong(hero, AnimalsTrans.animalsPowerTracker.class, AnimalsTrans.animalsPowerTracker.DURATION);
        hero.buff(AnimalsTrans.animalsPowerTracker.class);
        hero.sprite.operate(hero.pos);
        Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
        hero.sprite.emitter().burst(LeafParticle.GENERAL, 10);

        armor.charge -= chargeUse(hero);
        armor.updateQuickslot();
        Invisibility.dispel();
        hero.spendAndNext(Actor.TICK);
    }

    {
        baseChargeUse = 100f;
    }

    @Override
    public Talent[] talents() {
        return new Talent[0];
    }

    @Override
    public int icon() {
        return HeroIcon.SPIRIT_HAWK;
    }

    public static class animalsPowerTracker extends FlavourBuff {

        {
            type = buffType.POSITIVE;
        }

        public static final float DURATION = 400f;

        @Override
        public int icon() {
            return BuffIndicator.NATURE_POWER;
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (DURATION - visualcooldown()) / DURATION);
        }

    }
}