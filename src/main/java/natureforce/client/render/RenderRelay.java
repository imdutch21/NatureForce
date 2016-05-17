package natureforce.client.render;

import com.sun.javafx.geom.Vec3f;
import com.sun.org.apache.xml.internal.security.encryption.Reference;
import natureforce.api.natureunits.IEnergyBeamConnection;
import natureforce.lib.References;
import natureforce.tileentities.TileEntityRelay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderRelay extends TileEntitySpecialRenderer<TileEntityRelay> {
    private static final ResourceLocation beamTexture = new ResourceLocation(References.ASSETS_PREFIX + "textures/entity/unitBeam.png");

    @Override
    public void renderTileEntityAt(TileEntityRelay te, double x, double y, double z, float partialTicks, int destroyStage) {
        if (te.connectedTo != null) {
            TileEntity tileEntity = te.getWorld().getTileEntity(te.connectedTo);
            if (tileEntity instanceof IEnergyBeamConnection) {
                Vec3f beamStart = new Vec3f(te.getPos().getX() + te.connectionPointX(), te.getPos().getY() + te.connectionPointY(), te.getPos().getZ() + te.connectionPointZ());
                Vec3f beamEnd = new Vec3f(tileEntity.getPos().getX() + ((IEnergyBeamConnection) tileEntity).connectionPointX(), tileEntity.getPos().getY() + ((IEnergyBeamConnection) tileEntity).connectionPointY(), tileEntity.getPos().getZ() + ((IEnergyBeamConnection) tileEntity).connectionPointZ());
                beamEnd.sub(beamStart);
                BlockPos offset = te.connectedTo.subtract(te.getPos());
                int dx = offset.getX();
                int dy = offset.getY();
                int dz = offset.getZ();
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                double subDistance = MathHelper.sqrt_double(dx * dx + dz * dz);
                float rotYaw = -((float) (Math.atan2(dz, dx) * 180.0D / Math.PI));
                float rotPitch = ((float) (Math.atan2(dy, subDistance) * 180.0D / Math.PI));

                GlStateManager.pushMatrix();
                Tessellator tessellator = Tessellator.getInstance();
                VertexBuffer vertexBuffer = tessellator.getBuffer();
                this.bindTexture(beamTexture);

                GlStateManager.glTexParameteri(3553, 10242, 10497);
                GlStateManager.glTexParameteri(3553, 10243, 10497);
                GlStateManager.disableLighting();
                GlStateManager.disableCull();
                GlStateManager.enableTexture2D();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                GlStateManager.translate(x + te.connectionPointX(), y + te.connectionPointY(), z + te.connectionPointZ());

                GlStateManager.rotate(rotYaw, 0, 1, 0);
                GlStateManager.rotate(rotPitch, 0, 0, 1);

                double d1 = 0.08D;
                double d2 = -1.0D + MathHelper.frac((partialTicks + te.getWorld().getTotalWorldTime()) * 0.2D - (double)MathHelper.floor_double((partialTicks+ te.getWorld().getTotalWorldTime()) * 0.1D));
                double d3 = distance * 0.5D + d2;

                vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                vertexBuffer.pos(distance, -d1, -d1).tex(1.0D, d3).endVertex();
                vertexBuffer.pos(0, -d1, -d1).tex(1.0D, d2).endVertex();
                vertexBuffer.pos(0, d1, -d1).tex(0.0D, d2).endVertex();
                vertexBuffer.pos(distance, d1, -d1).tex(0.0D, d3).endVertex();
                vertexBuffer.pos(distance, d1, d1).tex(1.0D, d3).endVertex();
                vertexBuffer.pos(0, d1, d1).tex(1.0D, d2).endVertex();
                vertexBuffer.pos(0, -d1, d1).tex(0.0D, d2).endVertex();
                vertexBuffer.pos(distance, -d1, d1).tex(0.0D, d3).endVertex();
                vertexBuffer.pos(distance, d1, -d1).tex(1.0D, d3).endVertex();
                vertexBuffer.pos(0, d1, -d1).tex(1.0D, d2).endVertex();
                vertexBuffer.pos(0, d1, d1).tex(0.0D, d2).endVertex();
                vertexBuffer.pos(distance, d1, d1).tex(0.0D, d3).endVertex();
                vertexBuffer.pos(distance, -d1, d1).tex(1.0D, d3).endVertex();
                vertexBuffer.pos(0, -d1, d1).tex(1.0D, d2).endVertex();
                vertexBuffer.pos(0, -d1, -d1).tex(0.0D, d2).endVertex();
                vertexBuffer.pos(distance, -d1, -d1).tex(0.0D, d3).endVertex();

                tessellator.draw();
                GlStateManager.enableLighting();
                GlStateManager.enableCull();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();

            }
        }
    }
}
