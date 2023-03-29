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

import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.Actor;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Invisibility;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Vulnerable;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Talent;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.warrior.HeroicLeap;
import com.rexbattler41.shadowpixeldungeon.items.armor.ClassArmor;
import com.rexbattler41.shadowpixeldungeon.items.wands.WandOfBlastWave;
import com.rexbattler41.shadowpixeldungeon.mechanics.Ballistica;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.scenes.GameScene;
import com.rexbattler41.shadowpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class StrikingDash extends ArmorAbility {

    {
        baseChargeUse = 35f;
    }

    @Override
    public String targetingPrompt() {
        return Messages.get(this, "prompt");
    }

    @Override
    public float chargeUse( Hero hero ) {
        float chargeUse = super.chargeUse(hero);
        return chargeUse;
    }

    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {
        if (target != null) {

            Ballistica route = new Ballistica(hero.pos, target, Ballistica.STOP_SOLID);
            int cell = route.collisionPos;

            //can't occupy the same cell as another char, so move back one.
            int backTrace = route.dist-1;
            while (Actor.findChar( cell ) != null && cell != hero.pos) {
                cell = route.path.get(backTrace);
                backTrace--;
            }

            armor.charge -= chargeUse( hero );
            armor.updateQuickslot();

            final int dest = cell;
            hero.busy();
            hero.sprite.jump(hero.pos, cell, new Callback() {
                @Override
                public void call() {
                    hero.move(dest);
                    Dungeon.level.occupyCell(hero);
                    Dungeon.observe();
                    GameScene.updateFog();

                    for (int i : PathFinder.NEIGHBOURS8) {
                        Char mob = Actor.findChar(hero.pos + i);
                        if (mob != null && mob != hero && mob.alignment != Char.Alignment.ALLY) {
                            if (hero.hasTalent(Talent.BODY_SLAM)){
                                int damage = Random.NormalIntRange(hero.pointsInTalent(Talent.BODY_SLAM), 4*hero.pointsInTalent(Talent.BODY_SLAM));
                                damage += Math.round(hero.drRoll()*0.25f*hero.pointsInTalent(Talent.BODY_SLAM));
                                mob.damage(damage, hero);
                            }
                            if (mob.pos == hero.pos + i && hero.hasTalent(Talent.IMPACT_WAVE)){
                                Ballistica trajectory = new Ballistica(mob.pos, mob.pos + i, Ballistica.MAGIC_BOLT);
                                int strength = 1+hero.pointsInTalent(Talent.IMPACT_WAVE);
                                WandOfBlastWave.throwChar(mob, trajectory, strength, true, true, this.getClass());
                                if (Random.Int(4) < hero.pointsInTalent(Talent.IMPACT_WAVE)){
                                    Buff.prolong(mob, Vulnerable.class, 5f);
                                }
                            }
                        }
                    }

                    WandOfBlastWave.BlastWave.blast(dest);
                    Camera.main.shake(2, 0.5f);

                    Invisibility.dispel();
                    hero.spendAndNext(Actor.TICK);

                }
            });
        }
    }

    @Override
    public Talent[] talents() {
        return new Talent[0];
    }

    @Override
    public int icon() {
        return HeroIcon.FREERUNNER;
    }
}