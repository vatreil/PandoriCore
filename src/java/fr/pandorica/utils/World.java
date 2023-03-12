package fr.pandorica.utils;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.DynamicChunk;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.InstanceContainer;



public class World {
    public static InstanceContainer instanceContainer;
    public static void load(String path){
        IChunkLoader loader = new AnvilLoader(path);
        instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer(loader);
        instanceContainer.setChunkSupplier(DynamicChunk::new);
    }
}
