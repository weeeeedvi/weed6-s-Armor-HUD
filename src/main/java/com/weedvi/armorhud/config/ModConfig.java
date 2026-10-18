package com.weedvi.armorhud.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("weed6s_armor_hud.json").toFile();

    private static ModConfig INSTANCE;

    // General
    public boolean enabled = true;
    public boolean showEmptySlots = false;
    public boolean textOnLeft = true;

    // Helmet
    public int helmetX = 212;
    public int helmetY = 305;
    public float helmetScale = 0.85f;

    // Chestplate
    public int chestX = 212;
    public int chestY = 320;
    public float chestScale = 0.85f;

    // Leggings
    public int legsX = 433;
    public int legsY = 305;
    public float legsScale = 0.85f;

    // Boots
    public int bootsX = 433;
    public int bootsY = 320;
    public float bootsScale = 0.88f;

    public static ModConfig get() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                INSTANCE = GSON.fromJson(reader, ModConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (INSTANCE == null) {
            INSTANCE = new ModConfig();
        }
    }

    public static void save() {
        if (INSTANCE == null) {
            INSTANCE = new ModConfig();
        }
        try {
            if (CONFIG_FILE.getParentFile() != null) {
                CONFIG_FILE.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(INSTANCE, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
