package com.alysonsantos.aspect.configuration.value;

import lombok.Builder;

@Builder
public final class EnchantValue implements EnchantConfig {

    private final double startedPrice;

    private final double priceLevel;

    private final int maxLevel;

    @Override
    public double getStartedPrice() {
        return this.startedPrice;
    }

    @Override
    public double priceLevel() {
        return this.priceLevel;
    }

    @Override
    public int maxLevel() {
        return this.maxLevel;
    }

}
