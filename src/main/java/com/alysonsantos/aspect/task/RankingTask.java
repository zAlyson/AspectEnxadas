package com.alysonsantos.aspect.task;

import com.alysonsantos.aspect.AspectEnxada;
import com.alysonsantos.aspect.EconomyPlugin;
import com.alysonsantos.aspect.dao.EnxadaDAO;
import com.alysonsantos.aspect.model.Enxada;
import com.alysonsantos.aspect.models.Account;
import com.alysonsantos.aspect.repository.AccountRepository;
import com.alysonsantos.aspect.storage.RankingStorage;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.val;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Singleton
public final class RankingTask implements Runnable {

    private final AccountRepository accountRepository = EconomyPlugin.getPlugin().getRepository();

    @Inject private EnxadaDAO enxadaDAO;
    @Inject private RankingStorage rankingStorage;

    @Override
    public void run() {
        val init = System.currentTimeMillis();

        List<Enxada> brokenPlantationsTop = Lists.newLinkedList(enxadaDAO.selectAll("ORDER BY brokenPlantations DESC LIMIT 10"));
        if (!brokenPlantationsTop.isEmpty()) {
            rankingStorage.getRankingEnxadas().clear();
            brokenPlantationsTop.forEach(rankingStorage.getRankingEnxadas()::add);
        }

        List<Account> tokensTop = Lists.newLinkedList(accountRepository.getAllToken());
        if (!tokensTop.isEmpty()) {
            rankingStorage.getRankingAccounts().clear();
            tokensTop.forEach(rankingStorage.getRankingAccounts()::add);
        }

        this.rankingStorage.setNextUpdate(Instant.now()
                .plusMillis(TimeUnit.MINUTES.toMillis(5))
                .toEpochMilli());

        System.out.println(this.rankingStorage.getNextUpdate());

        val end = System.currentTimeMillis();
        AspectEnxada.getInstace().getLogger().info("[" + Thread.currentThread().getName() + "] It took " + (end - init) + "ms to load the rankings.");
    }
}
