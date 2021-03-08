package com.alysonsantos.aspect.configuration;

import com.alysonsantos.aspect.AspectEnxada;
import com.alysonsantos.aspect.configuration.value.EnchantConfig;
import com.alysonsantos.aspect.configuration.value.EnchantValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigValue {

    private static final ConfigValue instance = new ConfigValue();

    private final Configuration configuration = AspectEnxada.getInstace().getConfig();

    private final ConfigurationSection enchantmentWealthConfig = configuration.getConfigurationSection("Fortuna");

    private final ConfigurationSection enchantmentRewardingConfig = configuration.getConfigurationSection("Recompensador");

    private final ConfigurationSection enchantmentHerbalismConfig = configuration.getConfigurationSection("Herbalismo");

    private final EnchantConfig enchantmentWealthValue = EnchantValue.builder()
            .maxLevel(enchantmentWealthConfig.getInt("Nivel-Maximo"))
            .priceLevel(enchantmentWealthConfig.getDouble("Preço-Nivel"))
            .startedPrice(enchantmentHerbalismConfig.getDouble("Preço-Inicial"))
            .build();

    private final EnchantConfig enchantmentRewardingValue = EnchantValue.builder()
            .maxLevel(enchantmentRewardingConfig.getInt("Nivel-Maximo"))
            .priceLevel(enchantmentRewardingConfig.getDouble("Preço-Nivel"))
            .startedPrice(enchantmentRewardingConfig.getDouble("Preço-Inicial"))
            .build();

    private final EnchantConfig enchantmentHerbalismValue = EnchantValue.builder()
            .maxLevel(enchantmentHerbalismConfig.getInt("Nivel-Maximo"))
            .priceLevel(enchantmentHerbalismConfig.getDouble("Preço-Nivel"))
            .startedPrice(enchantmentHerbalismConfig.getDouble("Preço-Inicial"))
            .build();

    private double priceToButIt = configuration.getDouble("Preço");

    public static <T> T get(Function<ConfigValue, T> supplier) {
        return supplier.apply(ConfigValue.instance);
    }

}
