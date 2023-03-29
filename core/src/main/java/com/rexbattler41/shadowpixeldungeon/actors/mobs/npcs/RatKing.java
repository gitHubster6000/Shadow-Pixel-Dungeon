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

package com.rexbattler41.shadowpixeldungeon.actors.mobs.npcs;

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.Badges;
import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Perks;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.Ratmogrify;
import com.rexbattler41.shadowpixeldungeon.effects.CellEmitter;
import com.rexbattler41.shadowpixeldungeon.effects.Speck;
import com.rexbattler41.shadowpixeldungeon.items.*;
import com.rexbattler41.shadowpixeldungeon.items.food.Cheese;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.scenes.GameScene;
import com.rexbattler41.shadowpixeldungeon.sprites.RatKingSprite;
import com.rexbattler41.shadowpixeldungeon.windows.WndInfoArmorAbility;
import com.rexbattler41.shadowpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collection;

public class RatKing extends NPC {

    public boolean ghastly = false;
    public int counter = 0;

	{
		spriteClass = RatKingSprite.class;
		
		state = SLEEPING;

		HP = HT = 2000;
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return INFINITE_EVASION;
	}
	
	@Override
	public float speed() {
		return 2f;
	}
	
	@Override
	protected Char chooseEnemy() {
		return null;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	    if (buff instanceof Barter || buff instanceof MirrorImage.MirrorInvis) super.add(buff);
	}
	
	@Override
	public boolean reset() {
		return true;
	}

	//***This functionality is for when rat king may be summoned by a distortion trap

	@Override
	protected void onAdd() {
		super.onAdd();
		if (Dungeon.depth != 5 && !ghastly){
			yell(Messages.get(this, "confused"));
		}
	}

	@Override
	protected boolean act() {
        if (!ghastly) {
            if (Dungeon.depth < 5){
                if (pos == Dungeon.level.exit()){
                    destroy();
                    sprite.killAndErase();
                } else {
                    target = Dungeon.level.exit();
                }
            } else if (Dungeon.depth > 5){
                if (pos == Dungeon.level.entrance()){
                    destroy();
                    sprite.killAndErase();
                } else {
                    target = Dungeon.level.entrance();
                }
            }
        }
        Heap heap = Dungeon.level.heaps.get(pos );
        Barter barter = Buff.affect(this, Barter.class);
		if (heap != null){
		    Item item = heap.pickUp();
		    barter.stick(item);
            CellEmitter.get( pos ).burst( Speck.factory( Speck.WOOL ), 6 );
            Sample.INSTANCE.play( Assets.Sounds.PUFF );
        }
		if (barter.items.size() > 0){
		    if (!Dungeon.hero.perks.contains(Perks.Perk.BETTER_BARTERING) || Random.Int(3) != 0)
		        barter.items.remove(barter.items.size() - 1);
            Item item;
            do {
		            item = Generator.random();
                } while (item instanceof Gold);
            if (++counter == 50){
                counter = 0;
                item = new Cheese();
            }
            item.cast(this, Dungeon.hero.pos);

            spend(2f);
        }
		return super.act();
	}

	//***

	@Override
	public boolean interact(Char c) {
		sprite.turnTo( pos, c.pos );

		if (c != Dungeon.hero){
			return super.interact(c);
		}

		if (state == SLEEPING) {
			notice();
			yell( Messages.get(this, "not_sleeping") );
			state = WANDERING;
		} else {
			yell( Messages.get(this, "what_is_it") );
		}

		KingsCrown crown = Dungeon.hero.belongings.getItem(KingsCrown.class);
		if (state == SLEEPING) {
			notice();
			yell( Messages.get(this, "not_sleeping") );
			state = WANDERING;
		} else if (crown != null){
			if (Dungeon.hero.belongings.armor() == null){
				yell( Messages.get(RatKing.class, "crown_clothes") );
			} else {
				Badges.validateRatmogrify();
				Game.runOnRenderThread(new Callback() {
					@Override
					public void call() {
						GameScene.show(new WndOptions(
								sprite(),
								Messages.titleCase(name()),
								Messages.get(RatKing.class, "crown_desc"),
								Messages.get(RatKing.class, "crown_yes"),
								Messages.get(RatKing.class, "crown_info"),
								Messages.get(RatKing.class, "crown_no")
						){
							@Override
							protected void onSelect(int index) {
								if (index == 0){
									crown.upgradeArmor(Dungeon.hero, Dungeon.hero.belongings.armor(), new Ratmogrify());
									((RatKingSprite)sprite).resetAnims();
									yell(Messages.get(RatKing.class, "crown_thankyou"));
								} else if (index == 1) {
									GameScene.show(new WndInfoArmorAbility(Dungeon.hero.heroClass, new Ratmogrify()));
								} else {
									yell(Messages.get(RatKing.class, "crown_fine"));
								}
							}
						});
					}
				});
			}
		} else if (Dungeon.hero.armorAbility instanceof Ratmogrify) {
			yell( Messages.get(RatKing.class, "crown_after") );
		} else {
			yell( Messages.get(this, "what_is_it") );
		}
		return true;
	}
	
	@Override
	public String description() {
		return ((RatKingSprite)sprite).festive ?
				Messages.get(this, "desc_festive")
				: ghastly ? Messages.get(this, "ghastly") : super.description();
	}

    public static class Barter extends Buff {

        private ArrayList<Item> items = new ArrayList<>();

        public void stick(Item heh){
            for (Item item : items){
                if (item.isSimilar(heh)){
                    item.merge(heh);
                    return;
                }
            }
            items.add(heh);
        }

        @Override
        public void detach() {
            for (Item item : items)
                Dungeon.level.drop( item, target.pos).sprite.drop();
            super.detach();
        }

        private static final String ITEMS = "items";

        @Override
        public void storeInBundle(Bundle bundle) {
            bundle.put( ITEMS , items );
            super.storeInBundle(bundle);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            items = new ArrayList<>((Collection<Item>) ((Collection<?>) bundle.getCollection(ITEMS)));
            super.restoreFromBundle( bundle );
        }


    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("h", ghastly);
        bundle.put("e", counter);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        ghastly = bundle.getBoolean("h");
        if (bundle.contains("e")){
            counter = bundle.getInt("e");
        }
    }
}