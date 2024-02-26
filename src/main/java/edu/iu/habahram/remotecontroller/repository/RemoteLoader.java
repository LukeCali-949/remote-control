package edu.iu.habahram.remotecontroller.repository;

import edu.iu.habahram.remotecontroller.model.DeviceData;
import edu.iu.habahram.remotecontroller.model.Light;
import edu.iu.habahram.remotecontroller.model.RemoteControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RemoteLoader implements IRemoteLoader {

    private HashMap<Integer, RemoteControl> remoteControls = new HashMap<>();

    @Override
    public void setup(int id, List<DeviceData> devices) {
        synchronized (this) {
            RemoteControl remoteControl = new RemoteControl(devices.size());
            for (DeviceData device : devices) {
                switch (device.type()) {
                    case "light":
                        Light light = new Light(device.location());
                        remoteControl.setCommand(device.slot(), light::on, light::off);
                        break;
                }
            }
            remoteControls.put(id, remoteControl);
        }
    }

    @Override
    public String onButtonWasPushed(int id, int slot) {

        synchronized (this) {
            return remoteControls.get(id).onButtonWasPushed(slot);
        }
    }

    @Override
    public String offButtonWasPushed(int id, int slot) {

        synchronized (this) {
            return remoteControls.get(id).offButtonWasPushed(slot);
        }
    }
}
