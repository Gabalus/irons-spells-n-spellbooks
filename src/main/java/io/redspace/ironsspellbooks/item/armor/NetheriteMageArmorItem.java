package io.redspace.ironsspellbooks.item.armor;

import io.redspace.ironsspellbooks.entity.armor.netherite.NetheriteMageArmorModel;


import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class NetheriteMageArmorItem extends ImbuableChestplateArmorItem {
    public NetheriteMageArmorItem(Type type, Properties settings) {
        super(ExtendedArmorMaterials.NETHERITE_BATTLEMAGE, type, settings);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new GeoArmorRenderer<>(new NetheriteMageArmorModel());
    }

}
