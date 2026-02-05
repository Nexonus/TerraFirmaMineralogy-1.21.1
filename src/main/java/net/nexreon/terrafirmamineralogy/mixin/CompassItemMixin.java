package net.nexreon.terrafirmamineralogy.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CompassItemPropertyFunction.class)
public abstract class CompassItemMixin {
    @Shadow
    protected abstract float getRotationTowardsCompassTarget(Entity entity, long ticks, BlockPos pos);

    @Shadow
    protected abstract float getRandomlySpinningRotation(int seed, long ticks);

    @Inject(method = "getCompassRotation", at = @At("HEAD"), cancellable = true)
    private void tfcm$magnetiteOverride(ItemStack stack, ClientLevel level, int seed, Entity entity, CallbackInfoReturnable<Float> cir) {
        if (entity == null || level == null) return;

        BlockPos playerPos = entity.blockPosition();
        int radius = 7;
        long gameTime = level.getGameTime();

        double totalX = 0, totalY = 0, totalZ = 0;
        int count = 0;

        for (BlockPos pos : BlockPos.betweenClosed(playerPos.offset(-radius, -radius, -radius), playerPos.offset(radius, radius, radius))) {
            BlockState state = level.getBlockState(pos);

            if (BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath().contains("magnetite")) {
                totalX += pos.getX() + 0.5;
                totalY += pos.getY() + 0.5;
                totalZ += pos.getZ() + 0.5;
                count++;
            }
        }

        if (count > 0) {
            double avgX = totalX / count;
            double avgY = totalY / count;
            double avgZ = totalZ / count;
            BlockPos averagePos = BlockPos.containing(avgX, avgY, avgZ);

            double distanceSq = entity.distanceToSqr(avgX, avgY, avgZ);

            if (distanceSq < 2.25) {
                cir.setReturnValue(this.getRandomlySpinningRotation(seed, gameTime));
            } else {
                float rotation = this.getRotationTowardsCompassTarget(entity, gameTime, averagePos);
                cir.setReturnValue(rotation);
            }
        }
    }
}