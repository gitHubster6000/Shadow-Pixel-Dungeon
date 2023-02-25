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
import com.shatteredpixel.rexbattler41.actors.Actor;
import com.shatteredpixel.rexbattler41.actors.Char;
import com.shatteredpixel.rexbattler41.actors.buffs.Invisibility;
import com.shatteredpixel.rexbattler41.effects.CellEmitter;
import com.shatteredpixel.rexbattler41.effects.Speck;
import com.shatteredpixel.rexbattler41.items.KindOfWeapon;
import com.shatteredpixel.rexbattler41.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;

public class Greataxe extends MeleeWeapon {

	{
		image = ItemSpriteSheet.GREATAXE;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 1f;
        defaultAction = AC_THROW;

		internalTier = tier = 5;
	}

	@Override
	public int max(int lvl) {
		return  7*(tier+4) +    //63 base, up from 36
				lvl*(tier+2);   //+7
	}

	@Override
	public int STRReq(int lvl) {
		return STRReq(tier+1, lvl); //20 base strength req, up from 18
	}

    @Override
    protected void onThrow(int cell) {
        super.onThrow(cell);
        int enemyCount = 0;
        for (int i : PathFinder.NEIGHBOURS9){

            if (!Dungeon.level.solid[cell + i]
                    && !Dungeon.level.pit[cell + i]
                    && Actor.findChar(cell + i) != null
                    && Actor.findChar(cell + i).alignment == Char.Alignment.ENEMY) {
                KindOfWeapon equipped = Dungeon.hero.belongings.weapon;
                Dungeon.hero.belongings.weapon = this;
                boolean hit = Dungeon.hero.attack( Actor.findChar(cell + i) );
                Invisibility.dispel();

                Dungeon.hero.belongings.weapon = equipped;
                enemyCount++;

                CellEmitter.get(cell + i).burst(Speck.factory(Speck.SCREAM), 2);
            }
        }
        Dungeon.hero.spendAndNext( delayFactor(Dungeon.hero) );
    }
}
