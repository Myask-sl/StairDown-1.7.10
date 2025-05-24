package invalid.myask.stairdown;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {
    public static int stair_output_qty = 4;
    public static int log_output_qty = 18;
    public static int small_log_output_qty = 5;
    public static int hollow_thickness = 2;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        stair_output_qty = configuration.getInt("stair_output_qty", Configuration.CATEGORY_GENERAL,
            stair_output_qty, 0, Integer.MAX_VALUE, "Stair recipes yield (4 default, 8 preserving-volume).");

        log_output_qty = configuration.getInt("log_output_qty", Configuration.CATEGORY_GENERAL,
            log_output_qty, 0, Integer.MAX_VALUE, "Hollow log 8box recipes yield (18 default, 8 preserving-blocks, <=0 to disable).");
        small_log_output_qty = configuration.getInt("small_log_output_qty", Configuration.CATEGORY_GENERAL,
            small_log_output_qty, 0, Integer.MAX_VALUE, "Hollow log NSWE recipe yield (<=0 to disable)");

        hollow_thickness = configuration.getInt("hollow_thickness", Configuration.CATEGORY_GENERAL,
            hollow_thickness, 0, 8, "Hollow log thickness in standard MC pixels (0-8)");
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
