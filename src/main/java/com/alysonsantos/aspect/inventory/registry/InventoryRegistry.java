package com.alysonsantos.aspect.inventory.registry;

import com.alysonsantos.aspect.inventory.BuyEnxadaInventory;
import com.alysonsantos.aspect.inventory.EnxadaInventory;
import com.alysonsantos.aspect.inventory.RankingInventory;
import com.google.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public final class InventoryRegistry {

    private EnxadaInventory enxadaInventory;
    private RankingInventory rankingInventory;
    private BuyEnxadaInventory buyEnxadaInventory;

    public void register() {
        this.enxadaInventory = new EnxadaInventory().init();
        this.rankingInventory = new RankingInventory().init();
        this.buyEnxadaInventory = new BuyEnxadaInventory().init();
    }

}
