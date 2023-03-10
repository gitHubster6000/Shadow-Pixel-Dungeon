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

package com.rexbattler41.shadowpixeldungeon.items.armor.glyphs;

import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Charm;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Degrade;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Hex;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.MagicalSleep;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Vulnerable;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Weakness;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.rexbattler41.shadowpixeldungeon.actors.mobs.DM100;
import com.rexbattler41.shadowpixeldungeon.actors.mobs.Eye;
import com.rexbattler41.shadowpixeldungeon.actors.mobs.Shaman;
import com.rexbattler41.shadowpixeldungeon.actors.mobs.Warlock;
import com.rexbattler41.shadowpixeldungeon.actors.mobs.YogFist;
import com.rexbattler41.shadowpixeldungeon.items.armor.Armor;
import com.rexbattler41.shadowpixeldungeon.items.bombs.Bomb;
import com.rexbattler41.shadowpixeldungeon.items.rings.RingOfArcana;
import com.rexbattler41.shadowpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.rexbattler41.shadowpixeldungeon.items.wands.*;
import com.rexbattler41.shadowpixeldungeon.levels.traps.DisintegrationTrap;
import com.rexbattler41.shadowpixeldungeon.levels.traps.GrimTrap;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSprite;

import java.util.HashSet;

public class AntiMagic extends Armor.Glyph {

	private static ItemSprite.Glowing TEAL = new ItemSprite.Glowing( 0x88EEFF );
	
	public static final HashSet<Class> RESISTS = new HashSet<>();
	static {
		RESISTS.add( MagicalSleep.class );
		RESISTS.add( Charm.class );
		RESISTS.add( Weakness.class );
		RESISTS.add( Vulnerable.class );
		RESISTS.add( Hex.class );
		RESISTS.add( Degrade.class );
		
		RESISTS.add( DisintegrationTrap.class );
		RESISTS.add( GrimTrap.class );

		RESISTS.add( Bomb.MagicalBomb.class );
		RESISTS.add( ScrollOfPsionicBlast.class );

		RESISTS.add( WandOfBlastWave.class );
		RESISTS.add( WandOfDisintegration.class );
		RESISTS.add( WandOfFireblast.class );
		RESISTS.add( WandOfFrost.class );
		RESISTS.add( WandOfLightning.class );
		RESISTS.add( WandOfLivingEarth.class );
		RESISTS.add( WandOfMagicMissile.class );
		RESISTS.add( WandOfPrismaticLight.class );
		RESISTS.add( WandOfTransfusion.class );
		RESISTS.add( WandOfWarding.Ward.class );

		RESISTS.add( WarpBeacon.class );

		RESISTS.add( DM100.LightningBolt.class );
		RESISTS.add( Shaman.EarthenBolt.class );
		RESISTS.add( Warlock.DarkBolt.class );
		RESISTS.add( Eye.DeathGaze.class );
		RESISTS.add( YogFist.BrightFist.LightBeam.class );
		RESISTS.add( YogFist.DarkFist.DarkBolt.class );
	}
	
	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		//no proc effect, see:
		// Hero.damage
		// GhostHero.damage
		// Shadowclone.damage
		// ArmoredStatue.damage
		// PrismaticImage.damage
		return damage;
	}
	
	public static int drRoll( Char ch, int level ){
		return Dungeon.NormalIntRange(
				Math.round(level * RingOfArcana.enchantPowerMultiplier(ch)),
				Math.round((3 + (level*1.5f)) * RingOfArcana.enchantPowerMultiplier(ch)));
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return TEAL;
	}

}