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

package com.shatteredpixel.rexbattler41.items.weapon.melee;

import com.shatteredpixel.rexbattler41.Assets;
import com.shatteredpixel.rexbattler41.actors.Char;
import com.shatteredpixel.rexbattler41.actors.buffs.Buff;
import com.shatteredpixel.rexbattler41.actors.buffs.FlavourBuff;
import com.shatteredpixel.rexbattler41.messages.Messages;
import com.shatteredpixel.rexbattler41.sprites.ItemSpriteSheet;
import com.shatteredpixel.rexbattler41.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class Sai extends MeleeWeapon {

	{
		image = ItemSpriteSheet.SAI;
		hitSound = Assets.Sounds.HIT_STAB;
		hitSoundPitch = 1.3f;

		internalTier = tier = 3;
		DLY = 0.5f; //2x speed
	}

	@Override
	public int max(int lvl) {
		return  Math.round(3f*(tier+1)) +     //12 base, down from 24
				lvl*Math.round(0.5f*(tier+2));  //+2.5 per level, down from +5
	}

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        DefenseDebuff debuff = Buff.affect(defender, DefenseDebuff.class, 3.33f);
        debuff.stack += max() / 2;
        return super.proc(attacker, defender, damage);
    }

    public static class DefenseDebuff extends FlavourBuff {

        public static final float DURATION = 3f;

        public int stack = 0;

        {
            type = buffType.NEGATIVE;
            announced = true;
        }

        @Override
        public int icon() {
            return BuffIndicator.VULNERABLE;
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (DURATION - visualcooldown()) / DURATION);
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", dispTurns(), stack);
        }

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put("stack", stack);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            stack = bundle.getInt("stack");
        }
    }

}
