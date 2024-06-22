package io.redspace.ironsspellbooks.registries;

import io.redspace.ironsspellbooks.gui.overlays.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;


@EventBusSubscriber(Dist.CLIENT)

public class OverlayRegistry {

    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiLayersEvent event) {
        //Ironsspellbooks.logger.debug("Registering Overlays");

        event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "cast_bar", CastBarOverlay.instance);
//
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "mana_overlay", ManaBarOverlay.instance);
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "spell_bar", SpellBarOverlay.instance);
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "scroll_overlay", ActiveSpellOverlay.instance);
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "recast_bar", RecastOverlay.instance);

        event.registerAbove(VanillaGuiOverlay.PLAYER_LIST.id(), "spell_wheel", SpellWheelOverlay.instance);
        event.registerAbove(VanillaGuiOverlay.PLAYER_LIST.id(), "screen_effects", ScreenEffectsOverlay.instance);
    }
}
