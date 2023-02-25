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

import com.shatteredpixel.rexbattler41.Assets;
import com.shatteredpixel.rexbattler41.Challenges;
import com.shatteredpixel.rexbattler41.Dungeon;
import com.shatteredpixel.rexbattler41.Statistics;
import com.shatteredpixel.rexbattler41.actors.Actor;
import com.shatteredpixel.rexbattler41.actors.Char;
import com.shatteredpixel.rexbattler41.actors.blobs.*;
import com.shatteredpixel.rexbattler41.actors.buffs.*;
import com.shatteredpixel.rexbattler41.effects.CellEmitter;
import com.shatteredpixel.rexbattler41.effects.Lightning;
import com.shatteredpixel.rexbattler41.effects.particles.SparkParticle;
import com.shatteredpixel.rexbattler41.levels.BlackMimicLevel;
import com.shatteredpixel.rexbattler41.levels.CavesBossLevel;
import com.shatteredpixel.rexbattler41.messages.Messages;
import com.shatteredpixel.rexbattler41.scenes.GameScene;
import com.shatteredpixel.rexbattler41.sprites.CharSprite;
import com.shatteredpixel.rexbattler41.sprites.PylonSprite;
import com.shatteredpixel.rexbattler41.tiles.DungeonTilemap;
import com.shatteredpixel.rexbattler41.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pylon extends Mob {

	{
		spriteClass = PylonSprite.class;

		HP = HT = Dungeon.isChallenged(Challenges.STRONGER_BOSSES) ? 80 : 50;

		maxLvl = -2;

		properties.add(Property.MINIBOSS);
		properties.add(Property.INORGANIC);
		properties.add(Property.ELECTRIC);
		properties.add(Property.IMMOVABLE);

		state = PASSIVE;
		alignment = Alignment.NEUTRAL;
        switch (Dungeon.cycle){
            case 1:
                HP = HT = 450;
                break;
            case 2:
                HP = HT = 3456;
                break;
            case 3:
                HP = HT = 50000;
                break;
            case 4:
                HP = HT = 10000000;
                break;
        }
	}

	private int targetNeighbor = Random.Int(8);

	@Override
	protected boolean act() {
		alerted = false;
		super.act();

		if (alignment == Alignment.NEUTRAL){
			return true;
		}

		ArrayList<Integer> shockCells = new ArrayList<>();

		shockCells.add(pos + PathFinder.CIRCLE8[targetNeighbor]);

		if (Random.Float() < 0.5f && Dungeon.depth > 26){
			List<Class<? extends Blob>> blobs = Arrays.asList(ToxicGas.class, ConfusionGas.class, Blizzard.class, Inferno.class, Electricity.class, Web.class);
			GameScene.add(Blob.seed(pos, 500, Random.element(blobs)));
		}

		if (Dungeon.isChallenged(Challenges.STRONGER_BOSSES)){
			shockCells.add(pos + PathFinder.CIRCLE8[(targetNeighbor+3)%8]);
			shockCells.add(pos + PathFinder.CIRCLE8[(targetNeighbor+5)%8]);
		} else {
			shockCells.add(pos + PathFinder.CIRCLE8[(targetNeighbor+4)%8]);
		}

		sprite.flash();

		boolean visible = Dungeon.level.heroFOV[pos];
		for (int cell : shockCells){
			if (Dungeon.level.heroFOV[cell]){
				visible = true;
			}
		}

		if (visible) {
			for (int cell : shockCells){
				sprite.parent.add(new Lightning(sprite.center(),
						DungeonTilemap.raisedTileCenterToWorld(cell), null));
				CellEmitter.get(cell).burst(SparkParticle.FACTORY, 3);
			}
			Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
		}

		for (int cell : shockCells) {
			shockChar(Actor.findChar(cell));
		}

		targetNeighbor = (targetNeighbor+1)%8;

		return true;
	}

	private void shockChar( Char ch ){
		if (ch != null && !(ch instanceof DM300)){
			ch.sprite.flash();
			ch.damage(Random.NormalIntRange(10, 20) + Dungeon.cycle * 60, new Electricity());

			if (ch == Dungeon.hero) {
				Statistics.qualifiedForBossChallengeBadge = false;
				Statistics.bossScores[2] -= 100;
				if (!ch.isAlive()) {
					Dungeon.fail(DM300.class);
					GLog.n(Messages.get(Electricity.class, "ondeath"));
				}
			}
		}
	}

	public void activate(){
		alignment = Alignment.ENEMY;
		((PylonSprite) sprite).activate();
	}

	@Override
	public CharSprite sprite() {
		PylonSprite p = (PylonSprite) super.sprite();
		if (alignment != Alignment.NEUTRAL) p.activate();
		return p;
	}

	@Override
	public void beckon(int cell) {
		//do nothing
	}

	@Override
	public String description() {
		if (alignment == Alignment.NEUTRAL){
			return Messages.get(this, "desc_inactive");
		} else {
			return Messages.get(this, "desc_active");
		}
	}

	@Override
	public boolean interact(Char c) {
		return true;
	}

	@Override
	public void add(Buff buff) {
		//immune to all buffs/debuffs when inactive
		if (alignment != Alignment.NEUTRAL) {
			super.add(buff);
		}
	}

	@Override
	public boolean isInvulnerable(Class effect) {
		//immune to damage when inactive
		return (alignment == Alignment.NEUTRAL);
	}

	@Override
	public void damage(int dmg, Object src) {
		if (dmg >= 15 + Dungeon.cycle * 60 && Dungeon.cycle < 2){
			//takes 15/16/17/18/19/20 dmg at 15/17/20/24/29/36 incoming dmg
			dmg = 14 + Dungeon.cycle * 60 + (int)(Math.sqrt(8*(dmg - 14) + 1) - 1)/2;
		}
		super.damage(dmg, src);
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		if (Dungeon.depth == 15)
		((CavesBossLevel)Dungeon.level).eliminatePylon();
		else ((BlackMimicLevel)Dungeon.level).eliminatePylon();
	}

	private static final String ALIGNMENT = "alignment";
	private static final String TARGET_NEIGHBOUR = "target_neighbour";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(ALIGNMENT, alignment);
		bundle.put(TARGET_NEIGHBOUR, targetNeighbor);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		alignment = bundle.getEnum(ALIGNMENT, Alignment.class);
		targetNeighbor = bundle.getInt(TARGET_NEIGHBOUR);
	}

	{
		immunities.add( Paralysis.class );
		immunities.add( Amok.class );
		immunities.add( Sleep.class );
		immunities.add( Terror.class );
		immunities.add( Dread.class );
		immunities.add( Vertigo.class );
		if (Dungeon.depth == 27) immunities.add(Fire.class);
	}

}
