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
 * Shadow Pixel Dungeon
 * Copyright (C) 2023 Rexbattler41
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

package com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.voidwalker;

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.actors.Actor;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Combo;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Cripple;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Invisibility;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Paralysis;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.hero.HeroSubClass;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Talent;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.rexbattler41.shadowpixeldungeon.effects.MagicMissile;
import com.rexbattler41.shadowpixeldungeon.items.Item;
import com.rexbattler41.shadowpixeldungeon.items.armor.ClassArmor;
import com.rexbattler41.shadowpixeldungeon.mechanics.Ballistica;
import com.rexbattler41.shadowpixeldungeon.mechanics.ConeAOE;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.ui.HeroIcon;
import com.rexbattler41.shadowpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class SuperNova extends ArmorAbility {
    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {
        if (target == null){
            return;
        }
        if (target == hero.pos){
            GLog.w(Messages.get(this, "self_target"));
            return;
        }
        hero.busy();

        armor.charge -= chargeUse(hero);
        Item.updateQuickslot();

        Ballistica aim = new Ballistica(hero.pos, target, Ballistica.WONT_STOP);

        int maxDist = 5 + hero.pointsInTalent(Talent.EXPANDING_WAVE);
        int dist = Math.min(aim.dist, maxDist);

        ConeAOE cone = new ConeAOE(aim,
                dist,
                360,
                Ballistica.STOP_SOLID | Ballistica.STOP_TARGET);

        //cast to cells at the tip, rather than all cells, better performance.
        for (Ballistica ray : cone.outerRays){
            ((MagicMissile)hero.sprite.parent.recycle( MagicMissile.class )).reset(
                    MagicMissile.FORCE_CONE,
                    hero.sprite,
                    ray.path.get(ray.dist),
                    null
            );
        }

        hero.sprite.zap(target);
        Sample.INSTANCE.play(Assets.Sounds.BLAST, 1f, 0.5f);
        Camera.main.shake(2, 0.5f);
        //final zap at 2/3 distance, for timing of the actual effect
        MagicMissile.boltFromChar(hero.sprite.parent,
                MagicMissile.FORCE_CONE,
                hero.sprite,
                cone.coreRay.path.get(dist * 2 / 3),
                new Callback() {
                    @Override
                    public void call() {

                        for (int cell : cone.cells){

                            Char ch = Actor.findChar(cell);
                            if (ch != null && ch.alignment != hero.alignment){
                                int scalingStr = hero.STR()-10;
                                int damage = Random.NormalIntRange(5 + scalingStr, 10 + 2*scalingStr);
                                damage = Math.round(damage * 1f);
                                damage -= ch.drRoll();
                                ch.damage(damage, hero);

                                if (ch.isAlive()){
                                    Buff.affect(ch, Paralysis.class, 10f);
                                }

                            }
                        }

                        Invisibility.dispel();
                        hero.spendAndNext(Actor.TICK);

                    }
                });
    }

    @Override
    public Talent[] talents() {
        return new Talent[0];
    }

    @Override
    public int icon() {
        return HeroIcon.ELEMENTAL_BLAST;
    }
}