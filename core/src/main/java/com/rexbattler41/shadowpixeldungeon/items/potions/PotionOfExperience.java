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

package com.rexbattler41.shadowpixeldungeon.items.potions;

import com.rexbattler41.shadowpixeldungeon.actors.buffs.Bless;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Perks;
import com.rexbattler41.shadowpixeldungeon.effects.Flare;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;

public class PotionOfExperience extends Potion {

	{
		icon = ItemSpriteSheet.Icons.POTION_EXP;

		bones = true;
	}
	
	@Override
	public void apply( Hero hero ) {
		identify();
		float duration = Bless.DURATION * 8;
		if (hero.perks.contains(Perks.Perk.POTIONS)) duration *= 2;
		Buff.prolong(hero, Bless.class, duration);
		new Flare( 6, 32 ).color(0xFFFF00, true).show( curUser.sprite, 2f );
	}
	
	@Override
	public int value() {
		return isKnown() ? 50 * quantity : super.value();
	}

	@Override
	public int energyVal() {
		return isKnown() ? 8 * quantity : super.energyVal();
	}
}
