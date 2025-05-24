package invalid.myask.stairdown.client;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.IIcon;

import java.util.HashMap;
import java.util.Map;

public class HollowTextures {
    private static Map<String, TextureAtlasSprite> HOLLOW_ICONS = new HashMap<>();

    public static IIcon getInsideIcon(IIcon icon) {
        TextureAtlasSprite result = HOLLOW_ICONS.get(icon.getIconName());
        if (result == null) {
            result = new TextureAtlasSpriteSliced((TextureAtlasSprite) icon);
            HOLLOW_ICONS.put(icon.getIconName(), result);
        }
        return result;
    }
}
