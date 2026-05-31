package net.pufferlab.primal.mixins.early.minecraft.client;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.ResourcePackRepository;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.IOUtils;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ResourcePackRepository.class)
public class MixinResourcePackRepository {

    @Shadow
    @Final
    private File dirResourcepacks;
    @Shadow
    @Final
    protected static FileFilter resourcePackFilter;

    @Inject(method = "getResourcePackFiles", at = @At("RETURN"), cancellable = true)
    private void getResourcePackFiles$primal(CallbackInfoReturnable<List<File>> cir) {

        List<File> list = new ArrayList<>(cir.getReturnValue());

        File temp = IOUtils.createResourceStreamFile("/" + Primal.textureFile + ".zip", Primal.textureFile, "tmp");

        if (temp != null) {
            list.add(temp);
        }

        cir.setReturnValue(list);
    }
}
