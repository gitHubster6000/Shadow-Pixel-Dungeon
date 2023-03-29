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

package com.rexbattler41.shadowpixeldungeon.actors.buffs;

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.Actor;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.hero.HeroAction;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Talent;
import com.rexbattler41.shadowpixeldungeon.actors.mobs.npcs.NPC;
import com.rexbattler41.shadowpixeldungeon.effects.CellEmitter;
import com.rexbattler41.shadowpixeldungeon.effects.Effects;
import com.rexbattler41.shadowpixeldungeon.effects.Speck;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.scenes.CellSelector;
import com.rexbattler41.shadowpixeldungeon.scenes.GameScene;
import com.rexbattler41.shadowpixeldungeon.ui.ActionIndicator;
import com.rexbattler41.shadowpixeldungeon.ui.BuffIndicator;
import com.rexbattler41.shadowpixeldungeon.utils.BArray;
import com.rexbattler41.shadowpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Preparation extends Buff implements ActionIndicator.Action {
	
	{
		//always acts after other buffs, so invisibility effects can process first
		actPriority = BUFF_PRIO - 1;
		type = buffType.POSITIVE;
	}
	
	public enum AttackLevel{
		LVL_1( 5, 0.50f, 1),
		LVL_2( 15, 1.25f, 3),
		LVL_3( 44, 4.20f, 8),
		LVL_4( 120, 41.30f, 16);

		final int turnsReq;
		final float baseDmgBonus;
		final int damageRolls;
		
		AttackLevel( int turns, float base, int rolls){
			turnsReq = turns;
			baseDmgBonus = base;
			damageRolls = rolls;
		}

		//1st index is prep level, 2nd is talent level
		private static final float[][] KOThresholds = new float[][]{
				{.15f, .04f, .05f, .06f},
				{.27f, .13f, .17f, .20f},
				{.45f, .27f, .33f, .40f},
				{1.50f, .67f, .83f, 1.0f}
		};

		public float KOThreshold(){
			return KOThresholds[ordinal()][Dungeon.hero.pointsInTalent(Talent.ENHANCED_LETHALITY)];
		}

		//1st index is prep level, 2nd is talent level
		private static final int[][] blinkRanges = new int[][]{
				{2, 1, 2, 2},
				{6, 3, 4, 6},
				{12, 4, 6, 7},
				{24, 6, 8, 10}
		};

		public int blinkDistance(){
			return blinkRanges[ordinal()][Dungeon.hero.pointsInTalent(Talent.ASSASSINS_REACH)];
		}
		
		public boolean canKO(Char defender){
			if (defender.properties().contains(Char.Property.MINIBOSS)
					|| defender.properties().contains(Char.Property.BOSS)){
				return (defender.HP/(float)defender.HT) < (KOThreshold()/5f);
			} else {
				return (defender.HP/(float)defender.HT) < KOThreshold();
			}
		}
		
		public int damageRoll( Char attacker ){
			int dmg = attacker.damageRoll();
			for( int i = 1; i < damageRolls; i++){
				int newDmg = attacker.damageRoll();
				if (newDmg > dmg) dmg = newDmg;
			}
			return Math.round(dmg * (1f + baseDmgBonus));
		}
		
		public static AttackLevel getLvl(int turnsInvis){
			List<AttackLevel> values = Arrays.asList(values());
			Collections.reverse(values);
			for ( AttackLevel lvl : values ){
				if (turnsInvis >= lvl.turnsReq){
					return lvl;
				}
			}
			return LVL_1;
		}
	}
	
	private int turnsInvis = 0;
	
	@Override
	public boolean act() {
		if (Dungeon.hero.justMoved) turnsInvis++;
		if (AttackLevel.getLvl(turnsInvis).blinkDistance() > 0 && target == Dungeon.hero){
			ActionIndicator.setAction(this);
		}
		spend(TICK);
		return true;
	}
	
	@Override
	public void detach() {
		super.detach();
		ActionIndicator.clearAction(this);
	}

	public int attackLevel(){
		return AttackLevel.getLvl(turnsInvis).ordinal()+1;
	}

	public int damageRoll( Char attacker ){
		return AttackLevel.getLvl(turnsInvis).damageRoll(attacker);
	}

	public boolean canKO( Char defender ){
		return !defender.isInvulnerable(target.getClass()) && AttackLevel.getLvl(turnsInvis).canKO(defender);
	}
	
	@Override
	public int icon() {
		return BuffIndicator.PREPARATION;
	}
	
	@Override
	public void tintIcon(Image icon) {
		switch (AttackLevel.getLvl(turnsInvis)){
			case LVL_1:
				icon.hardlight(0f, 1f, 0f);
				break;
			case LVL_2:
				icon.hardlight(1f, 1f, 0f);
				break;
			case LVL_3:
				icon.hardlight(1f, 0.6f, 0f);
				break;
			case LVL_4:
				icon.hardlight(1f, 0f, 0f);
				break;
		}
	}

	@Override
	public float iconFadePercent() {
		AttackLevel level = AttackLevel.getLvl(turnsInvis);
		if (level == AttackLevel.LVL_4){
			return 0;
		} else {
			float turnsForCur = level.turnsReq;
			float turnsForNext = AttackLevel.values()[level.ordinal()+1].turnsReq;
			turnsForNext -= turnsForCur;
			float turnsToNext = turnsInvis - turnsForCur;
			return Math.min(1, (turnsForNext - turnsToNext)/(turnsForNext));
		}
	}

	@Override
	public String iconTextDisplay() {
		return Integer.toString(turnsInvis);
	}

	@Override
	public String desc() {
		String desc = Messages.get(this, "desc");
		
		AttackLevel lvl = AttackLevel.getLvl(turnsInvis);

		desc += "\n\n" + Messages.get(this, "desc_dmg",
				(int)(lvl.baseDmgBonus*100),
				(int)(lvl.KOThreshold()*100),
				(int)(lvl.KOThreshold()*20));
		
		if (lvl.damageRolls > 1){
			desc += " " + Messages.get(this, "desc_dmg_likely");
		}
		
		if (lvl.blinkDistance() > 0){
			desc += "\n\n" + Messages.get(this, "desc_blink", lvl.blinkDistance());
		}
		
		desc += "\n\n" + Messages.get(this, "desc_invis_time", turnsInvis);
		
		if (lvl.ordinal() != AttackLevel.values().length-1){
			AttackLevel next = AttackLevel.values()[lvl.ordinal()+1];
			desc += "\n" + Messages.get(this, "desc_invis_next", next.turnsReq);
		}
		
		return desc;
	}
	
	private static final String TURNS = "turnsInvis";
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		turnsInvis = bundle.getInt(TURNS);
		ActionIndicator.setAction(this);
	}
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TURNS, turnsInvis);
	}

	@Override
	public String actionName() {
		return Messages.get(this, "action_name");
	}

	@Override
	public Image actionIcon() {
		Image actionIco = Effects.get(Effects.Type.WOUND);
		tintIcon(actionIco);
		return actionIco;
	}
	
	@Override
	public void doAction() {
		GameScene.selectCell(attack);
	}
	
	private CellSelector.Listener attack = new CellSelector.Listener() {
		
		@Override
		public void onSelect(Integer cell) {
			if (cell == null) return;
			final Char enemy = Actor.findChar( cell );
			if (enemy == null || Dungeon.hero.isCharmedBy(enemy) || enemy instanceof NPC || !Dungeon.level.heroFOV[cell] || enemy == Dungeon.hero){
				GLog.w(Messages.get(Preparation.class, "no_target"));
			} else {

				//just attack them then!
				if (Dungeon.hero.canAttack(enemy)){
					Dungeon.hero.curAction = new HeroAction.Attack( enemy );
					Dungeon.hero.next();
					return;
				}
				
				AttackLevel lvl = AttackLevel.getLvl(turnsInvis);

				PathFinder.buildDistanceMap(Dungeon.hero.pos, BArray.not(Dungeon.level.solid, null), lvl.blinkDistance());
				int dest = -1;
				for (int i : PathFinder.NEIGHBOURS8){
					//cannot blink into a cell that's occupied or impassable, only over them
					if (Actor.findChar(cell+i) != null)     continue;
					if (!Dungeon.level.passable[cell+i])    continue;

					if (dest == -1 || PathFinder.distance[dest] > PathFinder.distance[cell+i]){
						dest = cell+i;
					//if two cells have the same pathfinder distance, prioritize the one with the closest true distance to the hero
					} else if (PathFinder.distance[dest] == PathFinder.distance[cell+i]){
						if (Dungeon.level.trueDistance(Dungeon.hero.pos, dest) > Dungeon.level.trueDistance(Dungeon.hero.pos, cell+i)){
							dest = cell+i;
						}
					}

				}

				if (dest == -1 || PathFinder.distance[dest] == Integer.MAX_VALUE || Dungeon.hero.rooted){
					GLog.w(Messages.get(Preparation.class, "out_of_reach"));
					return;
				}
				
				Dungeon.hero.pos = dest;
				Dungeon.level.occupyCell(Dungeon.hero);
				//prevents the hero from being interrupted by seeing new enemies
				Dungeon.observe();
				GameScene.updateFog();
				Dungeon.hero.checkVisibleMobs();
				
				Dungeon.hero.sprite.place( Dungeon.hero.pos );
				Dungeon.hero.sprite.turnTo( Dungeon.hero.pos, cell);
				CellEmitter.get( Dungeon.hero.pos ).burst( Speck.factory( Speck.WOOL ), 6 );
				Sample.INSTANCE.play( Assets.Sounds.PUFF );

				Dungeon.hero.curAction = new HeroAction.Attack( enemy );
				Dungeon.hero.next();
			}
		}
		
		@Override
		public String prompt() {
			return Messages.get(Preparation.class, "prompt", AttackLevel.getLvl(turnsInvis).blinkDistance());
		}
	};
}