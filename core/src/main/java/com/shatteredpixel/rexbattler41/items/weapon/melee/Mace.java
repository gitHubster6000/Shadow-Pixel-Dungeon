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
import com.shatteredpixel.rexbattler41.Dungeon;
import com.shatteredpixel.rexbattler41.actors.Char;
import com.shatteredpixel.rexbattler41.items.wands.WandOfDisintegration;
import com.shatteredpixel.rexbattler41.mechanics.Ballistica;
import com.shatteredpixel.rexbattler41.sprites.ItemSpriteSheet;
import com.watabou.utils.Callback;

public class Mace extends MeleeWeapon {

	{
		image = ItemSpriteSheet.MACE;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 1f;

		internalTier = tier = 3;
		ACC = 1.28f; //28% boost to accuracy
	}

	@Override
	public int max(int lvl) {
		return  5*(tier+1) +    //20 base, down from 24
				lvl*(tier+2);   //+5
	}

    @Override
    public int proc(Char attacker, Char defender, int damage) {
	    if (attacker == Dungeon.hero) {
			WandOfDisintegration wand = ((WandOfDisintegration)(new WandOfDisintegration().upgrade(level())));
	    	wand.fx(
	    		new Ballistica(attacker.pos, defender.pos, Ballistica.STOP_SOLID),
				new Callback() {
					public void call() {
						wand.onZap(new Ballistica(attacker.pos, defender.pos, Ballistica.STOP_SOLID));
						wand.wandUsed();
					}
				}

		);
	    }
        return super.proc(attacker, defender, damage);
    }
}
