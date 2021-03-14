package com.alysonsantos.aspect.manager;

import com.alysonsantos.aspect.dao.EnxadaDAO;
import com.alysonsantos.aspect.model.Enxada;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import me.lucko.helper.Schedulers;
import me.lucko.helper.scheduler.HelperExecutors;

import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

import static me.lucko.helper.Schedulers.async;

@Singleton
@Getter
public class EnxadaManager {

    private final Map<String, Enxada> cache = new WeakHashMap<>();

    @Inject
    private EnxadaDAO enxadaDAO;
    private Random random;

    public void init() {
        random = new Random();
        enxadaDAO.createTable();
    }

    public Enxada getByPlayer(String player) {

        Enxada enxada = cache.getOrDefault(player, null);
        if (enxada == null) {
            enxada = enxadaDAO.selectOne(player);
        }

        return enxada;
    }

    public void createOne(Enxada enxada) {
        runAsync(() -> {
            cache.put(enxada.getOwner(), enxada);
            enxadaDAO.insertOne(enxada);
        });
    }

    public void runAsync(Runnable runnable) {
        async().run(runnable);
    }

//    enxada = Enxada.builder()
//            .enxadaEnchantments(EnxadaEnchantments.builder()
//                                .rewarding(0)
//                                .herbalism(0)
//                                .wealth(0)
//                                .build())
//            .brokenPlantions(0)
//                        .owner(player)
//                        .level(1)
//                        .id(random.nextInt(10000))
//            .build();
//
//                enxadaDAO.insertOne(enxada);

}
