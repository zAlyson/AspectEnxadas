package com.alysonsantos.aspect.manager;

import com.alysonsantos.aspect.dao.EnxadaDAO;
import com.alysonsantos.aspect.model.Enxada;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;

import java.util.Map;
import java.util.WeakHashMap;

@Singleton
@Getter
public class EnxadaManager {

    @Inject
    private EnxadaDAO enxadaDAO;

    private final Map<String, Enxada> CACHE = new WeakHashMap<>();



}
