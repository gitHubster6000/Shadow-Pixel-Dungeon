/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
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

package com.rexbattler41.shadowpixeldungeon.items.potions.exotic;

import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Overload;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.effects.Flare;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;

public class PotionOfHolyFuror extends ExoticPotion {
	
	{
		icon = ItemSpriteSheet.Icons.POTION_DIVINE;
	}

	@Override
	public void apply( Hero hero ) {
		identify();

		Buff.prolong(hero, Overload.class, 200f);

		new Flare( 6, 32 ).color(0xFFFF00, true).show( curUser.sprite, 4f );
	}
	
}
