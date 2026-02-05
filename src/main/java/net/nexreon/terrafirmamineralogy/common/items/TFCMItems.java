package net.nexreon.terrafirmamineralogy.common.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.nexreon.terrafirmamineralogy.TFCM;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;

/*
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
 */
import net.dries007.tfc.util.registry.RegistryHolder;

public final class TFCMItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, TFCM.MODID);

    // Items

    public static final ItemId LODESTONE = register("lodestone");


    // These are the original methods from the TFC, we're using them to register items:
    private static ItemId register(String name)
    {
        return register(name, () -> new Item(new Properties()));
    }

    private static ItemId register(String name, Properties properties)
    {
        return new ItemId(ITEMS.register(name.toLowerCase(Locale.ROOT), () -> new Item(properties)));
    }

    private static ItemId register(String name, Supplier<Item> item)
    {
        return new ItemId(ITEMS.register(name.toLowerCase(Locale.ROOT), item));
    }

    public record ItemId(DeferredHolder<Item, Item> holder) implements RegistryHolder<Item, Item>, ItemLike
    {
        @Override
        public Item asItem()
        {
            return get();
        }
    }
}

