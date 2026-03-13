package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;
import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill skillA, Skill skillB) {
        RaidResult result = new RaidResult();
        int round = 1;
        int maxRounds = 20;

        while (round <= maxRounds && teamA.isAlive() && teamB.isAlive()) {
            result.addLine("--- Round " + round + " ---");

            result.addLine(teamA.getName() + " uses " + skillA.getSkillName() + " on " + teamB.getName());
            skillA.cast(teamB);

            if (teamB.isAlive()) {
                result.addLine(teamB.getName() + " uses " + skillB.getSkillName() + " on " + teamA.getName());
                skillB.cast(teamA);
            }

            result.addLine(teamA.getName() + " HP: " + teamA.getHealth() + " | " + teamB.getName() + " HP: " + teamB.getHealth());
            round++;
        }

        result.setRounds(round - 1);
        if (!teamA.isAlive() && !teamB.isAlive()) result.setWinner("Draw");
        else if (teamA.isAlive()) result.setWinner(teamA.getName());
        else result.setWinner(teamB.getName());

        return result;
    }
}