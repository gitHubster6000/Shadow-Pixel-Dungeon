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
import com.rexbattler41.shadowpixeldungeon.actors.hero.Perks;
import com.rexbattler41.shadowpixeldungeon.items.Generator;
import com.rexbattler41.shadowpixeldungeon.items.Item;
import com.rexbattler41.shadowpixeldungeon.items.armor.Armor;
import com.rexbattler41.shadowpixeldungeon.items.artifacts.Artifact;
import com.rexbattler41.shadowpixeldungeon.items.potions.exotic.ExoticPotion;
import com.rexbattler41.shadowpixeldungeon.items.rings.Ring;
import com.rexbattler41.shadowpixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.rexbattler41.shadowpixeldungeon.items.wands.Wand;
import com.rexbattler41.shadowpixeldungeon.items.weapon.Weapon;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class QualityBag extends TreasureBag {
    {
        image = ItemSpriteSheet.QUALITY_BAG;
    }

    @Override
    protected ArrayList<Item> items() {
        ArrayList<Item> items = new ArrayList<>();
        int amount = 1;
        if (Dungeon.hero.perks.contains(Perks.Perk.MORE_BAG) && Random.Int(2) == 0) amount = 2;
        for (int i = 0; i < amount; i++) {
            Weapon wep = Generator.randomWeapon(Dungeon.depth + 1);
            wep.cursed = false;
            if (wep.hasCurseEnchant()) wep.enchant();
            wep.identify();
            items.add(wep);

            Armor arm = Generator.randomArmor(Dungeon.depth + 1);
            arm.cursed = false;
            if (arm.hasCurseGlyph()) arm.inscribe();
            arm.identify();
            items.add(arm);

            Ring ring = (Ring) Generator.random(Generator.Category.RING);
            ring.cursed = false;
            ring.identify();
            items.add(ring);

            Artifact artifact = Generator.randomArtifact();
            if (artifact != null) {
                artifact.cursed = false;
                artifact.identify();
                items.add(artifact);
            }

            items.add(Generator.randomUsingDefaults(Generator.Category.SCROLL).random());
            items.add(Reflection.newInstance(ExoticPotion.exoToReg.get(Generator.randomUsingDefaults(Generator.Category.SCROLL).getClass())));
            items.add(Generator.randomUsingDefaults(Generator.Category.POTION).random());
            items.add(Reflection.newInstance(ExoticScroll.exoToReg.get(Generator.randomUsingDefaults(Generator.Category.POTION).getClass())));

            items.add(Generator.random(Generator.Category.FOOD));
            items.add(Generator.random(Generator.Category.STONE));
            Wand wand = (Wand) Generator.random(Generator.Category.WAND);
            wand.cursed = false;
            items.add(wand);

            items.add(Generator.random(Generator.Category.SEED));
        }

        return items;
    }

    @Override
    public int value() {
        return 400 * quantity;
    }
}
