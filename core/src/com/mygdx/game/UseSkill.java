package com.mygdx.game;

import com.mygdx.game.battle.BattleAnimator;
import com.mygdx.game.battle.BattleMenu;

/**
 * Class to manage skill usage and the movement required for each type of attack.
 * Can be extended so that each individual skill will perform different actions.
 * This class makes heavy use of the BattleAnimator to perform its actions.
 */
public class UseSkill extends UseAbility {

    BattleAnimator battleAnimator;
    Skill skill;

    /**
     * UseSkill constructor. Immediately begins the process of using the given skill on the target as soon as it is instantiated.
     * @param user The Agent that is using the skill
     * @param target The target Agent
     * @param abilityID The ID of the skill being used
     * @param battleMenu The instance of the battleMenu
     */
    public UseSkill(Agent user, Agent target, int abilityID, BattleMenu battleMenu){
        super(user, target, abilityID, battleMenu);

        InputHandler.disableAllInput();

        battleAnimator = new BattleAnimator();

        skill = Game.skills.getSkill(abilityID);

        battleMenu.showTurnIndicator=false;
        //removed a redundant switch statement that was here

        battleAnimator.moveAgentTo(user, target.getX(), target.getY(), this);
        battleMenu.createInfoBox(user.getName() + " uses " + skill.getName()+" on "+target.getName(),3);



    }

    /**
     * Updates the battleAnimator.
     * @param delta The amount of time passed between frames
     */
    public void update(float delta){
        battleAnimator.update(delta);
    }

    /**
     * Called by the BattleAnimator after it has completed a movement.
     * This function, after the first movement, will cause the intended effect of the skill (e.g. deal damage etc.).
     * @param type An integer corresponding to the type of movement that has completed.
     */
    public void movementDone(int type){
        //Type 0=moved, type 1=returned
        //added melee and ranged attack support here.
        if(type==0) {
            switch (skill.getSkillType()) {
                case MELEE: {
                    Assets.sfx_hitNoise.play(Game.masterVolume);
                    int dmg = user.getStats().getStrength() + user.getCurrentEquipment().getTotalStrengthModifiers() + skill.getBasePower()
                            - target.getStats().getArmourVal();
                    String infoBoxText;
                    if (dmg <= 0) {
                        infoBoxText = (target.getName() + " takes 0 damage.");
                    }
                    else {
                        target.dealDamage(dmg);
                        infoBoxText = (target.getName() + " takes " + dmg + " damage");
                    }
                    if(target.isDead())
                        infoBoxText+=" and is defeated.";
                    battleMenu.createInfoBox( infoBoxText, 3);
                    battleAnimator.returnAgent();//Return agent to start point
                    break;
                }
                case RANGED: {
                    Assets.sfx_hitNoise.play(Game.masterVolume);
                    int dmg = user.getStats().getDexterity() + user.getCurrentEquipment().getTotalDexterityModifiers() + skill.getBasePower()
                            - target.getStats().getArmourVal();
                    String infoBoxText;
                    if (dmg <= 0) {
                        infoBoxText = (target.getName() + " takes 0 damage.");
                    }
                    else {
                        target.dealDamage(dmg);
                        infoBoxText = (target.getName() + " takes " + dmg + " damage");
                    }
                    if(target.isDead())
                        infoBoxText+=" and is defeated.";
                    battleMenu.createInfoBox( infoBoxText, 3);
                    battleAnimator.returnAgent();//Return agent to start point
                    break;
                }
                case MAGIC: {
                    Assets.sfx_hitNoise.play(Game.masterVolume);
                    int dmg = user.getStats().getIntelligence() + user.getCurrentEquipment().getTotalIntelligenceModifiers() + skill.getBasePower()
                            - target.getStats().getArmourVal();
                    String infoBoxText;
                    if (dmg <= 0) {
                        infoBoxText = (target.getName() + " takes 0 damage.");
                    }
                    else {
                        target.dealDamage(dmg);
                        infoBoxText = (target.getName() + " takes " + dmg + " damage");
                    }
                    if(target.isDead())
                        infoBoxText+=" and is defeated.";
                    battleMenu.createInfoBox( infoBoxText, 3);
                    battleAnimator.returnAgent();//Return agent to start point
                    break;
                }
                case HEAL: {
                    Assets.sfx_healNoise.play(Game.masterVolume);
                    target.dealHealth(skill.getBasePower());
                    battleMenu.createInfoBox(target.getName() + " healed for " + skill.getBasePower()
                            + " health", 3);
                    battleAnimator.returnAgent();
                    break;
                }
            }
            user.takeMana(skill.getMPCost());
        } else if(type==1){
            InputHandler.enableAllInput();//re enable input
            battleMenu.showTurnIndicator=false;
            battleMenu.battleScreen.endTurn(); //End the turn

        }
    }

}
