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

package com.rexbattler41.shadowpixeldungeon;

import com.rexbattler41.shadowpixeldungeon.items.potions.exotic.PotionOfHolyFuror;
import com.rexbattler41.shadowpixeldungeon.scenes.GameScene;
import com.rexbattler41.shadowpixeldungeon.scenes.PixelScene;
import com.rexbattler41.shadowpixeldungeon.scenes.TitleScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PlatformSupport;

public class shadowpixeldungeon extends Game {

	//variable constants for specific older versions of shattered, used for data conversion
	//versions older than v1.0.3 are no longer supported, and data from them is ignored
	public static final int v0_1_0 = 1;
	
	public shadowpixeldungeon(PlatformSupport platform ) {
		super( sceneClass == null ? TitleScene.class : sceneClass, platform );

		//pre-v1.3.0
		com.watabou.utils.Bundle.addAlias(
				com.rexbattler41.shadowpixeldungeon.actors.buffs.Bleeding.class,
				"com.shatteredpixel.shadowpixeldungeon.levels.features.Chasm$FallBleed" );
		com.watabou.utils.Bundle.addAlias(
				com.rexbattler41.shadowpixeldungeon.plants.Mageroyal.class,
				"com.shatteredpixel.shadowpixeldungeon.plants.Dreamfoil" );
		com.watabou.utils.Bundle.addAlias(
				com.rexbattler41.shadowpixeldungeon.plants.Mageroyal.Seed.class,
				"com.shatteredpixel.shadowpixeldungeon.plants.Dreamfoil$Seed" );

		com.watabou.utils.Bundle.addAlias(
				com.rexbattler41.shadowpixeldungeon.items.weapon.curses.Dazzling.class,
				"com.shatteredpixel.shadowpixeldungeon.items.weapon.curses.Exhausting" );
		com.watabou.utils.Bundle.addAlias(
				com.rexbattler41.shadowpixeldungeon.items.weapon.curses.Explosive.class,
				"com.shatteredpixel.shadowpixeldungeon.items.weapon.curses.Fragile" );

		//pre-v1.2.0
		com.watabou.utils.Bundle.addAlias(
				com.rexbattler41.shadowpixeldungeon.items.weapon.missiles.darts.CleansingDart.class,
				"com.shatteredpixel.shadowpixeldungeon.items.weapon.missiles.darts.SleepDart" );

		com.watabou.utils.Bundle.addAlias(
				com.rexbattler41.shadowpixeldungeon.levels.rooms.special.CrystalVaultRoom.class,
				"com.shatteredpixel.shadowpixeldungeon.levels.rooms.special.VaultRoom" );

		//pre-v1.1.0
		com.watabou.utils.Bundle.addAlias(
				com.rexbattler41.shadowpixeldungeon.items.scrolls.exotic.ScrollOfDread.class,
				"com.shatteredpixel.shadowpixeldungeon.items.scrolls.exotic.ScrollOfPetrification" );
		com.watabou.utils.Bundle.addAlias(
				com.rexbattler41.shadowpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong.class,
				"com.shatteredpixel.shadowpixeldungeon.items.scrolls.exotic.ScrollOfAffection" );
		com.watabou.utils.Bundle.addAlias(
				PotionOfHolyFuror.class,
				"com.shatteredpixel.shadowpixeldungeon.items.potions.exotic.PotionOfHolyFuror" );
		com.watabou.utils.Bundle.addAlias(
				com.rexbattler41.shadowpixeldungeon.items.potions.exotic.PotionOfMastery.class,
				"com.shatteredpixel.shadowpixeldungeon.items.potions.exotic.PotionOfAdrenalineSurge" );
		
	}
	
	@Override
	public void create() {
		super.create();

		updateSystemUI();
		SPDAction.loadBindings();
		
		Music.INSTANCE.enable( SPDSettings.music() );
		Music.INSTANCE.volume( SPDSettings.musicVol()*SPDSettings.musicVol()/100f );
		Sample.INSTANCE.enable( SPDSettings.soundFx() );
		Sample.INSTANCE.volume( SPDSettings.SFXVol()*SPDSettings.SFXVol()/100f );

		Sample.INSTANCE.load( Assets.Sounds.all );
		
	}

	@Override
	public void finish() {
		if (!DeviceCompat.isiOS()) {
			super.finish();
		} else {
			//can't exit on iOS (Apple guidelines), so just go to title screen
			switchScene(TitleScene.class);
		}
	}

	public static void switchNoFade(Class<? extends PixelScene> c){
		switchNoFade(c, null);
	}

	public static void switchNoFade(Class<? extends PixelScene> c, SceneChangeCallback callback) {
		PixelScene.noFade = true;
		switchScene( c, callback );
	}
	
	public static void seamlessResetScene(SceneChangeCallback callback) {
		if (scene() instanceof PixelScene){
			((PixelScene) scene()).saveWindows();
			switchNoFade((Class<? extends PixelScene>) sceneClass, callback );
		} else {
			resetScene();
		}
	}
	
	public static void seamlessResetScene(){
		seamlessResetScene(null);
	}
	
	@Override
	protected void switchScene() {
		super.switchScene();
		if (scene instanceof PixelScene){
			((PixelScene) scene).restoreWindows();
		}
	}
	
	@Override
	public void resize( int width, int height ) {
		if (width == 0 || height == 0){
			return;
		}

		if (scene instanceof PixelScene &&
				(height != Game.height || width != Game.width)) {
			PixelScene.noFade = true;
			((PixelScene) scene).saveWindows();
		}

		super.resize( width, height );

		updateDisplaySize();

	}
	
	@Override
	public void destroy(){
		super.destroy();
		GameScene.endActorThread();
	}
	
	public void updateDisplaySize(){
		platform.updateDisplaySize();
	}

	public static void updateSystemUI() {
		platform.updateSystemUI();
	}
}