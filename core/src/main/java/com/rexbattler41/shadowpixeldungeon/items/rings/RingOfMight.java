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
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.items.Item;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;

import java.text.DecimalFormat;

public class RingOfMight extends Ring {

	{
		icon = ItemSpriteSheet.Icons.RING_MIGHT;
	}

	@Override
	public boolean doEquip(Hero hero) {
		if (super.doEquip(hero)){
			hero.updateHT( false );
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)){
			hero.updateHT( false );
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Item upgrade() {
		super.upgrade();
		updateTargetHT();
		return this;
	}

	@Override
	public void level(int value) {
		super.level(value);
		updateTargetHT();
	}
	
	private void updateTargetHT(){
		if (buff != null && buff.target instanceof Hero){
			((Hero) buff.target).updateHT( false );
		}
	}
	
	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats", soloBuffedBonus()-1, new DecimalFormat("#.###").format(100f * ((1.10f + soloVisualBonus()*0.0025f) - 1f)));
		} else {
			return Messages.get(this, "typical_stats", 0, new DecimalFormat("#.###").format(10f));
		}
	}

	@Override
	protected RingBuff buff( ) {
		return new Might();
	}
	
	public static int strengthBonus( Char target ){
		return (getBuffedBonus(target, Might.class) >= 1) ? getBuffedBonus(target, Might.class) : 0;
    }
	
	public static float HTMultiplier( Char target ){
        float multiplier = 1f;
        if (getBuffedBonus(target, Might.class) == 1) multiplier = 1.1f;
        if (getBuffedBonus(target, Might.class) > 1) multiplier += getBuffedBonus(target, Might.class)*0.0025;
        return multiplier;
	}

	public class Might extends RingBuff {
	}
}

