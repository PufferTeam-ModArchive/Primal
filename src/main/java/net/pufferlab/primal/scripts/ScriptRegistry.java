package net.pufferlab.primal.scripts;

public class ScriptRegistry {

    public static final ScriptRemove scriptRemove = new ScriptRemove();
    public static final ScriptPrimitive scriptPrimitive = new ScriptPrimitive();
    public static final ScriptOreProcessing scriptOreProcessing = new ScriptOreProcessing();
    public static final ScriptWorld scriptWorld = new ScriptWorld();
    public static final ScriptInternal scriptInternal = new ScriptInternal();

    public void runEarly() {
        scriptRemove.runEarly();

        scriptInternal.runEarly();

        scriptPrimitive.runEarly();
        scriptWorld.runEarly();
        scriptOreProcessing.runEarly();
    }

    public void run() {
        scriptInternal.run();

        scriptPrimitive.run();
        scriptWorld.run();
        scriptOreProcessing.run();
    }
}
