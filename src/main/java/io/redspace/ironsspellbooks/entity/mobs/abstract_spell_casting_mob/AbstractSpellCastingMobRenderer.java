package io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob;


import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import io.redspace.ironsspellbooks.render.GeoChargeSpellLayer;
import io.redspace.ironsspellbooks.render.GeoEvasionLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.example.client.DefaultBipedBoneIdents;
import software.bernie.example.client.EntityResources;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

import java.util.List;

public abstract class AbstractSpellCastingMobRenderer extends ExtendedGeoEntityRenderer<AbstractSpellCastingMob> {
    private ResourceLocation textureResource;

    public AbstractSpellCastingMobRenderer(EntityRendererProvider.Context renderManager, AbstractSpellCastingMobModel model) {
        super(renderManager, model);
        this.shadowRadius = 0.5f;
        this.addLayer(new GeoEvasionLayer(this));
        this.addLayer(new GeoChargeSpellLayer(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, AbstractSpellCastingMob animatable) {
        if ("bipedCape".equals(boneName))
            return EntityResources.EXTENDED_CAPE_TEXTURE;

        return modelProvider.getTextureResource(animatable);
    }

    @Override
    protected boolean isArmorBone(GeoBone bone) {
        return bone.getName().startsWith("armor");
    }

    @Override
    protected ModelPart getArmorPartForBone(String name, HumanoidModel<?> armorModel) {
        return switch (name) {
            case DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_2_IDENT -> armorModel.leftLeg;
            case DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_2_IDENT -> armorModel.rightLeg;
            case DefaultBipedBoneIdents.RIGHT_ARM_ARMOR_BONE_IDENT -> armorModel.rightArm;
            case DefaultBipedBoneIdents.LEFT_ARM_ARMOR_BONE_IDENT -> armorModel.leftArm;
            case DefaultBipedBoneIdents.BODY_ARMOR_BONE_IDENT -> armorModel.body;
            case DefaultBipedBoneIdents.HEAD_ARMOR_BONE_IDENT -> armorModel.head;
            default -> null;
        };
    }

    @Override
    protected void prepareArmorPositionAndScale(GeoBone bone, List<ModelPart.Cube> cubeList, ModelPart sourceLimb, PoseStack poseStack, boolean geoArmor, boolean modMatrixRot) {
        if (bone.getName().equals(GenericCustomArmorRenderer.leggingTorsoLayerBone)) {
            super.prepareArmorPositionAndScale((GeoBone) this.modelProvider.getBone(DefaultBipedBoneIdents.BODY_ARMOR_BONE_IDENT), cubeList, sourceLimb, poseStack, false, modMatrixRot);
        } else {
            super.prepareArmorPositionAndScale(bone, cubeList, sourceLimb, poseStack, false, modMatrixRot);
        }
    }

    @Override
    protected EquipmentSlot getEquipmentSlotForArmorBone(String boneName, AbstractSpellCastingMob currentEntity) {
        return switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_2_IDENT -> EquipmentSlot.FEET;
            case DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_2_IDENT -> EquipmentSlot.LEGS;
            case DefaultBipedBoneIdents.RIGHT_ARM_ARMOR_BONE_IDENT ->
                    !currentEntity.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            case DefaultBipedBoneIdents.LEFT_ARM_ARMOR_BONE_IDENT ->
                    currentEntity.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            case DefaultBipedBoneIdents.BODY_ARMOR_BONE_IDENT -> EquipmentSlot.CHEST;
            case DefaultBipedBoneIdents.HEAD_ARMOR_BONE_IDENT -> EquipmentSlot.HEAD;
            default -> null;
        };
    }

    @Override
    protected ItemStack getArmorForBone(String boneName, AbstractSpellCastingMob currentEntity) {
        return switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_FOOT_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.RIGHT_FOOT_ARMOR_BONE_2_IDENT ->
                    currentEntity.getItemBySlot(EquipmentSlot.FEET);
            case DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_LEG_ARMOR_BONE_2_IDENT,
                    DefaultBipedBoneIdents.RIGHT_LEG_ARMOR_BONE_2_IDENT ->
                    currentEntity.getItemBySlot(EquipmentSlot.LEGS);
            case DefaultBipedBoneIdents.BODY_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.RIGHT_ARM_ARMOR_BONE_IDENT,
                    DefaultBipedBoneIdents.LEFT_ARM_ARMOR_BONE_IDENT ->
                    currentEntity.getItemBySlot(EquipmentSlot.CHEST);
            case DefaultBipedBoneIdents.HEAD_ARMOR_BONE_IDENT -> currentEntity.getItemBySlot(EquipmentSlot.HEAD);
            default -> null;
        };
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, AbstractSpellCastingMob entity) {
        return switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_HAND_BONE_IDENT ->
                    entity.isLeftHanded() ? entity.getMainHandItem() : entity.getOffhandItem();
            case DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT ->
                    entity.isLeftHanded() ? entity.getOffhandItem() : entity.getMainHandItem();
            default -> null;
        };
    }

    @Override
    protected ItemTransforms.TransformType getCameraTransformForItemAtBone(ItemStack stack, String boneName) {
        return switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_HAND_BONE_IDENT, DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT ->
                    ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND; // Do Defaults
            default -> ItemTransforms.TransformType.NONE;
        };
    }

    @Nullable
    @Override
    protected BlockState getHeldBlockForBone(String boneName, AbstractSpellCastingMob animatable) {
        return null;
    }

    @Override
    protected void preRenderItem(PoseStack poseStack, ItemStack itemStack, String boneName, AbstractSpellCastingMob animatable, IBone bone) {
        var mainHandItem = animatable.getMainHandItem();
        var offHandItem = animatable.getOffhandItem();
        if (itemStack == mainHandItem) {
            poseStack.mulPose(Vector3f.XP.rotationDegrees(-90f));

            if (itemStack.getItem() instanceof ShieldItem)
                poseStack.translate(0, 0.125, -0.25);
        } else if (itemStack == offHandItem) {
            poseStack.mulPose(Vector3f.XP.rotationDegrees(-90f));

            if (itemStack.getItem() instanceof ShieldItem) {
                poseStack.translate(0, 0.125, 0.25);
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
            }
        }
    }

    @Override
    protected void preRenderBlock(PoseStack poseStack, BlockState state, String boneName, AbstractSpellCastingMob animatable) {

    }

    @Override
    protected void postRenderItem(PoseStack poseStack, ItemStack stack, String boneName, AbstractSpellCastingMob animatable, IBone bone) {

    }

    @Override
    protected void postRenderBlock(PoseStack poseStack, BlockState state, String boneName, AbstractSpellCastingMob animatable) {

    }

}