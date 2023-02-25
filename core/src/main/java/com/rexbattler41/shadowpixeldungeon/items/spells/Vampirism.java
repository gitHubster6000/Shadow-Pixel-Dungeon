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

package com.rexbattler41.shadowpixeldungeon.items.spells;

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.actors.Actor;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Healing;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.effects.Speck;
import com.rexbattler41.shadowpixeldungeon.effects.Splash;
import com.rexbattler41.shadowpixeldungeon.effects.particles.ShadowParticle;
import com.rexbattler41.shadowpixeldungeon.items.potions.PotionOfHealing;
import com.rexbattler41.shadowpixeldungeon.mechanics.Ballistica;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Vampirism extends TargetedSpell{

    {
        image = ItemSpriteSheet.VAMPIRISM;
    }

    @Override
    protected void affectTarget(Ballistica bolt, Hero hero) {
        int cell = bolt.collisionPos;

        Splash.at(cell, 0xCC0022, 15);

        Char target = Actor.findChar(cell);

        if (target != null && target != hero && !target.properties().contains(Char.Property.INORGANIC)){
            //33-66% of  health is transfered to user
            int heal = Random.Int(target.HT / 3, target.HT * 2 / 3);
            target.damage(heal, this);
            if (target.isAlive()){
                target.sprite.emitter().burst(ShadowParticle.CURSE, 10);
            }
            Buff.affect(hero, Healing.class).setHeal(heal, 0, heal / 10);
            hero.sprite.emitter().burst(Speck.factory(Speck.HEALING), 6);
            Sample.INSTANCE.play(Assets.Sounds.CURSED);
        }
    }

    @Override
    public int value() {
        //prices of ingredients, divided by output quantity
        return Math.round(quantity * ((30 + 43.333333f+ 40) / 3f));
    }

    public static class Recipe extends com.rexbattler41.shadowpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{PotionOfHealing.class, CurseInfusion.class, ArcaneCatalyst.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 8;

            output = Vampirism.class;
            outQuantity = 3;
        }

    }
}
