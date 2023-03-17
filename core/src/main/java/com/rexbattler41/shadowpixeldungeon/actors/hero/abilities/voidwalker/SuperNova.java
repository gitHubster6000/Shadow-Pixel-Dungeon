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
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Talent;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.rexbattler41.shadowpixeldungeon.actors.mobs.Mob;
import com.rexbattler41.shadowpixeldungeon.items.armor.ClassArmor;
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
    protected void activate(ClassArmor armor, Hero hero, Integer target) {
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (Dungeon.level.heroFOV[mob.pos]) {
                //deals 10%HT, plus 0-90%HP based on scaling
                mob.damage(Math.round(mob.HT/10f + (mob.HP * 0.225f)), this);
            }
        }

        final Ballistica chain = new Ballistica(curUser.pos, target, Ballistica.STOP_TARGET);

        pull( chain, curUser, Actor.findChar( chain.collisionPos ));

    }

    private void pull( Ballistica chain, final Hero hero, final Char enemy ) {
        int bestPos = -1;
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (Dungeon.level.heroFOV[mob.pos]) {
                for (int i : chain.subPath(1, chain.dist)){
                    //prefer to the earliest point on the path
                    if (!Dungeon.level.solid[i]
                            && Actor.findChar(i) == null
                            && (!Char.hasProp(enemy, Char.Property.LARGE))){
                        bestPos = i;
                        break;
                    }
                }
            }
        }

        final int pulledPos = bestPos;

        hero.busy();
        Sample.INSTANCE.play( Assets.Sounds.CHAINS );
        hero.sprite.parent.add(new Chains(hero.sprite.center(),
                enemy.sprite.center(),
                Effects.Type.ETHEREAL_CHAIN,
                new Callback() {
                    public void call() {
                        Actor.add(new Pushing(enemy, enemy.pos, pulledPos, new Callback() {
                            public void call() {
                                enemy.pos = pulledPos;
                                Dungeon.level.occupyCell(enemy);
                                Dungeon.observe();
                                GameScene.updateFog();
                                hero.spendAndNext(1f);
                            }
                        }));
                        hero.next();
                    }
                }));
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