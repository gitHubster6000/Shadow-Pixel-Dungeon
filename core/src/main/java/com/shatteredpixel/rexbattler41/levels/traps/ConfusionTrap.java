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

package com.shatteredpixel.rexbattler41.levels.traps;

import com.shatteredpixel.rexbattler41.Assets;
import com.shatteredpixel.rexbattler41.Dungeon;
import com.shatteredpixel.rexbattler41.actors.blobs.Blob;
import com.shatteredpixel.rexbattler41.actors.blobs.ConfusionGas;
import com.shatteredpixel.rexbattler41.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class ConfusionTrap extends Trap {

	{
		color = TEAL;
		shape = GRILL;
	}

	@Override
	public void activate() {

		GameScene.add(Blob.seed(pos, 300 + 20 * Dungeon.escalatingDepth(), ConfusionGas.class));
		Sample.INSTANCE.play(Assets.Sounds.GAS);

	}
}
