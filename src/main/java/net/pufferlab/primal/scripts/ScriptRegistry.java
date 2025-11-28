package net.pufferlab.primal.scripts;

public class ScriptRegistry {

    public static final ScriptPrimitive scriptPrimitive = new ScriptPrimitive();
    public static final ScriptVanilla scriptVanilla = new ScriptVanilla();

    public void run() {
        scriptPrimitive.run();
        scriptVanilla.run();
    }
}
