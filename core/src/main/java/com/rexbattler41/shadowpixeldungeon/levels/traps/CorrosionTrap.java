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

package com.rexbattler41.shadowpixeldungeon.levels.traps;

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.blobs.Blob;
import com.rexbattler41.shadowpixeldungeon.actors.blobs.CorrosiveGas;
import com.rexbattler41.shadowpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class CorrosionTrap extends Trap {

	{
		color = GREY;
		shape = GRILL;
	}

	@Override
	public void activate() {

		CorrosiveGas corrosiveGas = Blob.seed(pos, 80 + 5 * Dungeon.escalatingDepth(), CorrosiveGas.class);
		Sample.INSTANCE.play(Assets.Sounds.GAS);

		corrosiveGas.setStrength(1+Dungeon.escalatingDepth()/4);

		GameScene.add(corrosiveGas);

	}
}
