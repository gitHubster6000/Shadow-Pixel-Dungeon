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

package com.rexbattler41.shadowpixeldungeon.items.weapon.missiles;

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Bleeding;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;

public class Tomahawk extends MissileWeapon {

	{
		image = ItemSpriteSheet.TOMAHAWK;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 0.9f;

		internalTier = tier = 4;
		baseUses = 5;
	}

	@Override
	public int min(int lvl) {
		return  Math.round(3f * tier) +   //6 base, down from 8
				4 * lvl;                    //scaling unchanged
	}
	
	@Override
	public int max(int lvl) {
		return  Math.round(7f * tier) +  //15 base, down from 20
				(tier*2)*lvl;                 //scaling unchanged
	}
	
	@Override
	public int proc( Char attacker, Char defender, int damage ) {
		Buff.affect( defender, Bleeding.class ).set( Math.round(damage*0.6f) );
		return super.proc( attacker, defender, damage );
	}
}
