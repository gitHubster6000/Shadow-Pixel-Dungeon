/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.shatteredpixel.rexbattler41.ui.changelist;

import com.shatteredpixel.rexbattler41.Assets;
import com.shatteredpixel.rexbattler41.actors.hero.HeroClass;
import com.shatteredpixel.rexbattler41.items.BlackPsycheChest;
import com.shatteredpixel.rexbattler41.items.ExpGenerator;
import com.shatteredpixel.rexbattler41.items.TicketToArena;
import com.shatteredpixel.rexbattler41.items.bags.VelvetPouch;
import com.shatteredpixel.rexbattler41.items.fishingrods.BasicFishingRod;
import com.shatteredpixel.rexbattler41.items.food.Cheese;
import com.shatteredpixel.rexbattler41.items.potions.PotionOfExperience;
import com.shatteredpixel.rexbattler41.items.potions.elixirs.ElixirOfMight;
import com.shatteredpixel.rexbattler41.items.potions.exotic.PotionOfMastery;
import com.shatteredpixel.rexbattler41.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.rexbattler41.items.scrolls.exotic.ScrollOfSirensSong;
import com.shatteredpixel.rexbattler41.items.spells.Vampirism;
import com.shatteredpixel.rexbattler41.items.treasurebags.GambleBag;
import com.shatteredpixel.rexbattler41.items.wands.WandOfEarthblast;
import com.shatteredpixel.rexbattler41.items.wands.WandOfMagicMissile;
import com.shatteredpixel.rexbattler41.items.weapon.melee.Greataxe;
import com.shatteredpixel.rexbattler41.items.weapon.melee.Whip;
import com.shatteredpixel.rexbattler41.messages.Messages;
import com.shatteredpixel.rexbattler41.scenes.ChangesScene;
import com.shatteredpixel.rexbattler41.sprites.HeroSprite;
import com.shatteredpixel.rexbattler41.sprites.ItemSprite;
import com.shatteredpixel.rexbattler41.sprites.ItemSpriteSheet;
import com.shatteredpixel.rexbattler41.ui.Icons;
import com.shatteredpixel.rexbattler41.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class ExpPDChanges {

	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
        ChangeInfo changes = new ChangeInfo("ExpPD-2.11.4", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released February 02th, 2023\n" +
                        "_-_ 112 days after Experienced Pixel Dungeon 2.11.3\n" +
                        "\n" +
                        "This is first release in Google Play!"));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other Changes",
                "_-_ Fixed badges for Wand of Avalanche quest.\n" +
                        "_-_ Fixed rare crash when Elixir of Luck attempts to replace melee weapon in a shop.\n" +
                        "_-_ Fixed the rare crash with traps and Integer unboxing.\n" +
                        "_-_ Fixed Scrolls of Determination and Midas disappearing on reload.\n" +
                        "_-_ Added more actions into subclass action indicator recycling.\n" +
                        "_-_ Reworked Iron Will into Iron Reflection: now damages enemies with shockwaves when player is hurt.\n" +
                        "_-_ Determination Shield buff no longer gets used when player's HP is at full.\n" +
                        "_-_ Bigger Bags perk is now named in more clear way.\n" +
                        "_-_ Scroll of Dread is back and has fixed icon."));

        changes = new ChangeInfo("ExpPD-2.11.3", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released October 14th, 2022\n" +
                        "_-_ 29 days after Experienced Pixel Dungeon 2.11.2\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Shattered ports",
                    "Ported changes from Shattered 1.4.0.\n\n" +
                            "_-_ This includes Arcana, new landscape hero select, sandals and berserker rework."));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other Changes",
                "_-_ Level sizes are more varied.\n" +
                        "_-_ Significantly buffed scaling of most rings.\n" +
                        "_-_ Increased the base duration for fishing from 0-20 to 4-12.\n" +
                        "_-_ Arena mobs spawn 33% faster."));

        changes = new ChangeInfo("ExpPD-2.11.2", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released September 15th, 2022\n" +
                        "_-_ 3 days after Experienced Pixel Dungeon 2.11.1\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(Icons.get(Icons.CHALLENGE_ON), "Other Changes",
                "Tweaked some challenges:" +
                        "_-_ Food is only 10% as effective instead of 33% (On Diet)\n" +
                        "_-_ Mobs do not sleep and will not fight for you (Swarm Intelligence)\n" +
                        "_-_ SoUs no longer drop and farming them takes more exp (Forbidden Runes)"));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other Changes",
                "_-_ Fixed certain items causing stack overflow crash.\n" +
                        "_-_ Fishing rods get upgrades regardless of their position in blacksmith's window.\n" +
                        "_-_ Nerfed item drop perks for Rogue and Huntress.\n" +
                        "_-_ Fixed some of new scoring bugs."));

        changes = new ChangeInfo("ExpPD-2.11.1", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released September 12th, 2022\n" +
                        "_-_ 30 days after Experienced Pixel Dungeon 2.11\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other Changes",
            "_-_ Fixed Black Mimic breaking itself on death.\n" +
                    "_-_ Fixed perks blocking the game if you get beyond level 166.\n" +
                    "_-_ Fixed dirk having ShPD values for its ability.\n" +
                    "_-_ Fixed preparation triggering from the cloak.\n" +
                    "_-_ Fixed collect-related code for items, which fixes phantom gold and other issues.\n" +
                    "_-_ Fixed pylon on Black Mimic level not outputting their gases.\n" +
                    "_-_ Fixed scroll of upgrade causing freezes at high levels.\n" +
                    "_-_ Fixed scroll of upgrade being pseudo-identified.\n" +
                    "_-_ Fixed Desktop interface not fully displaying the inventory.\n" +
                    "_-_ Fixed Rat King having 2 velvet pouches.\n" +
                    "_-_ Fixed experience and overload potions not working.\n" +
                    "_-_ Fixed Bounty Hunter perk not working.\n" +
                    "_-_ Fixed toolkit not being able to warm up on +11 and more.\n" +
                    "_-_ Fixed being able to actually descend into arena from Black Mimic level.\n" +
                    "_-_ Fixed avatar-related crash.\n" +
                    "_-_ Fixed amount of traps in cycles being too high sometimes.\n" +
                    "_-_ Greataxe can be thrown from quickslot and no longer hurts allies.\n" +
                    "_-_ Nerfed Whip's minimal damage."));

        changes = new ChangeInfo("ExpPD-2.11", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                    "_-_ Released August 11th, 2022\n" +
                            "_-_ 247 days after Experienced Pixel Dungeon 2.10.2\n" +
                            "_-_ 289 days after Experienced Pixel Dungeon 2.10\n" +
                            "\n" +
                            "Welcome to 2nd anniversary of ExpPD! This release implements many changes from Shattered done over this time period,"+
                            "bringing engine's version to 1.3.2. Enjoy all new features while making builds with new item reworks and class perks!"));
            changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Shattered ports",
                    "Basically everything between 0.9.2 and 1.3.2.\n\n"+
                            "_-_ Armor abilities.\n" +
                            "_-_ Gladiator and Freerunner reworks.\n" +
                            "_-_ Alchemy rework.\n" +
                            "_-_ New music.\n" +
                            "_-_ New desktop UI.\n" +
                            "_-_ Liquid Metal and Arcane Resin.\n" +
                            "_-_ Hostile Champions and Badder Bosses challenges.\n" +
                            "_-_ Ankh overhaul.\n" +
                            "_-_ Exotic items reworks.\n" +
                            "_-_ New special rooms.\n" +
                            "_-_ Armband rework.\n" +
                            "_-_ Dreamfoil rework.\n" +
                            "_-_ Ascension challenge."
            ));
            changes.addButton( new ChangeButton(new Image(Assets.Interfaces.BUFFS_LARGE, 80, 32, 16, 16), "Perks",
                    "_-_ Reworked how frequently perks are gained to make them more accessible.\n" +
                            "_-_ Nerfed Iron Will to make you take less damage.\n" +
                            "_-_ Added Steel Will perk." ));

            changes.addButton( new ChangeButton(new ItemSprite(new Whip()), "Weapon changes",
                    "_-_ Overhauled Whip, Round Shield, Stone Gauntlet and Greatshield.\n" +
                            "_-_ Buffed Warhammer.\n" +
                            "_-_ Nerfed Sai.\n" +
                            "_-_ Nerfed all missile weapons, including Spirit Bow."));
            changes.addButton( new ChangeButton(HeroSprite.avatar(HeroClass.WARRIOR, 6), "Class tweaks",
                    "_-_ To make classes more appealing to play compared to Rat King, each one has many new perks to help them progress in dungeon.\n" +
                            "_-_ This also includes subclasses."));
            changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other Changes",
                    "_-_ Fixed Ring of Elements's misleading description.\n" +
                            "_-_ Fixed treasure bags dropping higher tier weapons.\n" +
                            "_-_ Treasure bags no longer require turns to pick each item.\n" +
                            "_-_ Changed the coloring of buttons.\n" +
                            "_-_ Potion of Mastery buffs weapons and armor.\n" +
                            "_-_ Guide Entries now have a scroll bar.\n" +
                            "_-_ Changed score calculation and badges to fit Experienced's progression.\n" +
                            "_-_ You cannot fall into chasms on Black Mimic stage and arena.\n" +
                            "_-_ Added crash handling mechanism.\n" +
                            "_-_ Fixed crash for warhammer's attack."));



            changes = new ChangeInfo("ExpPD-2.10.2", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released December 8th, 2021\n" +
                        "_-_ 41 day after Experienced Pixel Dungeon 2.10.1\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Changes",
                "_-_ Fixed softlocks from chalice by overhauling regeneration.\n\n" +
                        "_-_ Rarity of items inside of categories is longer affected luck (should remove prevalence of 2 types of wands or rings).\n\n" +
                        "_-_ Fixed rings giving much less benefits than shown in their description.\n\n" +
                        "_-_ Fixed RK's mirror images.\n\n" +
                        "_-_ Added an icon for older Android versions."));
        changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Shattered ports",
                "_-_ New level gen algorithm.\n" +
                        "_-_ Added HP number to health bar.\n" +
                        "_-_ Quick-use UI.\n" +
                        "_-_ Items with cone AoEs can trickshot.\n" +
                        "_-_ Web can burn and wands can shoot through web.\n" +
                        "_-_ Overhauled On Diet, Faith is my Armor and Pharmo."
        ));

        changes = new ChangeInfo("ExpPD-2.10.1", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released October 28th, 2021\n" +
                        "_-_ 1 day after Experienced Pixel Dungeon 2.10\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Changes",
                "_-_ Fixed the crash for rat king beating Tengu."));
        changes = new ChangeInfo("ExpPD-2.10", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released October 27th, 2021\n" +
                        "_-_ 53 days after Experienced Pixel Dungeon 2.9.4\n" +
                        "_-_ 361 day after Experienced Pixel Dungeon 2.9\n\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(HeroSprite.avatar(HeroClass.RAT_KING, 6), "New class!",
                "His Ratiness joins the battle for greed and loot, with his signature ability being everything he wants to be and faster leveling from RKD!"));
        changes.addButton( new ChangeButton(new Image(Assets.Interfaces.BUFFS_LARGE, 80, 32, 16, 16), "Perks",
                "Each 10th level you will get a random neat bonus, called Perk. Right now there is 12 of them." ));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Changes",
                "_-_ Fixed certain issues with infinity turns.\n\n" +
                        "_-_ Levels are bigger at higher cycles.\n\n" +
                        "_-_ Artifacts are no longer enforced to be unique.\n\n" +
                        "_-_ Massively buffed most of melee weapons.\n\n" +
                        "_-_ Greataxe uses one turn regardless of targets.\n\n" +
                        "_-_ Arena mobs give 50% XP instead of 0.\n\n" +
                        "_-_ Fishing no longer uses luck and no longer exclusively uses purple tier at higher levels."));

            changes = new ChangeInfo("ExpPD-2.9.4", true, "");
            changes.hardlight(Window.TITLE_COLOR);
            changeInfos.add(changes);
            changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                    "_-_ Released September 4th, 2021\n" +
                            "_-_ 17 days after Experienced Pixel Dungeon 2.9.3\n" +
                            "\n" +
                            "Dev commentary will be added here in the future."));
            changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Changes",
                    "_-_ Fixed certain issues with arena ticket.\n\n" +
                            "_-_ Bbat upgrades when being dead.\n\n" +
                            "_-_ Overpopulation spell can scale much further.\n\n" +
                            "_-_ Wands scale their damage with hero's level.\n\n" +
                            "_-_ Added cycle indicator."));

        changes = new ChangeInfo("ExpPD-2.9.3", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 18th, 2021\n" +
                        "_-_ 223 days after Experienced Pixel Dungeon 2.9.2\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Changes",
                "_-_ Fixed bugs with Scroll of Transmutation.\n\n" +
                        "_-_ Fixed bug with Preparation bugging out bosses.\n\n" +
                        "_-_ Fixed certain missiles having lower damage than intended.\n\n" +
                        "_-_ Fixed Scroll of Upgrade showing the upgrade option even if you didn't identify it.\n\n" +
                        "_-_ Fixed Scroll of Affection making bosses allied.\n\n" +
                        "_-_ Fixed Wealth effect triggering on opening containers.\n\n" +
                        "_-_ Hugely buffed Black Mimic's stats, fixed his clones counting as boss defeat.\n\n" +
                        "_-_ Scroll of Determination lasts two times longer.\n\n" +
                        "_-_ Fixed certain artifacts not recharging at all on high upgrade levels.\n\n" +
                        "_-_ Significantly buffed fishing: you need to wait less and rare drops are more frequent.\n\n" +
                        "_-_ Alchemy pots give more energy on higher dungeon cycles."));

            changes = new ChangeInfo("ExpPD-2.9.2", true, "");
            changes.hardlight(Window.TITLE_COLOR);
            changeInfos.add(changes);
            changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                    "_-_ Released January 7th, 2021\n" +
                            "_-_ 4 days after Experienced Pixel Dungeon 2.9.1\n" +
                            "\n" +
                            "Dev commentary will be added here in the future."));
            changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Changes",
                    "_-_ Fixed bug with Ring of Tenacity.\n\n" +
                            "_-_ Fixed bug with Mace not shooting.\n\n" +
                            "_-_ Every throwing weapon has double damage. Even Spirit Bow. Yeth.\n\n" +
                            "_-_ Dirk now accumulates Preparation. No, you can't confess in love to rat and kill yourself with it.\n\n" +
                            "_-_ Leather Gloves now temporarily get more power, if you deal 0 damage."));

            changes = new ChangeInfo("ExpPD-2.9.1", true, "");
            changes.hardlight(Window.TITLE_COLOR);
            changeInfos.add(changes);
            changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                    "_-_ Released January 3th, 2021\n" +
                            "_-_ 64 days after Experienced Pixel Dungeon 2.9\n" +
                            "\n" +
                            "Dev commentary will be added here in the future."));
            changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other",
                    "_-_ Fixed bug with shopkeeper not giving key.\n\n" +
                            "_-_ Clayball is sold in each store after key of truth.\n\n" +
                            "_-_ Grinding is now enabled by default."));

            changes = new ChangeInfo("ExpPD-2.9", true, "");
            changes.hardlight(Window.TITLE_COLOR);
            changeInfos.add(changes);
            changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                    "_-_ Released October 31th, 2020\n" +
                            "_-_ 47 days after Experienced Pixel Dungeon 2.8.1\n" +
                            "\n" +
                            "Dev commentary will be added here in the future."));

            changes.addButton( new ChangeButton(new ItemSprite(new WandOfEarthblast()), "Brand new wand!",
                    "Added the legendary overpowered Wand of Avalanche. To find it, you need to complete big backtracking quest with 6 parts!\n\n" +
                            "To begin, get cheese from Rat King."));

            changes.addButton( new ChangeButton(new ItemSprite(new Cheese()), "WoA quest content",
                    "Added 6 items, used in Wand of Avalanche quest, 5 of which are usable as equipment or consumable."));

            changes.addButton( new ChangeButton(new ItemSprite(new ElixirOfMight()), "Luck mechanic",
                    "Added incrementable luck. You can drink elixirs of luck to improve your chances to attack and dodge, item rarity, damage rolls and many other things.\n\n" +
                            "_-_ Elixir of Luck can replace any random item with 0.5% chance or be brewed from Strength potion."));

            changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other",
                    "_-_ Buffed Grim's damage and Greatsword's knights HP.\n\n" +
                            "_-_ Added little animation quirk with fast weapons."));

            changes.addButton( new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                    "Fixed:\n" +
                            "_-_ Chalice of Blood being broken beyond +30.\n"+
                                "_-_ Potential transmutation crashes."));

	        changes = new ChangeInfo("ExpPD-2.8.1", true, "");
            changes.hardlight(Window.TITLE_COLOR);
            changeInfos.add(changes);
            changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                    "_-_ Released September 13th, 2020\n" +
                            "_-_ 16 days after Experienced Pixel Dungeon 2.8\n" +
                            "\n" +
                            "Dev commentary will be added here in the future."));
            changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other",
                    "_-_ Fixed tenacity and might.\n\n" +
                            "_-_ Fixed beacons of returning breaking time and space.\n\n" +
                            "_-_ Fixed bags on arena and arena's ticket price."));

        changes = new ChangeInfo("ExpPD-2.8", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 28th, 2020\n" +
                        "_-_ 12 days after Experienced Pixel Dungeon 2.7.1\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(new ItemSprite(new GambleBag()), "Treasure Bags",
                "Fixed the crash, when item from backpack is null."));
        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_RUBY, null), "Rings",
                "Refactored multiplier code to match vanilla's behaviour."));
        changes.addButton( new ChangeButton(new ItemSprite(new Greataxe()), "Greataxe",
                "Greataxe now properly hits several enemies."));
        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.MACE, null), "Overhauled Weapons",
                "_-_ Mace shoots laser beams on attacking.\n\n" +
                        "_-_ Warhammer is very powerful, but very slow."));
        changes.addButton( new ChangeButton(new ItemSprite(new TicketToArena()), "Arena",
                "Replaced passive exp generation with arena.\n\n" +
                        "_-_You can buy one-time tickets in any shops.\n\n" +
                        "_-_ You can meet a wide variety of mobs, which have no exp, but drop gold and valueable items.\n\n" +
                        "_-_ There is shop with bags to buy.\n\n\n" +
                        "Experience Gens in inventory will be replaced by tickets, generators in world will be replaced by sheeps."));

        changes = new ChangeInfo("ExpPD-2.7.1", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 16th, 2020\n" +
                        "_-_ 3 hours after Experienced Pixel Dungeon 2.7\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other",
                "_-_ Fixed rings having effects when they shouldn't.\n\n" +
                        "_-_ Fixed power plants being not functional on +0.\n\n" +
                        "_-_ Fixed new bags having incorrect icons."));

        changes = new ChangeInfo("ExpPD-2.7", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 16th, 2020\n" +
                        "_-_ 0 days after Experienced Pixel Dungeon 2.6\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(new ItemSprite(new GambleBag()), "Treasure Bags",
                "_-_ Boss treasure bags contain more money.\n\n" +
                        "_-_ Lucky bag contains more items and have reduced price.\n\n" +
                        "_-_ Alchemy bag have more items and increased price.\n\n" +
                        "_-_ Added big lucky bag and elite bag."));
        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_RUBY, null), "Rings",
                "Ring's scaling is reworked to be linear."));
        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.EXOTIC_ODAL, null), "Scrolls",
                "_-_ Scroll of Confusion is now _Scroll of Determination_ that applies life-stealing shield to user.\n\n" +
                        "_-_ Scroll of Polymorph is now _Scroll of Midas_, that turns every visible enemy into money."));
        changes.addButton( new ChangeButton(new ItemSprite(new Vampirism()), "Spells",
                "Overpopulation and Flame Empower are consumed on using."));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other",
                "_-_ Resetting floor resets RNG.\n\n" +
                        "_-_ Power Plants are getting upgraded instead of being stacked.\n\n" +
                        "_-_ Tridents now drop some gold on damaging enemies."));

         changes = new ChangeInfo("ExpPD-2.6", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 16th, 2020\n" +
                        "_-_ 3 days after Experienced Pixel Dungeon 2.5.1\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(new ItemSprite(new GambleBag()), "Treasure Bags",
                "Added treasure bags.\n\n" +
                        "_-_ Once boss is defeated once, on next fights it will drop treasure bag with unique drops, loads of money and unique items for dungeon cycles.\n\n" +
                        "_-_ You can find Gamble Bag in shops and you can catch alchemy items bag with fishing rod."));
        changes.addButton( new ChangeButton(new ItemSprite(new Vampirism()), "Spells",
                "Added three new spells: Vampirism, Overpopulation and Fire Empower. More info in in-game guide."));
        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_RUBY, null), "Rings",
                "Ring's scaling is extremely nerfed again to somewhat negate their power creep. (why) (I hate furor)"));
        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.BASIC_HOOK, null), "Fishing",
                "Reduced upgrades on fished items."));

        changes = new ChangeInfo("ExpPD-2.5.1", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 13th, 2020\n" +
                        "_-_ 0 days after Experienced Pixel Dungeon 2.5\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other",
                "_-_ Increased amount of possible Marked debuffs.\n\n" +
                        "_-_ Fixed crash when power plants are attacked by magic.\n\n" +
                        "_-_ Power Plants require to be accessible by mobs to work."));

        changes = new ChangeInfo("ExpPD-2.5", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 13th, 2020\n" +
                        "_-_ 1 days after Experienced Pixel Dungeon 2.4.1\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));


        changes.addButton( new ChangeButton(new Image(Assets.Sprites.BBAT, 0, 0, 15, 15), "Bbat",
                "_-_ Fixed crash on bbat's death." ));

        changes.addButton( new ChangeButton(new Image(Assets.Sprites.ROGUE, 0, 90, 12, 15), "Hunter subclass",
                "Replaced Assassin with Hunter.\n\n" +
                        "_-_ Instead of waiting in shadows, Hunter need to aggro bbat into enemies to mark them.\n\n" +
                        "_-_ Marking enemies increases bbat's damage to them and allows Hunter to teleport and inflict debuffs." ));

        changes.addButton( new ChangeButton(new ItemSprite(new ExpGenerator()), "EXP Generator",
                "_-_ Reverted satiety and sleep consumption, but you need to defend power plants from mobs.\n\n" +
                        "_-_ If monster manages to kill plant, monster will gain a huge amount of armor and attack speed.\n\n" +
                        "_-_ Power Plants attract mobs in 32x32 sphere."));

        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other",
                "_-_ Adjusted amount of Yog's lasers immensely.\n\n" +
                        "_-_ Significantly increased inventory size and removed hidden slots.\n\n" +
                        "_-_ Added cycle 4 Yog laser damage."));



        changes = new ChangeInfo("ExpPD-2.4.1", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 12th, 2020\n" +
                        "_-_ 1 days after Experienced Pixel Dungeon 2.4\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));

        changes.addButton( new ChangeButton(new Image(Assets.Sprites.BBAT, 0, 0, 15, 15), "Bbat",
                "Added bbat minion to rogue to decrease early-game difficulty." ));

        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other",
                "_-_ Greatsword no longer spawns knights, when owned by statue.\n\n" +
                        "_-_ Kunai have text for buff and actually works.\n\n" +
                        "_-_ Armored Statue doesn't crash with tiered armor.\n\n" +
                        "_-_ Guardian Trap no longer depends on escalating depth.\n\n" +
                        "_-_ Cycles no longer preserve between runs (will not affect runs before this update).\n\n" +
                        "_-_ Hunger can damage you on locked floor when drained by power plants.\n\n" +
                        "_-_ Honeypot cannot be obtained from fishing."));

        changes = new ChangeInfo("ExpPD-2.4", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 11th, 2020\n" +
                        "_-_ 1 days after Experienced Pixel Dungeon 2.3\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));

        changes.addButton( new ChangeButton(new ItemSprite(new ExpGenerator()), "EXP Generator",
                "Fixed interruptions when unneeded."));

        changes.addButton( new ChangeButton(new ItemSprite(new VelvetPouch()), "Inventory",
                "Adjusted inventory size."));

        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.GREATSWORD, null), "Overhauled Weapons",
                "Added new abilities for some underused weapons and items. For more details, visit In-Game Guide."));

        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.SWORD, new ItemSprite.Glowing( 0x000000 )), "Grim",
                "Significantly nerfed Grim: now it does additional damage instead of instakill."));

        changes.addButton( new ChangeButton(new ItemSprite(new BlackPsycheChest()), "Dungeon Cycles",
                "You can enter fourth dungeon cycle."));

        changes.addButton( new ChangeButton(new ItemSprite(new ScrollOfSirensSong()), "Scroll of Affection",
                "Scroll of Affection turns enemies into allies instead of charming."));

        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other",
                "_-_ Corruption is affected by Bless buff.\n\n" +
                        "_-_ Shield weapons now grow with dungeon's cycles.\n\n" +
                        "_-_ Significantly reduced amount of particles for some wand, to reduce lag.\n\n" +
                        "_-_ Added new fishing rod for new dungeon cycle.\n\n" +
                        "_-_ Various small adjustments to UI.\n\n" +
                        "_-_ Fishing is fully fixed, for real."));

        changes = new ChangeInfo("ExpPD-2.3", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 10th, 2020\n" +
                        "_-_ 4 days after Experienced Pixel Dungeon 2.2\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton(Icons.get(Icons.NEWS), "Guide",
                "Added in-game guide, which you can access in title menu."));
        changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Shattered 0.8.2 changes",
                "_-_ New title screen.\n" +
                        "_-_ New settings window.\n" +
                        "_-_ 3rd accessory (misc) slot.\n" +
                        "_-_ Questgivers are added to journal, when hero firstly sees them.\n" +
                        "_-_ Disintegration's range benefits from magic charge.\n" +
                        "_-_ Artifacts benefit from Ring of Energy.\n" +
                        "_-_ Knockback effects closing door only on melee attacks.\n\n" +
                        "Fixes:\n" +
                        "_-_ Tengu's freezing the game when jumping.\n" +
                        "_-_ Boss drop enemies softlocking the game on death.\n"+
                        "_-_ Bees refusing to retarget when target is invulnerable\n" +
                        "_-_ Potion splashes cleaning fire/ooze from enemies.\n" +
                        "_-_ Blast wave attempting to throw enemies that move after being damaged.\n" +
                        "_-_ Corrupting enchant attempting to corrupt dead enemies.\n" +
                        "_-_ Magic missile charge buff not visually applying to the mage's staff.\n" +
                        "_-_ Specific cases where using an item wouldn't cancel the current cell selector.\n" +
                        "_-_ Large enemies rarely appearing in enclosed spaces due to levelgen.\n" +
                        "_-_ Lotus vfx persisting after the sprite is destroyed in rare case.\n"
                        ));

        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_RUBY, null), "Rings",
                "Ring's scaling is extremely nerfed to somewhat negate their power creep."));

        changes.addButton( new ChangeButton(new ItemSprite(new ExpGenerator()), "EXP Generator",
                "Exp generators now drain your satiety and interrupt your sleep when possible."));

        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other",
                "_-_ Number of traps, items and mobs now is much lower on dungeon's cycles.\n\n" +
                        "_-_ Fishing is fully fixed, also drops food."));


        changes = new ChangeInfo("ExpPD-2.2", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 6th, 2020\n" +
                        "_-_ 2 days after Experienced Pixel Dungeon 2.1\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));
        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.SCROLL_TIWAZ, null), new ScrollOfUpgrade().trueName(),
                "The whole stack of SoU can be applied at once with special action."));
        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.EXOTIC_CRIMSON, null), new PotionOfMastery().trueName(),
                "This potion replaces Adrenaline Surge and allows to spawn Rat King on any depth."));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "Other",
                "_-_ Added escalation data for cycle 3.\n" +
                        "_-_ Fixed artifacts not charging at very high levels.\n" +
                        "_-_ Fixed fishing not working entirely.\n" +
                        "_-_ Fixed transmutation crash for high-tier items.\n" +
                        "_-_ Tome of Mastery on rebeating Tengu is named Tome of Remastery.\n" +
                        "_-_ Adjusted inventory space and window.\n" +
                        "_-_ Blacksmith now transfers upgrades to higher tiered items.\n" +
                        "_-_ Power Plants are stackable now.\n" +
                        "_-_ Wand's charges are shown in percentage when in inventory, but exact charges are shown in description\n" +
                        "_-_ Fixed Mage's Staff ignoring his charge bonus."));




        changes = new ChangeInfo("ExpPD-2.1", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 4th, 2020\n" +
                        "_-_ 2 days after Experienced Pixel Dungeon 2.0\n" +
                        "\n" +
                        "Dev commentary will be added here in the future."));

        changes.addButton( new ChangeButton(new Image(Assets.Sprites.KING, 0, 0, 16, 16), "Dwarf King",
                "Fixed rounding error, when Dwarf King didn'tget damaged enough to progress boss fight." ));

        changes.addButton( new ChangeButton(new ItemSprite(new BasicFishingRod()), "Fishing",
                "_-_ Hook will disappear after leaving the depth or field of view properly (the bug was when you can't cast new hook).\n\n" +
                        "_-_ Hook can't be casted into walls.\n\n" +
                        "_-_ Now you can get some equipment (maybe).\n\n" +
                        "_-_ Fishing rods now give experience if enough rare item is caught."));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_HOLDER, null), "Artifacts",
                "Upgrading past +10 should work for more than one upgrade."));

        changes.addButton( new ChangeButton(new ItemSprite(new ExpGenerator()), "EXP Generator",
                "Added power plant for passive experience generation. Can be bought at shops."));

        changes.addButton( new ChangeButton(new Image(Assets.Sprites.TROLL, 0, 0, 13, 16), "Blacksmith",
                "_-_ Removed the limit for reforging.\n\n" +
                        "_-_ (was in previous update but whatever) Blacksmith can transfer upgrades with items of same equipment type.\n\n" +
                        "_-_ Bat quest can't appear on dungeon's cycles." ));

        changes.addButton( new ChangeButton(new ItemSprite(new WandOfMagicMissile()), "Wands",
                "_-_ Removed the limitation for wand charges.\n\n" +
                        "_-_ Mage's Staff gives 33% additional charges instead of just one.\n\n" +
                        "_-_ Magic Charge buff now adds levels to all wands rather than overriding them."));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SWORD, new ItemSprite.Glowing( 0x8844CC )), "Enchanting",
                "_-_ Removed the erasure mechanic, so enchantments stay forever on equipment.\n\n" +
                        "_-_ As result, Magical Infusion is no longer obtainable."));

        changes.addButton( new ChangeButton(new ItemSprite(new VelvetPouch()), "Dungeon's Cycles changes",
                "_-_ Armor kit and Tome of Mastery can be reobtained on dungeon's cycles.\n\n" +
                        "_-_ Armor on dungeon's cycles shouldn't crash the game with wrong tier images.\n\n" +
                        "_-_ Storage bags cannot reappear on dungeon's cycles, their capacity is increased to 40.\n\n" +
                        "_-_ Sad Ghost gives correctly tiered items on dungeon's cycles.\n\n" +
                        "_-_ Darts now respond to dungeon's cycles overall empowerment; Crossbow is now viable weapon for mid-game."));

        changes.addButton( new ChangeButton(Icons.get(Icons.INFO), "Updates",
                "Added GitHub powered updates."));

        changes = new ChangeInfo("ExpPD-2.0", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(Icons.get(Icons.BOBBY_IS_VERY_STRANGE_PERSON_BECAUSE_HE_TRIES_TO_REFERENCE_HIMSELF_IN_NEW_SHATTERED_CREDITS_SCREEN), "Developer Commentary",
                "_-_ Released August 2th, 2020\n" +
                "_-_ 286 days after ExperiencedPixel Dungeon v1.3\n" +
                "_-_ 34 days after ShPD 0.8.1 source release\n" +
                "\n" +
                "Dev commentary will be added here in the future."));

        changes.addButton( new ChangeButton(new Image(Assets.Interfaces.BUFFS_LARGE, 80, 32, 16, 16), "Experience changes",
                "All limits are removed, you can farm EXP forever!" ));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.PSYCHE_CHEST, null), "Grind Switcher",
                "Added new item into starting inventory - Fate Lock. It allows to toggle 'grinding' mode, where you get SoU from experience, and mobs drop random items.\n\n" +
                        "Also it allows to reset the level for new loot with exchange of 50% max HP."));
        changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.POTION_GOLDEN, null), new PotionOfExperience().trueName(),
                "Reworked both regular and exotic versions of Potion of Experience.\n\n" +
                        "_-_ _Potion of Experience_ now provides 2x EXP gain boost and additional item on killing mobs while in grinding mode.\n\n" +
                        "_-_ _Potion of Holy Furor_ is now _Potion of Overload_ that gives effect of spawning new monsters on killing older monsters."));
        changes.addButton( new ChangeButton(new ItemSprite(new BasicFishingRod()), "Fishing",
                "Ring of Wealth have been replaced by fishing mechanic.\n\n" +
                        "_-_ There is totally 4 fishing rods, appearing at different stages of the game.\n\n" +
                        "_-_ To begin fishing, CAST fishing rod to water. Once hook will signal about item, UNCAST the fishing rod to get your loot.\n\n" +
                        "_-_The amount of loot and farming speed are determined by rod's tier and upgrades."));
        changes.addButton( new ChangeButton(new Image(Assets.Sprites.KEEPER, 0, 0, 14, 14), "Shop changes",
                "Added more item categories in the shop." ));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_HOLDER, null), "Artifacts",
                "You can upgrade artifacts with scrolls, once they reached +10, except for Chalice of Blood."));
        changes.addButton( new ChangeButton(new Image(Assets.Sprites.RATKING, 0, 0, 16, 17), "Bartering",
                "Added bartering with Rat King.\n\n" +
                        "_-_ To start, throw something to Rat King's position. He will exchange it for something totally different.\n\n" +
                        "_-_ However, he doesn't give gold and need to rest 1 turn to exchange again." ));
        changes.addButton( new ChangeButton(new ItemSprite(new BlackPsycheChest()), "Dungeon Cycles",
                "Amulet of Yendor is replaced by Black Fate Lock.\n\n" +
                        "_-_ You can choose to reset dungeon to its unexplored state, or go to superboss area.\n\n" +
                        "_-_ When reset, you appear on first floor of dungeon, with more powerful loot and enemies.\n\n" +
                        "_-_All variables are reset on each resetting, so you can get shops, potions of strength and quests again."));
        changes.addButton( new ChangeButton(new Image(Assets.Sprites.MIMIC, 0, 48, 16, 16), "Black Mimic",
                "Black Mimic holds the Amulet and will not give it back without tedious and dangerous fight! Can you beat the ultimate mob, which uses abilities of every Shattered boss?" ));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
                "_-_ Increased hero's backpack size.\n\n" +
                        "_-_ Removed Patreon and languages buttons.\n\n" +
                        "_-_ Removed hero and challenge locks."));
    }

}
