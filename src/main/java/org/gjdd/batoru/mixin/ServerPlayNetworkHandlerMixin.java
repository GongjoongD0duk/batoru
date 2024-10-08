package org.gjdd.batoru.mixin;

import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import org.gjdd.batoru.component.BatoruDataComponentTypes;
import org.gjdd.batoru.config.BatoruConfigManager;
import org.gjdd.batoru.input.Action;
import org.gjdd.batoru.input.ActionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onPlayerAction", at = @At(value = "HEAD"), cancellable = true)
    private void batoru$injectOnPlayerAction(PlayerActionC2SPacket packet, CallbackInfo info) {
        var action = switch (packet.getAction()) {
            case DROP_ITEM -> Action.DROP;
            case SWAP_ITEM_WITH_OFFHAND -> Action.SWAP_OFFHAND;
            default -> null;
        };
        if (action != null) {
            batoru$handleAction(action, Hand.MAIN_HAND, () -> {}, () -> {
                player.currentScreenHandler.syncState();
                info.cancel();
            });
        }
    }

    @Inject(method = "onPlayerInteractItem", at = @At(value = "HEAD"), cancellable = true)
    private void batoru$injectOnPlayerInteractItem(PlayerInteractItemC2SPacket packet, CallbackInfo info) {
        batoru$handleAction(Action.INTERACT_ITEM, packet.getHand(), () -> {}, info::cancel);
    }

    @Inject(method = "onUpdateSelectedSlot", at = @At(value = "HEAD"), cancellable = true)
    private void batoru$injectOnUpdateSelectedSlot(UpdateSelectedSlotC2SPacket packet, CallbackInfo info) {
        var action = ActionUtil.getHotbarAction(packet.getSelectedSlot());
        if (action != null) {
            batoru$handleAction(action, Hand.MAIN_HAND, () -> {
                player.networkHandler.sendPacket(new UpdateSelectedSlotS2CPacket(player.getInventory().selectedSlot));
                info.cancel();
            }, () -> {});
        }
    }

    @Inject(method = "onHandSwing", at = @At(value = "HEAD"))
    private void batoru$injectOnHandSwing(HandSwingC2SPacket packet, CallbackInfo info) {
        batoru$handleAction(Action.HAND_SWING, packet.getHand(), () -> {}, () -> {});
    }

    @Inject(method = "onClientCommand", at = @At(value = "HEAD"))
    private void batoru$injectOnClientCommand(ClientCommandC2SPacket packet, CallbackInfo info) {
        var action = packet.getMode() == ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY ? Action.SNEAK : null;
        if (action != null) {
            batoru$handleAction(action, Hand.MAIN_HAND, () -> {}, () -> {});
        }
    }

    @Unique
    private void batoru$handleAction(Action action, Hand hand, Runnable onValidSlot, Runnable onSlotActivated) {
        var job = player.getJob();
        if (job == null) {
            return;
        }

        var skillSlot = BatoruConfigManager.INSTANCE.getConfig().skillSlotMappings().get(action);
        if (skillSlot == null) {
            return;
        }

        var usableJob = player.getStackInHand(hand).get(BatoruDataComponentTypes.USABLE_JOB);
        if (usableJob != null && usableJob.equals(job)) {
            var skill = job.value().getSkillMap().get(skillSlot);
            if (skill != null) {
                player.useSkill(skill);
            }

            onSlotActivated.run();
        }

        onValidSlot.run();
    }
}
