package invalid.myask.stairdown;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {
    public static int stair_output_qty = 4;
    public static int log_output_qty = 18;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        stair_output_qty = configuration.getInt("stair_output_qty", Configuration.CATEGORY_GENERAL,
            stair_output_qty, 0, Integer.MAX_VALUE, "Stair recipes yield (4 default, 8 preserving-volume).");

        log_output_qty = configuration.getInt("log_output_qty", Configuration.CATEGORY_GENERAL,
            log_output_qty, 0, Integer.MAX_VALUE, "Hollow log recipes yield (18 default, 8 preserving-blocks).");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
