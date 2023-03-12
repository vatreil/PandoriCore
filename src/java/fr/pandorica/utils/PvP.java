package fr.pandorica.utils;

import io.github.bloepiloepi.pvp.PvpExtension;
import io.github.bloepiloepi.pvp.config.DamageConfig;
import io.github.bloepiloepi.pvp.config.FoodConfig;
import io.github.bloepiloepi.pvp.config.PvPConfig;
import io.github.bloepiloepi.pvp.explosion.PvpExplosionSupplier;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.scoreboard.Team;

public class PvP {
    public static void loadLegacy(Instance instance){
        PvpExtension.init();
        FoodConfig foodConfig = FoodConfig.emptyBuilder(false).build();
        DamageConfig damageConfig = DamageConfig.legacyBuilder()
                .fallDamage(false)
                .equipmentDamage(false)
                .exhaustion(false)
                .build();

        PvPConfig pvPConfig = PvPConfig.legacyBuilder()
                .food(foodConfig)
                .damage(damageConfig)
                .build();

        MinecraftServer.getGlobalEventHandler().addChild(pvPConfig.createNode());
        instance.setExplosionSupplier(PvpExplosionSupplier.INSTANCE);
    }

    public static Team disableCollision(){
        return MinecraftServer.getTeamManager().createBuilder("default")
                .collisionRule(TeamsPacket.CollisionRule.NEVER)
                .build();
    }


}
