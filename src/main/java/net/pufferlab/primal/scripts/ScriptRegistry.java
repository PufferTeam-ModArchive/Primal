package net.pufferlab.primal.scripts;

public class ScriptRegistry {

    public static final ScriptRemove scriptRemove = new ScriptRemove();
    public static final ScriptPrimitive scriptPrimitive = new ScriptPrimitive();
    public static final ScriptOreProcessing scriptOreProcessing = new ScriptOreProcessing();
    public static final ScriptWorld scriptWorld = new ScriptWorld();
    public static final ScriptInternal scriptInternal = new ScriptInternal();

    public void run() {
        scriptRemove.run();

        scriptWorld.run();
        scriptInternal.run();

        scriptPrimitive.run();

        scriptOreProcessing.run();
    }
}
