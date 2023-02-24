package fr.pandorica.utils;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.DynamicChunk;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.InstanceContainer;

public class World {
    public static void load(GlobalEventHandler globalEventHandler, String path, Pos pos){
            IChunkLoader loader = new AnvilLoader(path);
            InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer(loader);
            instance.setChunkSupplier(DynamicChunk::new);
    }
}
