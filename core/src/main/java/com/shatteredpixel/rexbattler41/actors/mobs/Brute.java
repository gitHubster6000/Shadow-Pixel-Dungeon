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

package com.shatteredpixel.rexbattler41.actors.mobs;

import com.shatteredpixel.rexbattler41.Dungeon;
import com.shatteredpixel.rexbattler41.actors.Char;
import com.shatteredpixel.rexbattler41.actors.buffs.Buff;
import com.shatteredpixel.rexbattler41.actors.buffs.ShieldBuff;
import com.shatteredpixel.rexbattler41.actors.buffs.Terror;
import com.shatteredpixel.rexbattler41.effects.SpellSprite;
import com.shatteredpixel.rexbattler41.items.Gold;
import com.shatteredpixel.rexbattler41.levels.features.Chasm;
import com.shatteredpixel.rexbattler41.messages.Messages;
import com.shatteredpixel.rexbattler41.sprites.BruteSprite;
import com.shatteredpixel.rexbattler41.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Brute extends Mob {
	
	{
		spriteClass = BruteSprite.class;
		
		HP = HT = 40;
		defenseSkill = 15;
		
		EXP = 8;
		maxLvl = 16;
		
		loot = Gold.class;
		lootChance = 0.5f;
        switch (Dungeon.cycle){
            case 1:
                HP = HT = 420;
                defenseSkill = 53;
                EXP = 38;
                break;
            case 2:
                HP = HT = 5645;
                defenseSkill = 200;
                EXP = 360;
                break;
            case 3:
                HP = HT = 105000;
                defenseSkill = 525;
                EXP = 3600;
                break;
            case 4:
                HP = HT = 9000000;
                defenseSkill = 3400;
                EXP = 90000;
                break;
        }
	}
	
	protected boolean hasRaged = false;
	
	@Override
	public int damageRoll() {
        switch (Dungeon.cycle) {
            case 1: return buff(BruteRage.class) != null ?
                    Random.NormalIntRange( 70, 123 ) :
                    Random.NormalIntRange( 58, 70 );
            case 2: return buff(BruteRage.class) != null ?
                    Random.NormalIntRange( 340, 541 ) :
                    Random.NormalIntRange( 240, 368 );
            case 3: return buff(BruteRage.class) != null ?
                    Random.NormalIntRange( 1200, 1640 ) :
                    Random.NormalIntRange(900, 1340);
            case 4: return buff(BruteRage.class) != null ?
                    Random.NormalIntRange( 30000, 64000 ) :
                    Random.NormalIntRange(16500, 40000);
        }
		return buff(BruteRage.class) != null ?
			Random.NormalIntRange( 15, 40 ) :
			Random.NormalIntRange( 5, 25 );
	}
	
	@Override
	public int attackSkill( Char target ) {
        switch (Dungeon.cycle){
            case 1: return 80;
            case 2: return 300;
            case 3: return 740;
            case 4: return 3600;
        }
		return 20;
	}
	
	@Override
	public int drRoll() {
        switch (Dungeon.cycle){
            case 1: return Random.NormalIntRange(20, 39);
            case 2: return Random.NormalIntRange(120, 231);
            case 3: return Random.NormalIntRange(500, 890);
            case 4: return Random.NormalIntRange(10000, 22000);
        }
		return Random.NormalIntRange(0, 8);
	}

	@Override
	public void die(Object cause) {
		super.die(cause);

		if (cause == Chasm.class){
			hasRaged = true; //don't let enrage trigger for chasm deaths
		}
	}

	@Override
	public synchronized boolean isAlive() {
		if (super.isAlive()){
			return true;
		} else {
			if (!hasRaged){
				triggerEnrage();
			}
			return !buffs(BruteRage.class).isEmpty();
		}
	}
	
	protected void triggerEnrage(){
		Buff.affect(this, BruteRage.class).setShield(HT/2 + 4);
		if (Dungeon.level.heroFOV[pos]) {
			SpellSprite.show( this, SpellSprite.BERSERK);
		}
		spend( TICK );
		hasRaged = true;
	}
	
	private static final String HAS_RAGED = "has_raged";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(HAS_RAGED, hasRaged);
	}
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		hasRaged = bundle.getBoolean(HAS_RAGED);
	}
	
	public static class BruteRage extends ShieldBuff {
		
		{
			type = buffType.POSITIVE;
		}
		
		@Override
		public boolean act() {
			
			if (target.HP > 0){
				detach();
				return true;
			}
			
			absorbDamage( target.HT / 10 );
			
			if (shielding() <= 0){
				target.die(null);
			}
			
			spend( TICK );
			
			return true;
		}
		
		@Override
		public int icon () {
			return BuffIndicator.FURY;
		}
		
		@Override
		public String desc () {
			return Messages.get(this, "desc", shielding());
		}

		{
			immunities.add(Terror.class);
		}
	}
}
