package invalid.myask.stairdown;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static int stair_output_qty = 4;
    public static int log_output_qty = 18;
    public static int small_log_output_qty = 5;
    public static int hollow_thickness = 2;

    public static boolean reconstitute_slabs = true;

    public static boolean enable_vanilla_logstairs = true;
    public static boolean enable_vanilla_hollow_logs = true;
    public static boolean enable_vanilla_logslabs = true;

    public static boolean enable_giant_bamboo = true;
    public static boolean enable_hollow_bamboo = true;
    public static boolean enable_slab_bamboo = true;

    public static boolean enable_osha_noncompliant_stairs = true;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        stair_output_qty = configuration.getInt(
            "stair_output_qty",
            Configuration.CATEGORY_GENERAL,
            stair_output_qty,
            0,
            Integer.MAX_VALUE,
            "Stair recipes yield (4 default, 8 preserving-volume).");

        log_output_qty = configuration.getInt(
            "log_output_qty",
            Configuration.CATEGORY_GENERAL,
            log_output_qty,
            0,
            Integer.MAX_VALUE,
            "Hollow log 8box recipes yield (18 default, 8 preserving-blocks, <=0 to disable).");
        small_log_output_qty = configuration.getInt(
            "small_log_output_qty",
            Configuration.CATEGORY_GENERAL,
            small_log_output_qty,
            0,
            Integer.MAX_VALUE,
            "Hollow log NSWE recipe yield (<=0 to disable)");

        hollow_thickness = configuration.getInt(
            "hollow_thickness",
            Configuration.CATEGORY_GENERAL,
            hollow_thickness,
            0,
            8,
            "Hollow log thickness in standard MC pixels (0-8)");

        reconstitute_slabs = configuration.getBoolean(
            "reconstitute_slabs",
            "recipes",
            reconstitute_slabs,
            "Add remaking blocks from their [new] slabs recipe--doesn't make for pre-existing slabs.");

        enable_vanilla_logstairs = configuration.getBoolean(
            "enable_vanilla_logstairs",
            "addblocks.preset",
            enable_vanilla_logstairs,
            "Register/add recipes for stairs of vanilla logs.");
        enable_vanilla_hollow_logs = configuration.getBoolean(
            "enable_vanilla_hollow_logs",
            "addblocks.preset",
            enable_vanilla_hollow_logs,
            "Register/add recipes for hollows of vanilla logs.");
        enable_vanilla_logslabs = configuration.getBoolean(
            "enable_vanilla_logslabs",
            "addblocks.preset",
            enable_vanilla_logslabs,
            "Register/add recipes for slabs of vanilla logs.");
        enable_osha_noncompliant_stairs = configuration.getBoolean(
            "enable_osha_noncompliant_stairs",
            "addblocks.preset",
            enable_osha_noncompliant_stairs,
            "Register/add recipes for OSHA-noncompliant combination workplace-stairs.");

        enable_giant_bamboo = configuration.getBoolean(
            "enable_giant_bamboo",
            "addblocks.preset",
            enable_giant_bamboo,
            "Register/add recipe for giant bamboo.");
        enable_hollow_bamboo = configuration.getBoolean(
            "enable_hollow_bamboo",
            "addblocks.preset",
            enable_hollow_bamboo,
            "Register/add recipes for hollows of giant bamboo.");
        enable_slab_bamboo = configuration.getBoolean(
            "enable_slab_bamboo",
            "addblocks.preset",
            enable_slab_bamboo,
            "Register/add recipes for slabs of giant bamboo.");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
