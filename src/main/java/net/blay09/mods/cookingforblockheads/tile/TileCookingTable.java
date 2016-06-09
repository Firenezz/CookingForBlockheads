package net.blay09.mods.cookingforblockheads.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TileCookingTable extends TileEntity {

    private ItemStack noFilterBook;

    public boolean hasNoFilterBook() {
        return noFilterBook != null;
    }

    @Nullable
    public ItemStack getNoFilterBook() {
        return noFilterBook;
    }

    public void setNoFilterBook(ItemStack noFilterBook) {
        this.noFilterBook = noFilterBook;
        markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        NBTTagCompound itemCompound = new NBTTagCompound();
        if(noFilterBook != null) {
            noFilterBook.writeToNBT(itemCompound);
        }
        tagCompound.setTag("NoFilterBook", itemCompound);
        return tagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        if(tagCompound.hasKey("NoFilterBook")) {
            setNoFilterBook(ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("NoFilterBook")));
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

}