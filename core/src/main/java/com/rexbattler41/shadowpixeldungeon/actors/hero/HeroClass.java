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

package com.rexbattler41.shadowpixeldungeon.actors.hero;

import com.rexbattler41.shadowpixeldungeon.*;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.Ratmogrify;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.huntress.SpectralBlades;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.mage.WildMagic;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.ratking.LegacyWrath;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.voidwalker.AnimalsTrans;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.voidwalker.EarthQuake;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.voidwalker.StrikingDash;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.warrior.HeroicLeap;
import com.rexbattler41.shadowpixeldungeon.actors.hero.abilities.warrior.Shockwave;
import com.rexbattler41.shadowpixeldungeon.items.BrokenSeal;
import com.rexbattler41.shadowpixeldungeon.items.Item;
import com.rexbattler41.shadowpixeldungeon.items.KingsCrown;
import com.rexbattler41.shadowpixeldungeon.items.PsycheChest;
import com.rexbattler41.shadowpixeldungeon.items.Waterskin;
import com.rexbattler41.shadowpixeldungeon.items.armor.ClothArmor;
import com.rexbattler41.shadowpixeldungeon.items.artifacts.CloakOfShadows;
import com.rexbattler41.shadowpixeldungeon.items.bags.MagicalHolster;
import com.rexbattler41.shadowpixeldungeon.items.bags.PotionBandolier;
import com.rexbattler41.shadowpixeldungeon.items.bags.ScrollHolder;
import com.rexbattler41.shadowpixeldungeon.items.bags.VelvetPouch;
import com.rexbattler41.shadowpixeldungeon.items.food.Food;
import com.rexbattler41.shadowpixeldungeon.items.potions.PotionOfHealing;
import com.rexbattler41.shadowpixeldungeon.items.potions.PotionOfInvisibility;
import com.rexbattler41.shadowpixeldungeon.items.potions.PotionOfLevitation;
import com.rexbattler41.shadowpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.rexbattler41.shadowpixeldungeon.items.potions.PotionOfMindVision;
import com.rexbattler41.shadowpixeldungeon.items.scrolls.*;
import com.rexbattler41.shadowpixeldungeon.items.wands.WandOfMagicMissile;
import com.rexbattler41.shadowpixeldungeon.items.weapon.SpiritBow;
import com.rexbattler41.shadowpixeldungeon.items.weapon.missiles.LeftClaw;
import com.rexbattler41.shadowpixeldungeon.items.weapon.melee.Dagger;
import com.rexbattler41.shadowpixeldungeon.items.weapon.melee.Gloves;
import com.rexbattler41.shadowpixeldungeon.items.weapon.melee.MagesStaff;
import com.rexbattler41.shadowpixeldungeon.items.weapon.melee.RightClaw;
import com.rexbattler41.shadowpixeldungeon.items.weapon.melee.WornShortsword;
import com.rexbattler41.shadowpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.rexbattler41.shadowpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN ),
	VOIDWALKER( HeroSubClass.SPEEDSTER, HeroSubClass.CLAWFUSER),
	RAT_KING(HeroSubClass.KING);

	private HeroSubClass[] subClasses;

	HeroClass( HeroSubClass...subClasses ) {
		this.subClasses = subClasses;
	}

	public void initHero( Hero hero ) {

		hero.heroClass = this;
		Talent.initClassTalents(hero);

		Item i = new ClothArmor().identify();
		if (!Challenges.isItemBlocked(i)) hero.belongings.armor = (ClothArmor)i;

		i = new Food();
		if (!Challenges.isItemBlocked(i)) i.collect();

		new VelvetPouch().collect();
		Dungeon.LimitedDrops.VELVET_POUCH.drop();

		Waterskin waterskin = new Waterskin();
		waterskin.collect();

		new ScrollOfIdentify().identify();

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;

			case VOIDWALKER:
				initVoidwalker( hero );
				break;

			case RAT_KING:
				initRK(hero);
				break;
		}

		for (int s = 0; s < QuickSlot.SIZE; s++){
			if (Dungeon.quickslot.getItem(s) == null){
				Dungeon.quickslot.setSlot(s, waterskin);
				break;
			}
		}

		new PsycheChest().collect();
		hero.grinding = true;
	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
			case RAT_KING:
				return Badges.Badge.MASTERY_RAT_KING;
			case VOIDWALKER:
				return Badges.Badge.MASTERY_VOIDWALKER;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		(hero.belongings.weapon = new WornShortsword()).identify();
		ThrowingStone stones = new ThrowingStone();
		stones.quantity(3).collect();
		Dungeon.quickslot.setSlot(0, stones);

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		new PotionOfHealing().identify();
		new ScrollOfRage().identify();
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;

		staff = new MagesStaff(new WandOfMagicMissile());

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
		new PotionOfLiquidFlame().identify();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollOfMagicMapping().identify();
		new PotionOfInvisibility().identify();
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Gloves()).identify();
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(0, bow);

		new PotionOfMindVision().identify();
		new ScrollOfLullaby().identify();
	}

	private static void initVoidwalker( Hero hero ) {

		RightClaw right;

		right = new RightClaw();
		(hero.belongings.weapon = right).identify();
		LeftClaw claw = new LeftClaw();
		claw.identify().collect();

		Dungeon.quickslot.setSlot(0, claw);

		new ScrollOfTeleportation().identify();
		new PotionOfLevitation().identify();
	}

	private static void initRK( Hero hero ) {
		MagesStaff staff;

		staff = new MagesStaff(new WandOfMagicMissile());

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(1, bow);

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		Dungeon.quickslot.setSlot(2, cloak);

		new ScrollHolder().collect();
		Dungeon.LimitedDrops.SCROLL_HOLDER.drop();
		new PotionBandolier().collect();
		Dungeon.LimitedDrops.POTION_BANDOLIER.drop();
		new MagicalHolster().collect();
		Dungeon.LimitedDrops.MAGICAL_HOLSTER.drop();

		new PotionOfHealing().identify();
		new ScrollOfRage().identify();
	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
	}

	public String shortDesc(){
		return Messages.get(HeroClass.class, name()+"_desc_short");
	}

	public String perks_desc(){
		return Messages.get(HeroClass.class, name()+"_perk_desc");
	}

	public HeroSubClass[] subClasses() {
		return subClasses;
	}

	public ArmorAbility[] armorAbilities(){
		switch (this) {
			case WARRIOR: default:
				return new ArmorAbility[]{new HeroicLeap(), new Shockwave(), new Endure()};
			case MAGE:
				return new ArmorAbility[]{new ElementalBlast(), new WildMagic(), new WarpBeacon()};
			case ROGUE:
				return new ArmorAbility[]{new SmokeBomb(), new DeathMark(), new ShadowClone()};
			case HUNTRESS:
				return new ArmorAbility[]{new SpectralBlades(), new NaturesPower(), new SpiritHawk()};
			case VOIDWALKER:
				return new ArmorAbility[]{new AnimalsTrans(), new StrikingDash(), new EarthQuake()};
			case RAT_KING:
				return new ArmorAbility[]{new Ratmogrify(), new LegacyWrath()};
		}
	}

	public String spritesheet() {
		switch (this) {
			case WARRIOR: default:
				return Assets.Sprites.WARRIOR;
			case MAGE:
				return Assets.Sprites.MAGE;
			case ROGUE:
				return Assets.Sprites.ROGUE;
			case HUNTRESS:
				return Assets.Sprites.HUNTRESS;
			case VOIDWALKER:
				return Assets.Sprites.VOIDWALKER;
			case RAT_KING:
				return Assets.Sprites.RAT_KING_HERO;
		}
	}

	public String splashArt(){
		switch (this) {
			case WARRIOR: default:
				return Assets.Splashes.WARRIOR;
			case MAGE:
				return Assets.Splashes.MAGE;
			case ROGUE:
				return Assets.Splashes.ROGUE;
			case HUNTRESS:
				return Assets.Splashes.HUNTRESS;
			case VOIDWALKER:
				return Assets.Splashes.HUNTRESS;
				//This is just temporary, since I don't have a splash art yet. I am not a good pixel artist either
			case RAT_KING:
				return Assets.Splashes.RATKING;
		}
	}

	public boolean isUnlocked(){
        return true;
	}
	
	public String unlockMsg() {
		return shortDesc() + "\n\n" + Messages.get(HeroClass.class, name()+"_unlock");
	}

}