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

import com.shatteredpixel.rexbattler41.Badges;
import com.shatteredpixel.rexbattler41.Dungeon;
import com.shatteredpixel.rexbattler41.actors.Actor;
import com.shatteredpixel.rexbattler41.actors.Char;
import com.shatteredpixel.rexbattler41.actors.buffs.AscensionChallenge;
import com.shatteredpixel.rexbattler41.actors.buffs.Invisibility;
import com.shatteredpixel.rexbattler41.actors.buffs.Light;
import com.shatteredpixel.rexbattler41.effects.CellEmitter;
import com.shatteredpixel.rexbattler41.effects.particles.PurpleParticle;
import com.shatteredpixel.rexbattler41.items.Dewdrop;
import com.shatteredpixel.rexbattler41.items.Generator;
import com.shatteredpixel.rexbattler41.items.Item;
import com.shatteredpixel.rexbattler41.items.wands.WandOfDisintegration;
import com.shatteredpixel.rexbattler41.mechanics.Ballistica;
import com.shatteredpixel.rexbattler41.messages.Messages;
import com.shatteredpixel.rexbattler41.scenes.GameScene;
import com.shatteredpixel.rexbattler41.sprites.CharSprite;
import com.shatteredpixel.rexbattler41.sprites.EyeSprite;
import com.shatteredpixel.rexbattler41.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Eye extends Mob {

	public static int experience(){
		switch (Dungeon.cycle){
			case 0: default:
				return 13;
			case 1:
				return 93;
			case 2:
				return 780;
			case 3:
				return 18000;
			case 4:
				return 6000000;
		}
	}
	
	{
		spriteClass = EyeSprite.class;
		
		HP = HT = 100;
		defenseSkill = 20;
		viewDistance = Light.DISTANCE;
		
		EXP = experience();
		maxLvl = 26;
		
		flying = true;

		HUNTING = new Hunting();
		
		loot = new Dewdrop();
		lootChance = 1f;

		properties.add(Property.DEMONIC);
        switch (Dungeon.cycle){
            case 1:
                HP = HT = 1093;
                defenseSkill = 85;
                break;
            case 2:
                HP = HT = 18965;
                defenseSkill = 280;
                break;
            case 3:
                HP = HT = 750000;
                defenseSkill = 900;
                break;
            case 4:
                HP = HT = 290000000;
                defenseSkill = 14000;
                break;
        }
	}

	@Override
	public int damageRoll() {
        switch (Dungeon.cycle) {
            case 1: return Random.NormalIntRange(86, 106);
            case 2: return Random.NormalIntRange(360, 487);
            case 3: return Random.NormalIntRange(2600, 3641);
            case 4: return Random.NormalIntRange(179000, 290000);
        }
		return Random.NormalIntRange(20, 30);
	}

	@Override
	public int attackSkill( Char target ) {
        switch (Dungeon.cycle){
            case 1: return 143;
            case 2: return 450;
            case 3: return 1100;
            case 4: return 16000;
        }
		return 30;
	}
	
	@Override
	public int drRoll() {
        switch (Dungeon.cycle){
            case 1: return Random.NormalIntRange(40, 74);
            case 2: return Random.NormalIntRange(178, 334);
            case 3: return Random.NormalIntRange(1750, 2800);
            case 4: return Random.NormalIntRange(200000, 300000);
        }
		return Random.NormalIntRange(0, 10);
	}
	
	private Ballistica beam;
	private int beamTarget = -1;
	private int beamCooldown;
	public boolean beamCharged;

	@Override
	protected boolean canAttack( Char enemy ) {

		if (beamCooldown == 0) {
			Ballistica aim = new Ballistica(pos, enemy.pos, Ballistica.STOP_SOLID);

			if (enemy.invisible == 0 && !isCharmedBy(enemy) && fieldOfView[enemy.pos] && aim.subPath(1, aim.dist).contains(enemy.pos)){
				beam = aim;
				beamTarget = aim.collisionPos;
				return true;
			} else
				//if the beam is charged, it has to attack, will aim at previous location of target.
				return beamCharged;
		} else
			return super.canAttack(enemy);
	}

	@Override
	protected boolean act() {
		if (beamCharged && state != HUNTING){
			beamCharged = false;
			sprite.idle();
		}
		if (beam == null && beamTarget != -1) {
			beam = new Ballistica(pos, beamTarget, Ballistica.STOP_SOLID);
			sprite.turnTo(pos, beamTarget);
		}
		if (beamCooldown > 0)
			beamCooldown--;
		return super.act();
	}

	@Override
	protected boolean doAttack( Char enemy ) {

		if (beamCooldown > 0) {
			return super.doAttack(enemy);
		} else if (!beamCharged){
			((EyeSprite)sprite).charge( enemy.pos );
			spend( attackDelay()*2f );
			beamCharged = true;
			return true;
		} else {

			spend( attackDelay() );
			
			beam = new Ballistica(pos, beamTarget, Ballistica.STOP_SOLID);
			if (Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[beam.collisionPos] ) {
				sprite.zap( beam.collisionPos );
				return false;
			} else {
				sprite.idle();
				deathGaze();
				return true;
			}
		}

	}

	@Override
	public void damage(int dmg, Object src) {
		if (beamCharged) dmg /= 4;
		super.damage(dmg, src);
	}
	
	//used so resistances can differentiate between melee and magical attacks
	public static class DeathGaze{}

	public void deathGaze(){
		if (!beamCharged || beamCooldown > 0 || beam == null)
			return;

		beamCharged = false;
		beamCooldown = Random.IntRange(4, 6);

		boolean terrainAffected = false;

		Invisibility.dispel(this);
		for (int pos : beam.subPath(1, beam.dist)) {

			if (Dungeon.level.flamable[pos]) {

				Dungeon.level.destroy( pos );
				GameScene.updateMap( pos );
				terrainAffected = true;

			}

			Char ch = Actor.findChar( pos );
			if (ch == null) {
				continue;
			}

			if (hit( this, ch, true )) {
                int dmg = Random.NormalIntRange(30, 50);
                switch (Dungeon.cycle){
                    case 1: dmg = Random.NormalIntRange(168, 231); break;
                    case 2: dmg = Random.NormalIntRange(510, 824); break;
                    case 3: dmg = Random.NormalIntRange(3750, 5200); break;
                    case 4: dmg = Random.NormalIntRange(200000, 560000); break;
                }
				dmg = Math.round(dmg * AscensionChallenge.statModifier(this));
                ch.damage(dmg, new DeathGaze() );

				if (Dungeon.level.heroFOV[pos]) {
					ch.sprite.flash();
					CellEmitter.center( pos ).burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
				}

				if (!ch.isAlive() && ch == Dungeon.hero) {
					Badges.validateDeathFromEnemyMagic();
					Dungeon.fail( getClass() );
					GLog.n( Messages.get(this, "deathgaze_kill") );
				}
			} else {
				ch.sprite.showStatus( CharSprite.NEUTRAL,  ch.defenseVerb() );
			}
		}

		if (terrainAffected) {
			Dungeon.observe();
		}

		beam = null;
		beamTarget = -1;
	}

	//generates an average of 1 dew, 0.25 seeds, and 0.25 stones
	@Override
	public Item createLoot() {
		Item loot;
		switch(Random.Int(4)){
			case 0: case 1: default:
				loot = new Dewdrop();
				int ofs;
				do {
					ofs = PathFinder.NEIGHBOURS8[Random.Int(8)];
				} while (Dungeon.level.solid[pos + ofs] && !Dungeon.level.passable[pos + ofs]);
				if (Dungeon.level.heaps.get(pos+ofs) == null) {
					Dungeon.level.drop(new Dewdrop(), pos + ofs).sprite.drop(pos);
				} else {
					Dungeon.level.drop(new Dewdrop(), pos + ofs).sprite.drop(pos + ofs);
				}
				break;
			case 2:
				loot = Generator.randomUsingDefaults(Generator.Category.SEED);
				break;
			case 3:
				loot = Generator.randomUsingDefaults(Generator.Category.STONE);
				break;
		}
		return loot;
	}

	private static final String BEAM_TARGET     = "beamTarget";
	private static final String BEAM_COOLDOWN   = "beamCooldown";
	private static final String BEAM_CHARGED    = "beamCharged";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put( BEAM_TARGET, beamTarget);
		bundle.put( BEAM_COOLDOWN, beamCooldown );
		bundle.put( BEAM_CHARGED, beamCharged );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (bundle.contains(BEAM_TARGET))
			beamTarget = bundle.getInt(BEAM_TARGET);
		beamCooldown = bundle.getInt(BEAM_COOLDOWN);
		beamCharged = bundle.getBoolean(BEAM_CHARGED);
	}

	{
		resistances.add( WandOfDisintegration.class );
	}
	
	{
		//immunities.add( Terror.class );
	}

	private class Hunting extends Mob.Hunting{
		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			//even if enemy isn't seen, attack them if the beam is charged
			if (beamCharged && enemy != null && canAttack(enemy)) {
				enemySeen = enemyInFOV;
				return doAttack(enemy);
			}
			return super.act(enemyInFOV, justAlerted);
		}
	}
}
