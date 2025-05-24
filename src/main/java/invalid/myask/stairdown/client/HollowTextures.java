package invalid.myask.stairdown.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;

import java.util.HashMap;
import java.util.Map;

public class HollowTextures {
    public static final HollowTextures instance = new HollowTextures();
    private static Map<String, TextureAtlasSprite> HOLLOW_ICONS = new HashMap<>();

    public static IIcon getInsideIcon(IIcon icon) {
        TextureAtlasSprite result = HOLLOW_ICONS.get(icon.getIconName());
        if (result == null || (result.getOriginX() == 0 && result.getOriginY() == 0)) {
            result = loadInsideIcon(icon);
        }
        return result;
    }

    public static TextureAtlasSpriteSliced loadInsideIcon(IIcon icon) {
        TextureAtlasSpriteSliced result = new TextureAtlasSpriteSliced((TextureAtlasSprite) icon);
        HOLLOW_ICONS.put(icon.getIconName(), result);
        return result;
    }

    @SubscribeEvent
    public void applyToStitched(TextureStitchEvent.Post event) {
        for (TextureAtlasSprite icon : HOLLOW_ICONS.values())
            loadInsideIcon(icon);
    }
}
