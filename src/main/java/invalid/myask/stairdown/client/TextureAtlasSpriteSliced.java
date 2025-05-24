package invalid.myask.stairdown.client;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class TextureAtlasSpriteSliced extends TextureAtlasSprite {
    TextureAtlasSprite parent;
    public TextureAtlasSpriteSliced(TextureAtlasSprite parent) {
        super(parent.getIconName());
        this.parent = parent;
        this.copyFrom(parent);
        initSprite(parent.getIconWidth(), parent.getIconHeight()/4, parent.getOriginX(),
            parent.getOriginY() + (parent.getIconHeight() * 3 / 8), rotated);
    }
}
