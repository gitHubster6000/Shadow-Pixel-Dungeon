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

package com.shatteredpixel.rexbattler41.items.scrolls.exotic;

import com.shatteredpixel.rexbattler41.Assets;
import com.shatteredpixel.rexbattler41.Dungeon;
import com.shatteredpixel.rexbattler41.actors.hero.Belongings;
import com.shatteredpixel.rexbattler41.actors.hero.Talent;
import com.shatteredpixel.rexbattler41.effects.Enchanting;
import com.shatteredpixel.rexbattler41.items.Item;
import com.shatteredpixel.rexbattler41.items.armor.Armor;
import com.shatteredpixel.rexbattler41.items.bags.Bag;
import com.shatteredpixel.rexbattler41.items.scrolls.InventoryScroll;
import com.shatteredpixel.rexbattler41.items.stones.StoneOfEnchantment;
import com.shatteredpixel.rexbattler41.items.weapon.SpiritBow;
import com.shatteredpixel.rexbattler41.items.weapon.Weapon;
import com.shatteredpixel.rexbattler41.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.rexbattler41.messages.Messages;
import com.shatteredpixel.rexbattler41.scenes.GameScene;
import com.shatteredpixel.rexbattler41.sprites.ItemSprite;
import com.shatteredpixel.rexbattler41.sprites.ItemSpriteSheet;
import com.shatteredpixel.rexbattler41.ui.Icons;
import com.shatteredpixel.rexbattler41.utils.GLog;
import com.shatteredpixel.rexbattler41.windows.WndBag;
import com.shatteredpixel.rexbattler41.windows.WndOptions;
import com.shatteredpixel.rexbattler41.windows.WndTitledMessage;
import com.watabou.noosa.audio.Sample;

public class ScrollOfEnchantment extends ExoticScroll {
	
	{
		icon = ItemSpriteSheet.Icons.SCROLL_ENCHANT;

		unique = true;
	}

	protected static boolean identifiedByUse = false;
	
	@Override
	public void doRead() {
		if (!isKnown()) {
			identify();
			identifiedByUse = true;
		} else {
			identifiedByUse = false;
		}
		GameScene.selectItem( itemSelector );
	}

	public static boolean enchantable( Item item ){
		return (item instanceof MeleeWeapon || item instanceof SpiritBow || item instanceof Armor);
	}

	private void confirmCancelation() {
		GameScene.show( new WndOptions(new ItemSprite(this),
				Messages.titleCase(name()),
				Messages.get(InventoryScroll.class, "warning"),
				Messages.get(InventoryScroll.class, "yes"),
				Messages.get(InventoryScroll.class, "no") ) {
			@Override
			protected void onSelect( int index ) {
				switch (index) {
					case 0:
						curUser.spendAndNext( TIME_TO_READ );
						identifiedByUse = false;
						break;
					case 1:
						GameScene.selectItem(itemSelector);
						break;
				}
			}
			public void onBackPressed() {}
		} );
	}
	
	protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

		@Override
		public String textPrompt() {
			return Messages.get(ScrollOfEnchantment.class, "inv_title");
		}

		@Override
		public Class<?extends Bag> preferredBag(){
			return Belongings.Backpack.class;
		}

		@Override
		public boolean itemSelectable(Item item) {
			return enchantable(item);
		}

		@Override
		public void onSelect(final Item item) {
			
			if (item instanceof Weapon){
				
				final Weapon.Enchantment enchants[] = new Weapon.Enchantment[3];
				
				Class<? extends Weapon.Enchantment> existing = ((Weapon) item).enchantment != null ? ((Weapon) item).enchantment.getClass() : null;
				enchants[0] = Weapon.Enchantment.randomCommon( existing );
				enchants[1] = Weapon.Enchantment.randomUncommon( existing );
				enchants[2] = Weapon.Enchantment.random( existing, enchants[0].getClass(), enchants[1].getClass());
				
				GameScene.show(new WndOptions(new ItemSprite(ScrollOfEnchantment.this),
						Messages.titleCase(ScrollOfEnchantment.this.name()),
						Messages.get(ScrollOfEnchantment.class, "weapon") +
						"\n\n" +
						Messages.get(ScrollOfEnchantment.class, "cancel_warn"),
						enchants[0].name(),
						enchants[1].name(),
						enchants[2].name(),
						Messages.get(ScrollOfEnchantment.class, "cancel")){
					
					@Override
					protected void onSelect(int index) {
						if (index < 3) {
							((Weapon) item).enchant(enchants[index]);
							GLog.p(Messages.get(StoneOfEnchantment.class, "weapon"));
							((ScrollOfEnchantment)curItem).readAnimation();
							
							Sample.INSTANCE.play( Assets.Sounds.READ );
							Enchanting.show(curUser, item);
							Talent.onUpgradeScrollUsed( Dungeon.hero );
						}
					}
					
					@Override
					protected boolean hasInfo(int index) {
						return index < 3;
					}

					@Override
					protected void onInfo( int index ) {
						GameScene.show(new WndTitledMessage(
								Icons.get(Icons.INFO),
								Messages.titleCase(enchants[index].name()),
								enchants[index].desc()));
					}

					@Override
					public void onBackPressed() {
						//do nothing, reader has to cancel
					}
				});
			
			} else if (item instanceof Armor) {
				
				final Armor.Glyph glyphs[] = new Armor.Glyph[3];
				
				Class<? extends Armor.Glyph> existing = ((Armor) item).glyph != null ? ((Armor) item).glyph.getClass() : null;
				glyphs[0] = Armor.Glyph.randomCommon( existing );
				glyphs[1] = Armor.Glyph.randomUncommon( existing );
				glyphs[2] = Armor.Glyph.random( existing, glyphs[0].getClass(), glyphs[1].getClass());
				
				GameScene.show(new WndOptions( new ItemSprite(ScrollOfEnchantment.this),
						Messages.titleCase(ScrollOfEnchantment.this.name()),
						Messages.get(ScrollOfEnchantment.class, "armor") +
						"\n\n" +
						Messages.get(ScrollOfEnchantment.class, "cancel_warn"),
						glyphs[0].name(),
						glyphs[1].name(),
						glyphs[2].name(),
						Messages.get(ScrollOfEnchantment.class, "cancel")){
					
					@Override
					protected void onSelect(int index) {
						if (index < 3) {
							((Armor) item).inscribe(glyphs[index]);
							GLog.p(Messages.get(StoneOfEnchantment.class, "armor"));
							((ScrollOfEnchantment)curItem).readAnimation();
							
							Sample.INSTANCE.play( Assets.Sounds.READ );
							Enchanting.show(curUser, item);
							Talent.onUpgradeScrollUsed( Dungeon.hero );
						}
					}

					@Override
					protected boolean hasInfo(int index) {
						return index < 3;
					}

					@Override
					protected void onInfo( int index ) {
						GameScene.show(new WndTitledMessage(
								Icons.get(Icons.INFO),
								Messages.titleCase(glyphs[index].name()),
								glyphs[index].desc()));
					}
					
					@Override
					public void onBackPressed() {
						//do nothing, reader has to cancel
					}
				});
			} else {
				if (!identifiedByUse){
					curItem.collect();
				} else {
					((ScrollOfEnchantment)curItem).confirmCancelation();
				}
			}
		}
	};
}
