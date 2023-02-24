package org.example;

import io.github.bloepiloepi.pvp.PvpExtension;
import io.github.bloepiloepi.pvp.config.DamageConfig;
import io.github.bloepiloepi.pvp.config.FoodConfig;
import io.github.bloepiloepi.pvp.config.PotionConfig;
import io.github.bloepiloepi.pvp.config.PvPConfig;
import io.github.bloepiloepi.pvp.explosion.PvpExplosionSupplier;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.scoreboard.Team;

public class Main {

    public static Instance instance;
    public static Team team;
    public static void main(String[] args) {
//        // Initialization
//        MinecraftServer minecraftServer = MinecraftServer.init();
//        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
//        // Create the instance
//        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
//        // Set the ChunkGenerator
//        instanceContainer.setGenerator(unit ->
//                unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));
//        // Add an event callback to specify the spawning instance (and the spawn position)
//        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
//        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
//            final Player player = event.getPlayer();
//            event.setSpawningInstance(instanceContainer);
//            player.setRespawnPoint(new Pos(0, 42, 0));
//        });
//
//
//
//        // Start the server on port 25565
//        minecraftServer.start("0.0.0.0", 25565);
//
//
//        globalEventHandler.addChild(
//                PvPConfig.emptyBuilder()
//                        .potion(PotionConfig.legacyBuilder().drinking(false))
//                        .build().createNode()
//        );
//
//        team = MinecraftServer.getTeamManager().createBuilder("default")
//                .collisionRule(TeamsPacket.CollisionRule.NEVER)
//                .build();
//
//        PvpExtension.init();
//        FoodConfig foodConfig = FoodConfig.emptyBuilder(false).build();
//        DamageConfig damageConfig = DamageConfig.legacyBuilder()
//                .fallDamage(false)
//                .equipmentDamage(false)
//                .exhaustion(false)
//                .build();
//
//        PvPConfig pvPConfig = PvPConfig.legacyBuilder()
//                .food(foodConfig)
//                .damage(damageConfig)
//                .build();
//
//        instance = MinecraftServer.getInstanceManager().getInstances().iterator().next();
//        MinecraftServer.getGlobalEventHandler().addChild(pvPConfig.createNode());
//        instance.setExplosionSupplier(PvpExplosionSupplier.INSTANCE);


    }
}