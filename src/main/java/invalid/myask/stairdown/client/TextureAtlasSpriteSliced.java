package invalid.myask.stairdown.client;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class TextureAtlasSpriteSliced extends TextureAtlasSprite {

    protected TextureAtlasSprite parent;
    protected float minV, maxV;

    public TextureAtlasSpriteSliced(TextureAtlasSprite parent) {
        super(parent.getIconName());
        this.parent = parent;
        this.copyFrom(parent);
        float height = parent.getMaxV() - parent.getMinV();
        float sixPixels = height * 6 / 16;
        minV = parent.getMinV() + sixPixels;
        maxV = parent.getMaxV() - sixPixels;
    }

    @Override
    public float getMinV() {
        return minV;
    }

    @Override
    public float getMaxV() {
        return maxV;
    }

    @Override // gotta because parent's Vs were private
    public float getInterpolatedV(double pixels) {
        return minV + (float) ((maxV - minV) * pixels / 16);
    }
}
