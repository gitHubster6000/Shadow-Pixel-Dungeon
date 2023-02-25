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

package com.rexbattler41.shadowpixeldungeon.items.potions.exotic;

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.Actor;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.blobs.Blob;
import com.rexbattler41.shadowpixeldungeon.actors.blobs.Fire;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Burning;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Cripple;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.effects.MagicMissile;
import com.rexbattler41.shadowpixeldungeon.levels.Level;
import com.rexbattler41.shadowpixeldungeon.levels.Terrain;
import com.rexbattler41.shadowpixeldungeon.mechanics.Ballistica;
import com.rexbattler41.shadowpixeldungeon.mechanics.ConeAOE;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.scenes.CellSelector;
import com.rexbattler41.shadowpixeldungeon.scenes.GameScene;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSprite;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;
import com.rexbattler41.shadowpixeldungeon.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class PotionOfDragonsBreath extends ExoticPotion {
	
	{
		icon = ItemSpriteSheet.Icons.POTION_DRGBREATH;
	}

	protected static boolean identifiedByUse = false;

	@Override
	//need to override drink so that time isn't spent right away
	protected void drink(final Hero hero) {
		curUser = hero;
		curItem = detach( hero.belongings.backpack );

		if (!isKnown()) {
			identify();
			identifiedByUse = true;
		} else {
			identifiedByUse = false;
		}

		GameScene.selectCell(targeter);
	}
	
	private CellSelector.Listener targeter = new CellSelector.Listener() {

		private boolean showingWindow = false;
		private boolean potionAlreadyUsed = false;

		@Override
		public void onSelect(final Integer cell) {

			if (showingWindow){
				return;
			}
			if (potionAlreadyUsed){
				potionAlreadyUsed = false;
				return;
			}

			if (cell == null && identifiedByUse){
				showingWindow = true;
				GameScene.show( new WndOptions(new ItemSprite(PotionOfDragonsBreath.this),
						Messages.titleCase(name()),
						Messages.get(ExoticPotion.class, "warning"),
						Messages.get(ExoticPotion.class, "yes"),
						Messages.get(ExoticPotion.class, "no") ) {
					@Override
					protected void onSelect( int index ) {
						showingWindow = false;
						switch (index) {
							case 0:
								curUser.spendAndNext(1f);
								identifiedByUse = false;
								break;
							case 1:
								GameScene.selectCell( targeter );
								break;
						}
					}
					public void onBackPressed() {}
				} );
			} else if (cell == null && !anonymous){
				curItem.collect( curUser.belongings.backpack );
			} else if (cell != null) {
				potionAlreadyUsed = true;
				identifiedByUse = false;
				curUser.busy();
				Sample.INSTANCE.play( Assets.Sounds.DRINK );
				curUser.sprite.operate(curUser.pos, new Callback() {
					@Override
					public void call() {

						curItem.detach(curUser.belongings.backpack);

						curUser.sprite.idle();
						curUser.sprite.zap(cell);
						Sample.INSTANCE.play( Assets.Sounds.BURNING );

						final Ballistica bolt = new Ballistica(curUser.pos, cell, Ballistica.WONT_STOP);

						int maxDist = 6;
						int dist = Math.min(bolt.dist, maxDist);

						final ConeAOE cone = new ConeAOE(bolt, 6, 60, Ballistica.STOP_SOLID | Ballistica.STOP_TARGET | Ballistica.IGNORE_SOFT_SOLID );

						//cast to cells at the tip, rather than all cells, better performance.
						for (Ballistica ray : cone.outerRays){
							((MagicMissile)curUser.sprite.parent.recycle( MagicMissile.class )).reset(
									MagicMissile.FIRE_CONE,
									curUser.sprite,
									ray.path.get(ray.dist),
									null
							);
						}
						
						MagicMissile.boltFromChar(curUser.sprite.parent,
								MagicMissile.FIRE_CONE,
								curUser.sprite,
								bolt.path.get(dist / 2),
								new Callback() {
									@Override
									public void call() {
										ArrayList<Integer> adjacentCells = new ArrayList<>();
										for (int cell : cone.cells){
											//ignore caster cell
											if (cell == bolt.sourcePos){
												continue;
											}

											//knock doors open
											if (Dungeon.level.map[cell] == Terrain.DOOR){
												Level.set(cell, Terrain.OPEN_DOOR);
												GameScene.updateMap(cell);
											}

											//only ignite cells directly near caster if they are flammable
											if (Dungeon.level.adjacent(bolt.sourcePos, cell) && !Dungeon.level.flamable[cell]){
												adjacentCells.add(cell);
											} else {
												GameScene.add( Blob.seed( cell, 5, Fire.class ) );
											}
											
											Char ch = Actor.findChar( cell );
											if (ch != null) {
												
												Buff.affect( ch, Burning.class ).reignite( ch );
												Buff.affect(ch, Cripple.class, 5f);
											}
										}

										//ignite cells that share a side with an adjacent cell, are flammable, and are further from the source pos
										//This prevents short-range casts not igniting barricades or bookshelves
										for (int cell : adjacentCells){
											for (int i : PathFinder.NEIGHBOURS4){
												if (Dungeon.level.trueDistance(cell+i, bolt.sourcePos) > Dungeon.level.trueDistance(cell, bolt.sourcePos)
														&& Dungeon.level.flamable[cell+i]
														&& Fire.volumeAt(cell+i, Fire.class) == 0){
													GameScene.add( Blob.seed( cell+i, 5, Fire.class ) );
												}
											}
										}

										curUser.spendAndNext(1f);
									}
								});
						
					}
				});
			}
		}
		
		@Override
		public String prompt() {
			return Messages.get(PotionOfDragonsBreath.class, "prompt");
		}
	};
}
