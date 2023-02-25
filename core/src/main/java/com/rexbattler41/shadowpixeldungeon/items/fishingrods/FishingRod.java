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

package com.rexbattler41.shadowpixeldungeon.items.fishingrods;

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Doom;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.mobs.Mob;
import com.rexbattler41.shadowpixeldungeon.actors.mobs.npcs.Hook;
import com.rexbattler41.shadowpixeldungeon.effects.MagicMissile;
import com.rexbattler41.shadowpixeldungeon.items.Item;
import com.rexbattler41.shadowpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.rexbattler41.shadowpixeldungeon.mechanics.Ballistica;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.scenes.CellSelector;
import com.rexbattler41.shadowpixeldungeon.scenes.GameScene;
import com.rexbattler41.shadowpixeldungeon.utils.BArray;
import com.rexbattler41.shadowpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public abstract class FishingRod extends Item {
    public static final String AC_CAST = "CAST";
    public static final String AC_UNCAST = "UNCAST";

    {
        identify();
        defaultAction = AC_CAST;
        unique = false;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (!hook) actions.add(AC_CAST);
        else actions.add(AC_UNCAST);
        return actions;
    }

    public float amplifier = 1f;
    public int fishingStr = 1;
    public int tier;
    public boolean hook;

    public float fishingPower(){
        return level()*amplifier;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return true;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_CAST) && !hook){
            GameScene.selectCell(caster);
        }
        if (action.equals(AC_UNCAST) && hook){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (Dungeon.level.heroFOV[mob.pos] && mob instanceof Hook) {
                    for (Item b : ((Hook) mob).items) b.cast(mob, hero.pos);
                    mob.die(new Doom());
                    hook = false;
                    defaultAction = AC_CAST;
                }
            }
        }
    }

    private CellSelector.Listener caster = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {
            if (target != null && (Dungeon.level.heroFOV[target])) {

                PathFinder.buildDistanceMap(target, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
                if (PathFinder.distance[curUser.pos] == Integer.MAX_VALUE) {
                    GLog.w(Messages.get(FishingRod.class, "cant_reach"));
                    return;
                }

                final Ballistica hooking = new Ballistica(curUser.pos, target, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID);

                if (!(Dungeon.level.water[hooking.collisionPos] && !Dungeon.level.solid[hooking.collisionPos])) {
                    GLog.w(Messages.get(FishingRod.class, "no_water"));
                    return;
                }
                throwSound();
                Sample.INSTANCE.play(Assets.Sounds.CHAINS);
                hook = true;
                MagicMissile.boltFromChar(Dungeon.hero.sprite.parent, MagicMissile.MAGIC_MISSILE, Dungeon.hero.sprite, target, new Callback(){

                    @Override
                    public void call() {
                        Hook hook = new Hook();
                        hook.tier = tier;
                        hook.tries = fishingStr;
                        hook.power = (int) fishingPower();
                        GameScene.add(hook);
                        ScrollOfTeleportation.appear(hook, target);
                        defaultAction = AC_UNCAST;
                    }
                });



            } else if (target != null && !(Dungeon.level.heroFOV[target])){
                GLog.w(Messages.get(FishingRod.class, "cant_see"));
            }

        }

        @Override
        public String prompt() {
            return Messages.get(FishingRod.class, "prompt");
        }
    };

    @Override
    public Item upgrade() {
        super.upgrade();
        if (level() % 5 == 0) fishingStr++;
        return this;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("hook", hook);
        bundle.put("amp", fishingStr);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        hook = bundle.getBoolean("hook");
        if (hook) defaultAction = AC_UNCAST;
        else defaultAction = AC_CAST;
        fishingStr = bundle.getInt("amp");
    }

    @Override
    public int visiblyUpgraded() {
        return level();
    }

    @Override
    public int buffedVisiblyUpgraded() {
        return level();
    }

    @Override
    public String desc() {
        String desc = super.desc();
        desc += "\n\n" + Messages.get(FishingRod.class, "basics");
        return desc;
    }

    @Override
    public int value() {
        return 60 * Dungeon.escalatingDepth() / 5;
    }
}
