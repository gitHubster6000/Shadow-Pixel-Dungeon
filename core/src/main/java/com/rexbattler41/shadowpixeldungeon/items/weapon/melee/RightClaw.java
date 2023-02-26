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

package com.rexbattler41.shadowpixeldungeon.items.weapon.melee;

import com.rexbattler41.shadowpixeldungeon.Assets;
import com.rexbattler41.shadowpixeldungeon.Badges;
import com.rexbattler41.shadowpixeldungeon.Dungeon;
import com.rexbattler41.shadowpixeldungeon.actors.Char;
import com.rexbattler41.shadowpixeldungeon.actors.buffs.Buff;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Hero;
import com.rexbattler41.shadowpixeldungeon.actors.hero.Talent;
import com.rexbattler41.shadowpixeldungeon.effects.particles.ElmoParticle;
import com.rexbattler41.shadowpixeldungeon.items.ArcaneResin;
import com.rexbattler41.shadowpixeldungeon.items.Item;
import com.rexbattler41.shadowpixeldungeon.items.bags.Bag;
import com.rexbattler41.shadowpixeldungeon.items.bags.MagicalHolster;
import com.rexbattler41.shadowpixeldungeon.items.wands.Wand;
import com.rexbattler41.shadowpixeldungeon.items.wands.WandOfCorrosion;
import com.rexbattler41.shadowpixeldungeon.items.wands.WandOfCorruption;
import com.rexbattler41.shadowpixeldungeon.items.wands.WandOfDisintegration;
import com.rexbattler41.shadowpixeldungeon.items.wands.WandOfLivingEarth;
import com.rexbattler41.shadowpixeldungeon.items.wands.WandOfRegrowth;
import com.rexbattler41.shadowpixeldungeon.items.wands.*;
import com.rexbattler41.shadowpixeldungeon.items.weapon.Weapon;
import com.rexbattler41.shadowpixeldungeon.messages.Messages;
import com.rexbattler41.shadowpixeldungeon.scenes.GameScene;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSprite;
import com.rexbattler41.shadowpixeldungeon.sprites.ItemSpriteSheet;
import com.rexbattler41.shadowpixeldungeon.utils.GLog;
import com.rexbattler41.shadowpixeldungeon.windows.WndBag;
import com.rexbattler41.shadowpixeldungeon.windows.WndOptions;
import com.rexbattler41.shadowpixeldungeon.windows.WndUseItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class RightClaw extends MeleeWeapon{

    private Wand wand;

    public static final String AC_IMBUE = "IMBUE";
    public static final String AC_ZAP	= "ZAP";

    private static final float STAFF_SCALE_FACTOR = 0.75f;

    {
        image = ItemSpriteSheet.CLAW;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.1f;

        internalTier = tier = 1;
        DLY = 0.25f;

        unique = true;
        bones = false;
    }

    public RightClaw() {
        wand = null;
    }

    public int stack = 0;

    @Override
    public int max(int lvl) {
        return  Math.round(3f*(tier+1)) +     //5 base, down from 10
                lvl*Math.round(0.5f*(tier+1));  //+1 per level, down from +2
    }

    public RightClaw(Wand wand){
        this();
        wand.identify();
        wand.cursed = false;
        this.wand = wand;
        updateWand(false);
        wand.curCharges = wand.maxCharges;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions( hero );
        actions.add(AC_IMBUE);
        if (wand!= null && wand.curCharges > 0) {
            actions.add( AC_ZAP );
        }
        return actions;
    }

    @Override
    public void activate( Char ch ) {
        applyWandChargeBuff(ch);
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_IMBUE)) {

            curUser = hero;
            GameScene.selectItem(itemSelector);

        } else if (action.equals(AC_ZAP)){

            if (wand == null) {
                GameScene.show(new WndUseItem(null, this));
                return;
            }

            if (cursed || hasCurseEnchant()) wand.cursed = true;
            else                             wand.cursed = false;
            wand.execute(hero, AC_ZAP);
        }
    }

    @Override
    public int buffedLvl() {
        if (wand != null){
            return Math.max(super.buffedLvl(), wand.buffedLvl());
        } else {
            return super.buffedLvl();
        }
    }

    @Override
    public boolean collect( Bag container ) {
        if (super.collect(container)) {
            if (container.owner != null) {
                applyWandChargeBuff(container.owner);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDetach( ) {
        if (wand != null) wand.stopCharging();
    }

    public Item imbueWand(Wand wand, Char owner){

        int oldStaffcharges = this.wand.curCharges;

        if (owner == Dungeon.hero && Dungeon.hero.hasTalent(Talent.WAND_PRESERVATION)){
            Talent.WandPreservationCounter counter = Buff.affect(Dungeon.hero, Talent.WandPreservationCounter.class);
            if (counter.count() < 5 && Random.Float() < 0.34f + 0.33f*Dungeon.hero.pointsInTalent(Talent.WAND_PRESERVATION)){
                counter.countUp(1);
                this.wand.level(0);
                if (!this.wand.collect()) {
                    Dungeon.level.drop(this.wand, owner.pos);
                }
                GLog.newLine();
                GLog.p(Messages.get(this, "preserved"));
            } else {
                ArcaneResin resin = new ArcaneResin();
                if (!resin.collect()) {
                    Dungeon.level.drop(resin, owner.pos);
                }
                GLog.newLine();
                GLog.p(Messages.get(this, "preserved_resin"));
            }
        }

        this.wand = null;

        wand.resinBonus = 0;
        wand.updateLevel();

        //syncs the level of the two items.
        int targetLevel = Math.max(this.trueLevel(), wand.trueLevel());

        //if the staff's level is being overridden by the wand, preserve 1 upgrade
        if (wand.trueLevel() >= this.trueLevel() && this.trueLevel() > 0) targetLevel++;

        level(targetLevel);
        this.wand = wand;
        updateWand(false);
        wand.curCharges = Math.min(wand.maxCharges, wand.curCharges+oldStaffcharges);
        if (owner != null){
            applyWandChargeBuff(owner);
        } else if (Dungeon.hero.belongings.contains(this)){
            applyWandChargeBuff(Dungeon.hero);
        }

        //This is necessary to reset any particles.
        //FIXME this is gross, should implement a better way to fully reset quickslot visuals
        int slot = Dungeon.quickslot.getSlot(this);
        if (slot != -1){
            Dungeon.quickslot.clearSlot(slot);
            updateQuickslot();
            Dungeon.quickslot.setSlot( slot, this );
            updateQuickslot();
        }

        Badges.validateItemLevelAquired(this);

        return this;
    }

    public void gainCharge(float amt ){gainCharge(amt, false);
    }

    public void gainCharge( float amt, boolean overcharge ){
        if (wand != null){
            wand.gainCharge(amt, overcharge);
        }
    }

    public void applyWandChargeBuff(Char owner){
        if (wand != null){
            wand.charge(owner, STAFF_SCALE_FACTOR);
        }
    }

    public Class<?extends Wand> wandClass(){
        return wand != null ? wand.getClass() : null;
    }

    @Override
    public Item upgrade(boolean enchant) {
        super.upgrade( enchant );

        updateWand(true);

        return this;
    }

    @Override
    public Item degrade() {
        super.degrade();

        updateWand(false);

        return this;
    }

    public void updateWand(boolean levelled){
        if (wand != null) {
            int curCharges = wand.curCharges;
            wand.level(level());
            //gives the wand one additional max charge
            wand.maxCharges = Math.round(wand.maxCharges * 1.33f);
            wand.curCharges = Math.min(curCharges + (levelled ? 1 : 0), wand.maxCharges);
            updateQuickslot();
        }
    }

    @Override
    public String status() {
        if (wand == null) return super.status();
        else return wand.status();
    }

    @Override
    public String info() {
        String info = super.info();

        if (wand != null){
            info += "\n\n" + Messages.get(this, "has_wand", Messages.get(wand, "name"));
            if ((!cursed && !hasCurseEnchant()) || !cursedKnown)    info += " " + wand.statsDesc();
            else                                                    info += " " + Messages.get(this, "cursed_wand");
            
        }

        return info;
    }

    @Override
    public Emitter emitter() {
        if (wand == null) return null;
        Emitter emitter = new Emitter();
        emitter.pos(12.5f, 3);
        emitter.fillTarget = false;
        emitter.pour(RightClawParticleFactory, 0.1f);
        return emitter;
    }

    private static final String WAND = "wand";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(WAND, wand);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        wand = (Wand) bundle.get(WAND);
        if (wand != null) {
            wand.maxCharges = Math.round(wand.maxCharges * 1.33f);
        }
    }

    @Override
    public int value() {
        return 0;
    }

    @Override
    public Weapon enchant(Enchantment ench) {
        if (curseInfusionBonus && (ench == null || !ench.curse())){
            curseInfusionBonus = false;
            updateWand(false);
        }
        return super.enchant(ench);
    }

    private final WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return Messages.get(RightClaw.class, "prompt");
        }

        @Override
        public Class<?extends Bag> preferredBag(){
            return MagicalHolster.class;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return item instanceof Wand;
        }

        @Override
        public void onSelect( final Item item ) {
            if (item != null) {

                if (!item.isIdentified()) {
                    GLog.w(Messages.get(RightClaw.class, "id_first"));
                    return;
                } else if (item.cursed){
                    GLog.w(Messages.get(RightClaw.class, "cursed"));
                    return;
                }

                if (wand == null){
                    applyWand((Wand)item);
                } else {
                    int newLevel;
                    int itemLevel = item.trueLevel();
                    if (itemLevel >= trueLevel()){
                        if (trueLevel() > 0)    newLevel = itemLevel + 1;
                        else                    newLevel = itemLevel;
                    } else {
                        newLevel = trueLevel();
                    }

                    String bodyText = Messages.get(RightClaw.class, "imbue_desc", newLevel);
                    
                    bodyText += "\n\n" + Messages.get(RightClaw.class, "imbue_lost");
                    

                    GameScene.show(
                            new WndOptions(new ItemSprite(item),
                                    Messages.titleCase(item.name()),
                                    bodyText,
                                    Messages.get(RightClaw.class, "yes"),
                                    Messages.get(RightClaw.class, "no")) {
                                @Override
                                protected void onSelect(int index) {
                                    if (index == 0) {
                                        applyWand((Wand)item);
                                    }
                                }
                            }
                    );
                }
            }
        }

        private void applyWand(Wand wand){
            Sample.INSTANCE.play(Assets.Sounds.BURNING);
            curUser.sprite.emitter().burst( ElmoParticle.FACTORY, 12 );
            evoke(curUser);

            Dungeon.quickslot.clearItem(wand);

            wand.detach(curUser.belongings.backpack);

            GLog.p( Messages.get(RightClaw.class, "imbue", wand.name()));
            imbueWand( wand, curUser );

            updateQuickslot();
        }
    };

    private final Emitter.Factory RightClawParticleFactory = new Emitter.Factory() {
        @Override
        //reimplementing this is needed as instance creation of new staff particles must be within this class.
        public void emit( Emitter emitter, int index, float x, float y ) {
            RightClaw.RightClawParticle c = (RightClaw.RightClawParticle)emitter.getFirstAvailable(RightClaw.RightClawParticle.class);
            if (c == null) {
                c = new RightClaw.RightClawParticle();
                emitter.add(c);
            }
            c.reset(x, y);
        }

        @Override
        //some particles need light mode, others don't
        public boolean lightMode() {
            return !((wand instanceof WandOfDisintegration)
                    || (wand instanceof WandOfCorruption)
                    || (wand instanceof WandOfCorrosion)
                    || (wand instanceof WandOfRegrowth)
                    || (wand instanceof WandOfLivingEarth));
        }
    };

    //determines particle effects to use based on wand the staff owns.
    public class RightClawParticle extends PixelParticle {

        private float minSize;
        private float maxSize;
        public float sizeJitter = 0;

        public RightClawParticle(){
            super();
        }

        public void reset( float x, float y ) {
            revive();

            speed.set(0);

            this.x = x;
            this.y = y;

            if (wand != null)
                wand.RightClawFx( this );

        }

        public void setSize( float minSize, float maxSize ){
            this.minSize = minSize;
            this.maxSize = maxSize;
        }

        public void setLifespan( float life ){
            lifespan = left = life;
        }

        public void shuffleXY(float amt){
            x += Random.Float(-amt, amt);
            y += Random.Float(-amt, amt);
        }

        public void radiateXY(float amt){
            float hypot = (float)Math.hypot(speed.x, speed.y);
            this.x += speed.x/hypot*amt;
            this.y += speed.y/hypot*amt;
        }

        @Override
        public void update() {
            super.update();
            size(minSize + (left / lifespan)*(maxSize-minSize) + Random.Float(sizeJitter));
        }
    }

}