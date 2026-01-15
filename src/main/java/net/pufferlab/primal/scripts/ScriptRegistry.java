package net.pufferlab.primal.scripts;

public class ScriptRegistry {

    public static final ScriptRemove scriptRemove = new ScriptRemove();
    public static final ScriptPrimitive scriptPrimitive = new ScriptPrimitive();
    public static final ScriptOreProcessing scriptOreProcessing = new ScriptOreProcessing();
    public static final ScriptLang scriptLang = new ScriptLang();

    public void run() {
        scriptRemove.run();

        scriptPrimitive.run();

        scriptLang.run();

        scriptOreProcessing.run();
    }
}
