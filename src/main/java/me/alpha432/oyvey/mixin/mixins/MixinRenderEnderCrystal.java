/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.RenderEnderCrystal
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package me.alpha432.oyvey.mixin.mixins;

import java.awt.Color;
import me.alpha432.oyvey.event.events.RenderEntityModelEvent;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.features.modules.render.Chams;
import me.alpha432.oyvey.util.ColorUtil;
import me.alpha432.oyvey.util.EntityUtil;
import me.alpha432.oyvey.util.RenderUtill;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={RenderEnderCrystal.class})
public class MixinRenderEnderCrystal {
    @Shadow
    @Final
    private static ResourceLocation field_110787_a;
    private static ResourceLocation glint;

    @Redirect(method={"doRender"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderModelBaseHook(ModelBase model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (Chams.INSTANCE.isEnabled()) {
            GlStateManager.func_179152_a((float)Chams.INSTANCE.scale.getValue().floatValue(), (float)Chams.INSTANCE.scale.getValue().floatValue(), (float)Chams.INSTANCE.scale.getValue().floatValue());
        }
        if (Chams.INSTANCE.isEnabled() && Chams.INSTANCE.wireframe.getValue().booleanValue()) {
            RenderEntityModelEvent event = new RenderEntityModelEvent(0, model, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Chams.INSTANCE.onRenderModel(event);
        }
        if (Chams.INSTANCE.isEnabled() && Chams.INSTANCE.chams.getValue().booleanValue()) {
            Color visibleColor;
            GL11.glPushAttrib((int)1048575);
            GL11.glDisable((int)3008);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glLineWidth((float)1.5f);
            GL11.glEnable((int)2960);
            if (Chams.INSTANCE.rainbow.getValue().booleanValue()) {
                Color rainbowColor1 = Chams.INSTANCE.rainbow.getValue() != false ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(RenderUtill.getRainbow(20000, 0, 100.0f, 100.0f));
                Color rainbowColor = EntityUtil.getColor(entity, rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue(), Chams.INSTANCE.alpha.getValue(), true);
                if (Chams.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                }
                GL11.glEnable((int)10754);
                GL11.glColor4f((float)((float)rainbowColor.getRed() / 255.0f), (float)((float)rainbowColor.getGreen() / 255.0f), (float)((float)rainbowColor.getBlue() / 255.0f), (float)((float)Chams.INSTANCE.alpha.getValue().intValue() / 255.0f));
                model.func_78088_a(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                if (Chams.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glEnable((int)2929);
                    GL11.glDepthMask((boolean)true);
                }
            } else if (Chams.INSTANCE.xqz.getValue().booleanValue() && Chams.INSTANCE.throughWalls.getValue().booleanValue()) {
                Color hiddenColor = Chams.INSTANCE.rainbow.getValue() != false ? EntityUtil.getColor(entity, Chams.INSTANCE.hiddenRed.getValue(), Chams.INSTANCE.hiddenGreen.getValue(), Chams.INSTANCE.hiddenBlue.getValue(), Chams.INSTANCE.hiddenAlpha.getValue(), true) : EntityUtil.getColor(entity, Chams.INSTANCE.hiddenRed.getValue(), Chams.INSTANCE.hiddenGreen.getValue(), Chams.INSTANCE.hiddenBlue.getValue(), Chams.INSTANCE.hiddenAlpha.getValue(), true);
                visibleColor = Chams.INSTANCE.rainbow.getValue() != false ? EntityUtil.getColor(entity, Chams.INSTANCE.red.getValue(), Chams.INSTANCE.green.getValue(), Chams.INSTANCE.blue.getValue(), Chams.INSTANCE.alpha.getValue(), true) : EntityUtil.getColor(entity, Chams.INSTANCE.red.getValue(), Chams.INSTANCE.green.getValue(), Chams.INSTANCE.blue.getValue(), Chams.INSTANCE.alpha.getValue(), true);
                Color color = visibleColor;
                if (Chams.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                }
                GL11.glEnable((int)10754);
                GL11.glColor4f((float)((float)hiddenColor.getRed() / 255.0f), (float)((float)hiddenColor.getGreen() / 255.0f), (float)((float)hiddenColor.getBlue() / 255.0f), (float)((float)Chams.INSTANCE.alpha.getValue().intValue() / 255.0f));
                model.func_78088_a(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                if (Chams.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glEnable((int)2929);
                    GL11.glDepthMask((boolean)true);
                }
                GL11.glColor4f((float)((float)visibleColor.getRed() / 255.0f), (float)((float)visibleColor.getGreen() / 255.0f), (float)((float)visibleColor.getBlue() / 255.0f), (float)((float)Chams.INSTANCE.alpha.getValue().intValue() / 255.0f));
                model.func_78088_a(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            } else {
                visibleColor = Chams.INSTANCE.rainbow.getValue() != false ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : EntityUtil.getColor(entity, Chams.INSTANCE.red.getValue(), Chams.INSTANCE.green.getValue(), Chams.INSTANCE.blue.getValue(), Chams.INSTANCE.alpha.getValue(), true);
                Color color = visibleColor;
                if (Chams.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                }
                GL11.glEnable((int)10754);
                GL11.glColor4f((float)((float)visibleColor.getRed() / 255.0f), (float)((float)visibleColor.getGreen() / 255.0f), (float)((float)visibleColor.getBlue() / 255.0f), (float)((float)Chams.INSTANCE.alpha.getValue().intValue() / 255.0f));
                model.func_78088_a(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                if (Chams.INSTANCE.throughWalls.getValue().booleanValue()) {
                    GL11.glEnable((int)2929);
                    GL11.glDepthMask((boolean)true);
                }
            }
            GL11.glEnable((int)3042);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)3008);
            GL11.glPopAttrib();
            if (Chams.INSTANCE.glint.getValue().booleanValue()) {
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GlStateManager.func_179141_d();
                GlStateManager.func_179131_c((float)1.0f, (float)0.0f, (float)0.0f, (float)0.13f);
                model.func_78088_a(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GlStateManager.func_179118_c();
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
            }
        } else {
            model.func_78088_a(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
        if (Chams.INSTANCE.isEnabled()) {
            GlStateManager.func_179152_a((float)Chams.INSTANCE.scale.getValue().floatValue(), (float)Chams.INSTANCE.scale.getValue().floatValue(), (float)Chams.INSTANCE.scale.getValue().floatValue());
        }
    }

    static {
        glint = new ResourceLocation("textures/glint");
    }
}

