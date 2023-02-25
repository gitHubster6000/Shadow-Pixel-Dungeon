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
import com.shatteredpixel.rexbattler41.sprites.ItemSpriteSheet;

public class Scimitar extends MeleeWeapon {

	{
		image = ItemSpriteSheet.SCIMITAR;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 1.2f;

		internalTier = tier = 3;
		DLY = 0.8f; //1.25x speed
	}

	@Override
	public int max(int lvl) {
		return  5*(tier+1) +    //20 base, down from 24
				lvl*(tier+1);   //+5
	}

}
