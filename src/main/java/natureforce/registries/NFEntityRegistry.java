package natureforce.registries;

import natureforce.NatureForce;
import natureforce.entities.EntityEnergyBall;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class NFEntityRegistry {
    private static int id;

    public void preInit() {
        id = 0;
        registerEntity(EntityEnergyBall.class, "energyBall");
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange, int trackingFrequency, boolean velocityUpdates) {

        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(entityClass, name, id, NatureForce.instance, trackingRange, trackingFrequency, velocityUpdates);
        id++;
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name) {
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(entityClass, name, id, NatureForce.instance, 64, 3, true);
        id++;
    }

    private static void registerEntity(Class<? extends EntityLiving> entityClass, String name, int eggBackgroundColor, int eggForegroundColor, int trackingRange, int trackingFrequency, boolean velocityUpdates) {
        registerEntity(entityClass, name, trackingRange, trackingFrequency, velocityUpdates);
        net.minecraftforge.fml.common.registry.EntityRegistry.registerEgg(entityClass, eggBackgroundColor, eggForegroundColor);
    }

    private static void registerEntity(Class<? extends EntityLiving> entityClass, String name, int eggBackgroundColor, int eggForegroundColor) {
        registerEntity(entityClass, name);
        net.minecraftforge.fml.common.registry.EntityRegistry.registerEgg(entityClass, eggBackgroundColor, eggForegroundColor);
    }
}
