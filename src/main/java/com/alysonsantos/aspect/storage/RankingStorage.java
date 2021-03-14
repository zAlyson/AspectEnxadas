package com.alysonsantos.aspect.storage;

import com.alysonsantos.aspect.model.Enxada;
import com.alysonsantos.aspect.models.Account;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import lombok.Data;

import java.util.List;

@Data
@Singleton
public class RankingStorage {

    private final List<Enxada> rankingEnxadas = Lists.newLinkedList();

    private final List<Account> rankingAccounts = Lists.newLinkedList();

    private long nextUpdate;

}
