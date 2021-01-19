package com.github.genehyde.explosionbeam.init;

import com.github.genehyde.explosionbeam.ExplosionBeam;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class ModItemGroups {
    public static final ItemGroup MOD_ITEM_GROUP =
            new ModItemGroup(ExplosionBeam.MODID, () -> new ItemStack(ModItems.EXPLOSION_BEAM));

    public static class ModItemGroup extends ItemGroup {
        private final Supplier<ItemStack> iconSupplier;

        public ModItemGroup(final String name, final Supplier<ItemStack> iconSupplier) {
            super(name);
            this.iconSupplier = iconSupplier;
        }

        @Override
        public ItemStack createIcon() {
            return iconSupplier.get();
        }
    }
}
