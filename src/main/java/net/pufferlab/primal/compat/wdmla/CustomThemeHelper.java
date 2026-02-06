package net.pufferlab.primal.compat.wdmla;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.config.General;
import com.gtnewhorizons.wdmla.impl.format.TimeFormattingPattern;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.IconComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.VPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;

import mcp.mobius.waila.overlay.DisplayUtil;

public class CustomThemeHelper {

    public static final CustomThemeHelper INSTANCE = new CustomThemeHelper();

    public IComponent furnaceLikeProgressFail(List<ItemStack> input, List<ItemStack> output, int currentProgress,
        int maxProgress, boolean showDetails) {
        return furnaceLikeProgressFail(input, output, currentProgress, maxProgress, showDetails, null);
    }

    public IComponent furnaceLikeProgressFail(List<ItemStack> input, List<ItemStack> output, int currentProgress,
        int maxProgress, boolean showDetails, IComponent legacyProcessText) {
        if (!General.forceLegacy) {
            HPanelComponent hPanel = new HPanelComponent();
            for (ItemStack inputStack : input) {
                if (inputStack != null) {
                    hPanel.item(inputStack);
                }
            }
            float ratio = (float) currentProgress / maxProgress;
            hPanel.padding(new Padding().horizontal(2))
                .child(
                    new IconComponent(WDUIIcons.FURNACE_BG_FAIL, WDUIIcons.FURNACE_BG_FAIL.texPath)
                        .padding(new Padding())
                        .child(
                            new IconComponent(WDUIIcons.FURNACE_FAIL, WDUIIcons.FURNACE_FAIL.texPath)
                                .clip(0f, 0f, ratio, 1f)
                                .padding(new Padding())));
            for (ItemStack outputStack : output) {
                if (outputStack != null) {
                    hPanel.item(outputStack);
                }
            }
            return hPanel;
        } else {
            ITooltip vPanel = new VPanelComponent();
            if (showDetails) {
                for (ItemStack inputStack : input) {
                    if (inputStack != null) {
                        vPanel.horizontal()
                            .text(String.format("%s: ", StatCollector.translateToLocal("hud.msg.wdmla.in")))
                            .child(
                                ThemeHelper.INSTANCE.info(
                                    String.format(
                                        "%dx %s",
                                        inputStack.stackSize,
                                        DisplayUtil.itemDisplayNameShortFormatted(inputStack))));
                    }
                }
                for (ItemStack outputStack : output) {
                    if (outputStack != null) {
                        vPanel.horizontal()
                            .text(String.format("%s: ", StatCollector.translateToLocal("hud.msg.wdmla.out")))
                            .child(
                                ThemeHelper.INSTANCE.info(
                                    String.format(
                                        "%dx %s",
                                        outputStack.stackSize,
                                        DisplayUtil.itemDisplayNameShortFormatted(outputStack))));
                    }
                }
            }

            if (currentProgress != 0 && maxProgress != 0 && legacyProcessText == null) {
                legacyProcessText = ThemeHelper.INSTANCE.value(
                    StatCollector.translateToLocal("hud.msg.wdmla.progress"),
                    TimeFormattingPattern.ALWAYS_TICK.tickFormatter.apply(currentProgress) + " / "
                        + TimeFormattingPattern.ALWAYS_TICK.tickFormatter.apply(maxProgress));
            }

            if (legacyProcessText != null) {
                vPanel.child(legacyProcessText);
            }

            if (vPanel.childrenSize() != 0) {
                return vPanel;
            } else {
                return null;
            }
        }
    }
}
