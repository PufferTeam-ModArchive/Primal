package net.pufferlab.primal.utils;

import net.pufferlab.primal.Primal;

import gnu.trove.map.TObjectLongMap;
import gnu.trove.map.hash.TObjectLongHashMap;

public class Profiler {

    public final TObjectLongMap<String> map = new TObjectLongHashMap<>();
    public double timeTaken = 0;

    public void startProfile(String name) {
        map.put(name, System.nanoTime());
    }

    public void endProfile(String name) {
        long currentTime = System.nanoTime();
        long time = map.remove(name);
        double timePassed = (currentTime - time) / 1_000_000_000.0;
        timeTaken += timePassed;
        Primal.LOG.info("[Profiler] {} took {} seconds to execute", name, timePassed);
    }

    public void profileTotal() {
        Primal.LOG.info("[Profiler] Total took {} seconds to execute", timeTaken);
        timeTaken = 0;
    }
}
