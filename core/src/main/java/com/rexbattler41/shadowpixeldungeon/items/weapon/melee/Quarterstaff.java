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

package com.rexbattler41.shadowpixeldungeon.items.weapon.melee;

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;

public class Quarterstaff extends MeleeWeapon {

	{
		image = ItemSpriteSheet.QUARTERSTAFF;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 1f;

		internalTier = tier = 2;
	}

	@Override
	public int max(int lvl) {
		return  5*(tier+1) +    //15 base, down from 18
				lvl*(tier+2);   //+4
	}

	@Override
	public int defenseFactor( Char owner ) {
		return 2;	//2 extra defence
	}
}
