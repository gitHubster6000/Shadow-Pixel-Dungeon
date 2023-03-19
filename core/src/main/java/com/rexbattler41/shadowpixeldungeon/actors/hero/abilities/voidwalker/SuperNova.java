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

import com.rexbattler41.shadowpixeldungeon.Badges;
import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.Actor;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Paralysis;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Talent;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.rexbattler41.shadowpixeldungeon.actors.mobs.Mob;
import com.rexbattler41.shadowpixeldungeon.items.armor.ClassArmor;
import com.rexbattler41.shadowpixeldungeon.items.spells.AquaBlast;
import com.rexbattler41.shadowpixeldungeon.items.wands.WandOfBlastWave;
import com.rexbattler41.shadowpixeldungeon.levels.Terrain;
import com.rexbattler41.shadowpixeldungeon.levels.features.Door;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.ui.HeroIcon;
import com.rexbattler41.shadowpixeldungeon.utils.GLog;
import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.Actor;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Cripple;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.LockedFloor;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.MagicImmune;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Talent;
import com.rexbattler41.shadowpixeldungeon.effects.Chains;
import com.rexbattler41.shadowpixeldungeon.effects.Effects;
import com.rexbattler41.shadowpixeldungeon.effects.Pushing;
import com.rexbattler41.shadowpixeldungeon.mechanics.Ballistica;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.scenes.CellSelector;
import com.rexbattler41.shadowpixeldungeon.scenes.GameScene;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;
import com.rexbattler41.shadowpixeldungeon.tiles.DungeonTilemap;
import com.rexbattler41.shadowpixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class SuperNova extends ArmorAbility {

    {
        baseChargeUse = 35f;
    }

    protected static Hero curUser = null;

    @Override
    public String targetingPrompt() {
        return Messages.get(this, "prompt");
    }

    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (Dungeon.level.heroFOV[mob.pos]) {
                //deals 10%HT, plus 0-90%HP based on scaling
                mob.damage(Math.round(mob.HT/10f + (mob.HP * 0.225f)), this);


            }
        }
        final Ballistica bolt = new Ballistica(curUser.pos, target, Ballistica.STOP_TARGET);

        pull(bolt);
    }

    public void pull(Ballistica bolt) {
        //throws the char at the center of the blast
        Char ch = Actor.findChar(bolt.collisionPos);
        if (ch != null){


            if (bolt.path.size() > bolt.dist+1 && ch.pos == bolt.collisionPos) {
                Ballistica trajectory = new Ballistica(ch.pos, bolt.path.get(bolt.dist + 1), Ballistica.MAGIC_BOLT);
                int strength = 3;
                throwChar(ch, trajectory, strength, false, true, getClass());
            }
        }
    }

    public static void throwChar(final Char ch, final Ballistica trajectory, int power,
                                 boolean closeDoors, boolean collideDmg, Class cause){
        if (ch.properties().contains(Char.Property.BOSS)) {
            power /= 2;
        }

        int dist = Math.min(trajectory.dist, power);

        boolean collided = dist == trajectory.dist;

        if (dist <= 0
                || ch.rooted
                || ch.properties().contains(Char.Property.IMMOVABLE)) return;

        //large characters cannot be moved into non-open space
        if (Char.hasProp(ch, Char.Property.LARGE)) {
            for (int i = 1; i <= dist; i++) {
                if (!Dungeon.level.openSpace[trajectory.path.get(i)]){
                    dist = i-1;
                    collided = true;
                    break;
                }
            }
        }

        if (Actor.findChar(trajectory.path.get(dist)) != null){
            dist--;
            collided = true;
        }

        if (dist < 0) return;

        final int newPos = trajectory.path.get(dist);

        if (newPos == ch.pos) return;

        final int finalDist = dist;
        final boolean finalCollided = collided && collideDmg;
        final int initialpos = ch.pos;

        Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback() {
            public void call() {
                if (initialpos != ch.pos || Actor.findChar(newPos) != null) {
                    //something caused movement or added chars before pushing resolved, cancel to be safe.
                    ch.sprite.place(ch.pos);
                    return;
                }
                int oldPos = ch.pos;
                ch.pos = newPos;
                if (finalCollided && ch.isAlive()) {
                    ch.damage(Random.NormalIntRange(finalDist, 2*finalDist), this);
                    if (ch.isAlive()) {
                        Paralysis.prolong(ch, Paralysis.class, 1 + finalDist/2f);
                    } else if (ch == Dungeon.hero){
                        if (cause == WandOfBlastWave.class || cause == AquaBlast.class){
                            Badges.validateDeathFromFriendlyMagic();
                        }
                        Dungeon.fail(cause);
                    }
                }
                if (closeDoors && Dungeon.level.map[oldPos] == Terrain.OPEN_DOOR){
                    Door.leave(oldPos);
                }
                Dungeon.level.occupyCell(ch);
                if (ch == Dungeon.hero){
                    Dungeon.observe();
                    GameScene.updateFog();
                }
            }
        }), -1);
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