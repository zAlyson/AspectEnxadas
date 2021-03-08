package com.alysonsantos.aspect.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"id", "owner"})
@Builder
@Data
public class Enxada {

    private String owner;

    private int id;

    private int level;

    private double brokenPlantions;

    private EnxadaEnchantments enxadaEnchantments;

}
