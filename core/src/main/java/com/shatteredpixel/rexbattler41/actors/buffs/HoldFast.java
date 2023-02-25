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

package com.shatteredpixel.rexbattler41.actors.buffs;

import com.shatteredpixel.rexbattler41.Dungeon;
import com.shatteredpixel.rexbattler41.messages.Messages;
import com.shatteredpixel.rexbattler41.ui.BuffIndicator;
import com.watabou.noosa.Image;

public class HoldFast extends Buff {

    {
        type = buffType.POSITIVE;
    }

    public int pos = -1;

    @Override
    public boolean act() {
        if (pos == -1) pos = target.pos;
        if (pos != target.pos) {
            detach();
        } else {
            spend(TICK);
        }
        return true;
    }

    @Override
    public int icon() {
        return BuffIndicator.ARMOR;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(1.9f, 2.4f, 3.25f);
    }

    public static int armor() {
        return Dungeon.hero.lvl/2;
    }

    public static int minArmor(){
        return Dungeon.hero.lvl/4;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", Dungeon.hero.heroClass.title(), minArmor(), armor());
    }


}
