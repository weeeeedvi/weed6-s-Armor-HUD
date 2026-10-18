package com.weedvi.armorhud.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ModConfig config = ModConfig.get();

            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("weed6's Armor HUD Configuration"));

            builder.setSavingRunnable(ModConfig::save);

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            // Tab 1: General Settings
            ConfigCategory generalCat = builder.getOrCreateCategory(Text.literal("General"));

            generalCat.addEntry(entryBuilder.startBooleanToggle(Text.literal("Mod Enabled"), config.enabled)
                    .setDefaultValue(true)
                    .setTooltip(Text.literal("Enable or disable the Armor HUD overlay"))
                    .setSaveConsumer(v -> config.enabled = v)
                    .build());

            generalCat.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Empty Armor Slots"), config.showEmptySlots)
                    .setDefaultValue(false)
                    .setTooltip(Text.literal("Display HUD entries even when an armor slot is empty"))
                    .setSaveConsumer(v -> config.showEmptySlots = v)
                    .build());

            generalCat.addEntry(entryBuilder.startBooleanToggle(Text.literal("Durability Text on Left"), config.textOnLeft)
                    .setDefaultValue(true)
                    .setYesNoTextSupplier(v -> Text.literal(v ? "Left" : "Right"))
                    .setTooltip(Text.literal("Side of the armor icon where the durability number is shown"))
                    .setSaveConsumer(v -> config.textOnLeft = v)
                    .build());

            // Tab 2: Armor Positions & Scaling
            ConfigCategory armorCat = builder.getOrCreateCategory(Text.literal("Armor Positions & Scaling"));

            // --- HELMET ---
            armorCat.addEntry(entryBuilder.startIntField(Text.literal("Helmet Position X"), config.helmetX)
                    .setDefaultValue(212)
                    .setTooltip(Text.literal("X coordinate for the Helmet HUD"))
                    .setSaveConsumer(v -> config.helmetX = v)
                    .build());

            armorCat.addEntry(entryBuilder.startIntField(Text.literal("Helmet Position Y"), config.helmetY)
                    .setDefaultValue(305)
                    .setTooltip(Text.literal("Y coordinate for the Helmet HUD"))
                    .setSaveConsumer(v -> config.helmetY = v)
                    .build());

            addScaleSlider(armorCat, entryBuilder, "Helmet Scale", config.helmetScale, 85,
                    v -> config.helmetScale = v / 100.0f);

            // --- CHESTPLATE ---
            armorCat.addEntry(entryBuilder.startIntField(Text.literal("Chestplate Position X"), config.chestX)
                    .setDefaultValue(212)
                    .setTooltip(Text.literal("X coordinate for the Chestplate HUD"))
                    .setSaveConsumer(v -> config.chestX = v)
                    .build());

            armorCat.addEntry(entryBuilder.startIntField(Text.literal("Chestplate Position Y"), config.chestY)
                    .setDefaultValue(320)
                    .setTooltip(Text.literal("Y coordinate for the Chestplate HUD"))
                    .setSaveConsumer(v -> config.chestY = v)
                    .build());

            addScaleSlider(armorCat, entryBuilder, "Chestplate Scale", config.chestScale, 85,
                    v -> config.chestScale = v / 100.0f);

            // --- LEGGINGS ---
            armorCat.addEntry(entryBuilder.startIntField(Text.literal("Leggings Position X"), config.legsX)
                    .setDefaultValue(433)
                    .setTooltip(Text.literal("X coordinate for the Leggings HUD"))
                    .setSaveConsumer(v -> config.legsX = v)
                    .build());

            armorCat.addEntry(entryBuilder.startIntField(Text.literal("Leggings Position Y"), config.legsY)
                    .setDefaultValue(305)
                    .setTooltip(Text.literal("Y coordinate for the Leggings HUD"))
                    .setSaveConsumer(v -> config.legsY = v)
                    .build());

            addScaleSlider(armorCat, entryBuilder, "Leggings Scale", config.legsScale, 85,
                    v -> config.legsScale = v / 100.0f);

            // --- BOOTS ---
            armorCat.addEntry(entryBuilder.startIntField(Text.literal("Boots Position X"), config.bootsX)
                    .setDefaultValue(433)
                    .setTooltip(Text.literal("X coordinate for the Boots HUD"))
                    .setSaveConsumer(v -> config.bootsX = v)
                    .build());

            armorCat.addEntry(entryBuilder.startIntField(Text.literal("Boots Position Y"), config.bootsY)
                    .setDefaultValue(320)
                    .setTooltip(Text.literal("Y coordinate for the Boots HUD"))
                    .setSaveConsumer(v -> config.bootsY = v)
                    .build());

            addScaleSlider(armorCat, entryBuilder, "Boots Scale", config.bootsScale, 88,
                    v -> config.bootsScale = v / 100.0f);

            return builder.build();
        };
    }

    private static void addScaleSlider(ConfigCategory category, ConfigEntryBuilder entryBuilder,
                                       String label, float currentScale, int defaultPercent, Consumer<Integer> saveConsumer) {
        int percent = Math.round(currentScale * 100);
        category.addEntry(entryBuilder.startIntSlider(Text.literal(label + " Scale (%)"), percent, 10, 500)
                .setDefaultValue(defaultPercent)
                .setTooltip(Text.literal(label + " scale slider. 100% = normal size, range 10% to 500%"))
                .setSaveConsumer(saveConsumer)
                .build());
    }
}
