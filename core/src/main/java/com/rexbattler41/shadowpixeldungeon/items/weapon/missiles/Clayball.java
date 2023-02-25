/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.rexbattler41.shadowpixeldungeon.items.weapon.missiles;

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.Actor;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.effects.Splash;
import com.rexbattler41.shadowpixeldungeon.items.armor.glyphs.Viscosity;
import com.rexbattler41.shadowpixeldungeon.items.wands.WandOfBlastWave;
import com.rexbattler41.shadowpixeldungeon.mechanics.ShadowCaster;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;
import com.rexbattler41.shadowpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Point;

import java.util.ArrayList;

public class Clayball extends MissileWeapon {
	
	{
		image = ItemSpriteSheet.CUDGEL;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;
		
		bones = false;
		
		internalTier = tier = 1;
		baseUses = 1;
		sticky = false;
	}

	@Override
	public float durabilityPerUse() {
		return 1000f;
	}

	@Override
	public int value() {
		return 826 * quantity;
	}

	@Override
	protected void onThrow(int cell) {
		if (Dungeon.level.pit[cell]){
			super.onThrow(cell);
			return;
		}

		Dungeon.level.pressCell(cell);

		ArrayList<Char> targets = new ArrayList<>();
		if (Actor.findChar(cell) != null) targets.add(Actor.findChar(cell));

		boolean[] FOV = new boolean[Dungeon.level.length()];
		Point c = Dungeon.level.cellToPoint(cell);
		ShadowCaster.castShadow(c.x, c.y, FOV, Dungeon.level.losBlocking, 8);

		for (int i = 0; i < FOV.length; i++) {
			if (FOV[i]) {
				if (Dungeon.level.heroFOV[i] && !Dungeon.level.solid[i]) {
					//TODO better vfx?
					Splash.at(i, 0x838d99, 25);
				}
				Char ch = Actor.findChar(i);
				if (ch != null){
					targets.add(ch);
				}
			}
		}

		for (Char target : targets){
			if (target != Dungeon.hero) Buff.affect(target, Viscosity.DeferedDamage.class).prolong(target.HT*10);
			if (target == Dungeon.hero && !target.isAlive()){
				Dungeon.fail(getClass());
				GLog.n(Messages.get(this, "ondeath"));
			}
		}

		rangedHit( null, cell );

		WandOfBlastWave.BlastWave.blast(cell);
		Sample.INSTANCE.play( Assets.Sounds.BLAST );
	}


}
