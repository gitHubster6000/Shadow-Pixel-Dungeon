/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2020 Evan Debenham
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

package com.rexbattler41.shadowpixeldungeon.items.treasurebags;

import com.rexbattler41.shadowpixeldungeon.Badges;
import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.items.Generator;
import com.rexbattler41.shadowpixeldungeon.items.Gold;
import com.rexbattler41.shadowpixeldungeon.items.Item;
import com.rexbattler41.shadowpixeldungeon.items.potions.exotic.ExoticPotion;
import com.rexbattler41.shadowpixeldungeon.items.quest.GooBlob;
import com.rexbattler41.shadowpixeldungeon.items.quest.RatSkull;
import com.rexbattler41.shadowpixeldungeon.items.wands.WandOfUnstable;
import com.rexbattler41.shadowpixeldungeon.items.weapon.melee.CreativeGloves;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class GooTreasureBag extends TreasureBag{

    {
        image = ItemSpriteSheet.GOO_BAG;
    }

    @Override
    protected ArrayList<Item> items() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new GooBlob().quantity(Random.Int(2, 4)));
        items.add(new Gold().quantity(Random.Int( 400 + Dungeon.escalatingDepth() * 60, 850 + Dungeon.escalatingDepth() * 85 )));
        if (Dungeon.cycle > 0) {
            items.add(new RatSkull());
            for (int i = 0; i < 5; i++) items.add(Reflection.newInstance(ExoticPotion.exoToReg.get(Generator.randomUsingDefaults(Generator.Category.POTION).getClass())));
            for (int i = 0; i < 5; i++) items.add(Reflection.newInstance(ExoticPotion.exoToReg.get(Generator.randomUsingDefaults(Generator.Category.SCROLL).getClass())));
        }
        if (Dungeon.hero.belongings.getItem(WandOfUnstable.class) != null){
            items.add(new CreativeGloves().identify().random());
            Dungeon.hero.belongings.getItem(WandOfUnstable.class).detach(Dungeon.hero.belongings.backpack);
            Badges.validateCreative();
        }
        return items;
    }
}
