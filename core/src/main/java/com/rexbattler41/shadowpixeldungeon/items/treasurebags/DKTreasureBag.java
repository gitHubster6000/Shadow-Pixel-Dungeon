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

import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.items.Generator;
import com.rexbattler41.shadowpixeldungeon.items.Gold;
import com.rexbattler41.shadowpixeldungeon.items.Item;
import com.rexbattler41.shadowpixeldungeon.items.KingsCrown;
import com.rexbattler41.shadowpixeldungeon.items.potions.AlchemicalCatalyst;
import com.rexbattler41.shadowpixeldungeon.items.quest.KingBlade;
import com.rexbattler41.shadowpixeldungeon.items.spells.AquaBlast;
import com.rexbattler41.shadowpixeldungeon.items.spells.ArcaneCatalyst;
import com.rexbattler41.shadowpixeldungeon.items.spells.Recycle;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class DKTreasureBag extends TreasureBag {
    {
        image = ItemSpriteSheet.DK_BAG;
    }

    @Override
    protected ArrayList<Item> items() {
        ArrayList<Item> items = new ArrayList<>();
        if (!Dungeon.LimitedDrops.ARMOR_KIT.dropped()){
            items.add(new KingsCrown());
            Dungeon.LimitedDrops.ARMOR_KIT.drop();
        }
        items.add(new Gold().quantity(Random.Int( 1500 + Dungeon.escalatingDepth() * 175, 2000 + Dungeon.escalatingDepth() * 233 )));
        items.add(Generator.randomUsingDefaults(Generator.Category.ARTIFACT));
        items.add(Generator.randomUsingDefaults(Generator.Category.WEAPON));
        items.add(Generator.randomUsingDefaults(Generator.Category.ARMOR));
        items.add(Generator.randomUsingDefaults(Generator.Category.RING));
        if (Dungeon.cycle > 0){
            if (!Dungeon.LimitedDrops.DK_DROPS.dropped()){
                items.add(new KingBlade());
                Dungeon.LimitedDrops.DK_DROPS.drop();
            }
            for (int i = 0; i < 15; i++) items.add(Generator.random());
            items.add(new Recycle().quantity(50));
            items.add(new AquaBlast().quantity(50));
            items.add(new ArcaneCatalyst().quantity(50));
            items.add(new AlchemicalCatalyst().quantity(50));
        }
        return items;
    }
}
