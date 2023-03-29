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

package com.rexbattler41.shadowpixeldungeon.items.rings;

import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;

import java.text.DecimalFormat;

public class RingOfTenacity extends Ring {

	{
		icon = ItemSpriteSheet.Icons.RING_TENACITY;
	}

	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats", new DecimalFormat("#.###").format(100f * (1f - (0.85f - soloVisualBonus()*0.0015))));
		} else {
			return Messages.get(this, "typical_stats", new DecimalFormat("#.###").format(15f));
		}
	}

	@Override
	protected RingBuff buff( ) {
		return new Tenacity();
	}
	
	public static float damageMultiplier( Char t ){
        float multiplier = 0.85f;
		if (getBuffedBonus(t, Tenacity.class) > 0) multiplier = 1.1f;
		if (getBuffedBonus(t, Tenacity.class) > 1) multiplier -= 0.0015f*getBuffedBonus(t, Tenacity.class)*((float)(t.HT - t.HP)/t.HT);
        return multiplier;
	}

	public class Tenacity extends RingBuff {
	}
}
