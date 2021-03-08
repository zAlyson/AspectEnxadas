package com.alysonsantos.aspect.guice;

import com.alysonsantos.aspect.AspectEnxada;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.logging.Logger;

@EqualsAndHashCode(callSuper = false)
@Data(staticConstructor = "of")
public class PluginModule extends AbstractModule {

    private final AspectEnxada aspectEnxada;

    @Override
    protected void configure() {
        bind(AspectEnxada.class)
                .toInstance(aspectEnxada);
        bind(Logger.class)
                .annotatedWith(Names.named("main"))
                .toInstance(aspectEnxada.getLog());
        bind(SQLExecutor.class)
                .toInstance(new SQLExecutor(aspectEnxada.getSqlConnector()));
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }
}
