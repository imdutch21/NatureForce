package natureforce.client.render;

import natureforce.tileentities.TileEntityDeathPlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

@SideOnly(Side.CLIENT)
public class RenderDeathPlant extends TileEntitySpecialRenderer<TileEntityDeathPlant> {

    @Override
    public void renderTileEntityAt(TileEntityDeathPlant te, double x, double y, double z, float partialTicks, int destroyStage) {
        if (te.getBlock() != null) {
            IBlockState iblockstate = te.getBlock();

            if (iblockstate.getRenderType() == EnumBlockRenderType.MODEL) {
                World world = te.getWorld();

                if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
                    this.bindTexture(TextureMap.locationBlocksTexture);

                    GlStateManager.pushMatrix();
                    GlStateManager.disableLighting();
                    GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL14.glBlendColor(0f, 0f, 0f, 1f);

                    Tessellator tessellator = Tessellator.getInstance();
                    VertexBuffer vertexbuffer = tessellator.getBuffer();
                    vertexbuffer.begin(7, DefaultVertexFormats.BLOCK);
                    BlockPos blockpos = new BlockPos(te.getPos().getX(), te.getRenderBoundingBox().maxY, te.getPos().getZ());
                    GlStateManager.translate((float) (x - (double) blockpos.getX()), (float) (y - (double) blockpos.getY()), (float) (z - (double) blockpos.getZ()));
                    BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                    blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(iblockstate), iblockstate, blockpos, vertexbuffer, false, MathHelper.getPositionRandom(te.getPos()));
                    tessellator.draw();
                    GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
                    GL11.glDisable(GL11.GL_BLEND);
                    GlStateManager.enableLighting();
                    GlStateManager.popMatrix();
                }
            }
        }
    }
}
