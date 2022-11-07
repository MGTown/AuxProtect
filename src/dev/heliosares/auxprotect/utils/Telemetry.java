package dev.heliosares.auxprotect.utils;

import dev.heliosares.auxprotect.spigot.AuxProtectSpigot;
import dev.heliosares.auxprotect.spigot.Metrics;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

public class Telemetry {
    private static final HashMap<String, Boolean> hooks = new HashMap<>();
    private final Metrics metrics;

    public Telemetry(AuxProtectSpigot plugin, int pluginId) {
        metrics = new Metrics(plugin, pluginId);

        metrics.addCustomChart(new Metrics.SingleLineChart("entries", new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int count = plugin.getSqlManager().getCount();
                return count;
            }
        }));

        metrics.addCustomChart(new Metrics.SimplePie("private", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return plugin.getAPConfig().isPrivate() ? "Private" : "Public";
            }
        }));

        metrics.addCustomChart(new Metrics.SimplePie("mysql", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return plugin.getSqlManager().isMySQL() ? "MySQL" : "SQLite";
            }
        }));

        metrics.addCustomChart(new Metrics.SimplePie("db-version", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return plugin.getSqlManager().getVersion() + "";
            }
        }));

        metrics.addCustomChart(new Metrics.SimplePie("db-original-version", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return plugin.getSqlManager().getOriginalVersion() + "";
            }
        }));

        metrics.addCustomChart(new Metrics.SimplePie("updatechecker", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return plugin.getAPConfig().shouldCheckForUpdates() ? "Enabled" : "Disabled";
            }
        }));

        for (Entry<String, Boolean> entry : hooks.entrySet()) {
            metrics.addCustomChart(new Metrics.SimplePie("hook-" + entry.getKey(), new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return entry.getValue() ? "Enabled" : "Disabled";
                }
            }));
        }

//		metrics.addCustomChart(new Metrics.SimplePie("entriespersecond", new Callable<String>() {
//			@Override
//			public String call() throws Exception {
//				return entry.getValue() ? "Enabled" : "Disabled";
//			}
//		}));
    }

    public static void reportHook(AuxProtectSpigot plugin, String name, boolean state) {
        if (state) {
            plugin.info(name + " hooked");
        } else {
            plugin.debug(name + " not hooked");
        }
        hooks.put(name.toLowerCase(), state);
    }
}
