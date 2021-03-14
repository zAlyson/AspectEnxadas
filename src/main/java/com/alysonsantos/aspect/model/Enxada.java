package com.alysonsantos.aspect.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"owner"})
@Builder
@Data
public class Enxada {

    private String owner;

    private int level;

    private boolean storaged;

    private double brokenPlantions;

    private EnxadaEnchantments enxadaEnchantments;


}
