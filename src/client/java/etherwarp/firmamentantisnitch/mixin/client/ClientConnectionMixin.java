package etherwarp.firmamentantisnitch.mixin.client;

import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class ClientConnectionMixin {

    @Unique
    private static final ResourceLocation FIRMAMENT_MOD_LIST =
            ResourceLocation.fromNamespaceAndPath("firmament", "mod_list");

    @Inject(
            method = "send(Lnet/minecraft/network/protocol/Packet;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void blockFirmamentModList(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof ServerboundCustomPayloadPacket(
                net.minecraft.network.protocol.common.custom.CustomPacketPayload payload
        )) {
            if (FIRMAMENT_MOD_LIST.equals(payload.type().id())) {
                ci.cancel();
            }
        }
    }
}