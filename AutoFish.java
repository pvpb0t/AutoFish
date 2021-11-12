package who.pvpb0t.b0tware.impl.modules.misc;

import who.pvpb0t.b0tware.api.event.events.PacketEvent;
import who.pvpb0t.b0tware.impl.modules.Module;
import who.pvpb0t.b0tware.impl.setting.Setting;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoFish extends Module {

    public Setting<Boolean> AutoSwitch = this.register(new Setting<Boolean>("AutoSwitch", true));
    private int hotBarSlot;
    /*
    by pvpb0t
    2021-10-20
    */
    public AutoFish() {
        super("AutoFish", "Fishes for you", Category.MISC, true, false, false);


    }

    public int onUpdate(){
        this.getFishRod();
        return 0;
    }

    @Override
    public void onEnable() {
        this.hotBarSlot = -1;
        //this.getFishRod();
    }

    public void getFishRod(){
        this.hotBarSlot = -1;
            int fishrodslot;
            int n = fishrodslot = AutoFish.mc.player.getHeldItemMainhand().getItem() == Items.FISHING_ROD ? AutoFish.mc.player.inventory.currentItem : -1;
            if (fishrodslot == -1) {
                for (int l = 0; l < 9; ++l) {
                    if (AutoFish.mc.player.inventory.getStackInSlot(l).getItem() != Items.FISHING_ROD) continue;
                    fishrodslot = l;
                    this.hotBarSlot = l;
                    break;
                }
            }
            if (fishrodslot == -1) {
                return;
            }

        if (this.hotBarSlot != -1 && this.AutoSwitch.getValue().booleanValue()) {
            AutoFish.mc.player.inventory.currentItem = this.hotBarSlot;
        }
    }

    @SubscribeEvent
    public void onPacketReceived(PacketEvent.Receive event) {
        if (AutoFish.mc.player != null && (AutoFish.mc.player.getHeldItemMainhand().getItem() == Items.FISHING_ROD || AutoFish.mc.player.getHeldItemOffhand().getItem() == Items.FISHING_ROD) && event.getPacket() instanceof SPacketSoundEffect && SoundEvents.ENTITY_BOBBER_SPLASH.equals(((SPacketSoundEffect)event.getPacket()).getSound())) {
            new Thread(() -> {
                try {
                    Thread.sleep(200L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
               mc.rightClickMouse();
                try {
                    Thread.sleep(200L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mc.rightClickMouse();
            }).start();
        }
    }
}
