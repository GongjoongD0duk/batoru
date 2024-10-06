package org.gjdd.batoru.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * {@link BatoruConfig} 객체를 관리하는 클래스입니다.
 */
public enum BatoruConfigManager {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(BatoruConfigManager.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private BatoruConfig config = new BatoruConfig();

    /**
     * 현재 설정을 반환합니다.
     *
     * @return 현재 설정
     */
    public BatoruConfig getConfig() {
        return config;
    }

    /**
     * 설정 파일을 불러옵니다. 설정 파일이 존재하지 않을 경우 새로 생성합니다.
     */
    public void loadConfig() {
        var configDir = FabricLoader.getInstance().getConfigDir().resolve("batoru").toFile();
        if (!configDir.exists()) {
            if (!configDir.mkdir()) {
                LOGGER.warn("Could not create configuration directory: {}", configDir.getAbsolutePath());
            }
        }

        var configFile = new File(configDir, "config.json");
        if (configFile.exists()) {
            try (var reader = new FileReader(configFile)) {
                config = BatoruConfig.CODEC
                        .decode(JsonOps.INSTANCE, GSON.fromJson(reader, JsonElement.class))
                        .getOrThrow()
                        .getFirst();
            } catch (IOException exception) {
                LOGGER.warn("Could not read json file '{}'", configFile.getAbsolutePath(), exception);
            }
        } else {
            try (var writer = new FileWriter(configFile)) {
                GSON.toJson(BatoruConfig.CODEC.encodeStart(JsonOps.INSTANCE, config).getOrThrow(), writer);
            } catch (IOException exception) {
                LOGGER.warn("Could not store json file '{}'", configFile.getAbsolutePath(), exception);
            }
        }
    }
}
