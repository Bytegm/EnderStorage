package codechicken.enderstorage.storage;

import codechicken.enderstorage.api.AbstractEnderStorage;
import codechicken.enderstorage.api.Frequency;
import codechicken.enderstorage.manager.EnderStorageManager;
import codechicken.lib.fluid.FluidUtils;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class EnderLiquidStorage extends AbstractEnderStorage implements IFluidHandler, IFluidTank {

    public static final EnderStorageManager.StorageType<EnderLiquidStorage> TYPE = new EnderStorageManager.StorageType<>("liquid");

    public static final int CAPACITY = 16 * FluidUtils.B;

    private class Tank extends FluidTank {

        public Tank(int capacity) {
            super(capacity);
        }

        @Override
        protected void onContentsChanged() {
            setDirty();
        }
    }

    private Tank tank;

    public EnderLiquidStorage(EnderStorageManager manager, Frequency freq) {
        super(manager, freq);
        tank = new Tank(CAPACITY);
    }

    @Override
    public void clearStorage() {
        tank = new Tank(CAPACITY);
        setDirty();
    }

    @Override
    public void loadFromTag(CompoundNBT tag) {
        tank.readFromNBT(tag.getCompound("tank"));
    }

    @Override
    public String type() {
        return "liquid";
    }

    public CompoundNBT saveToTag() {
        CompoundNBT compound = new CompoundNBT();
        compound.put("tank", tank.writeToNBT(new CompoundNBT()));

        return compound;
    }

    //@formatter:off
    @Override public FluidStack getFluid() { return tank.getFluid(); }
    @Override public int getFluidAmount() { return tank.getFluidAmount(); }
    @Override public int getCapacity() { return tank.getCapacity(); }
    @Override public boolean isFluidValid(FluidStack stack) { return tank.isFluidValid(stack); }
    @Override public int getTanks() { return tank.getTanks(); }
    @Override public FluidStack getFluidInTank(int tankId) { return tank.getFluidInTank(tankId); }
    @Override public int getTankCapacity(int tankId) { return tank.getTankCapacity(tankId); }
    @Override public boolean isFluidValid(int tankId, FluidStack stack) { return tank.isFluidValid(tankId, stack); }
    @Override public int fill(FluidStack resource, FluidAction action) { return tank.fill(resource, action); }
    @Override public FluidStack drain(FluidStack resource, FluidAction action) { return tank.drain(resource, action); }
    @Override public FluidStack drain(int maxDrain, FluidAction action) { return tank.drain(maxDrain, action); }
    //@formatter:on
}
