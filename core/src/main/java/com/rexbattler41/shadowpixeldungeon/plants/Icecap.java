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

package com.rexbattler41.shadowpixeldungeon.plants;

import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.blobs.Freezing;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.FrostImbue;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.hero.HeroSubClass;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;
import com.rexbattler41.shadowpixeldungeon.utils.BArray;
import com.watabou.utils.PathFinder;

public class Icecap extends Plant {
	
	{
		image = 4;
		seedClass = Seed.class;
	}
	
	@Override
	public void activate( Char ch ) {
		
		if (ch instanceof Hero && ((Hero) ch).isSubclass(HeroSubClass.WARDEN)){
			Buff.affect(ch, FrostImbue.class, FrostImbue.DURATION*0.3f);
		}
		
		PathFinder.buildDistanceMap( pos, BArray.not( Dungeon.level.losBlocking, null ), 1 );

		for (int i=0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				Freezing.affect( i );
			}
		}
	}
	
	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_ICECAP;

			plantClass = Icecap.class;
		}
	}
}
