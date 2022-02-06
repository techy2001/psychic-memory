package main.java.mialee.psychicmemory;

import main.java.mialee.psychicmemory.data.PMData;
import main.java.mialee.psychicmemory.data.PMSave;
import main.java.mialee.psychicmemory.data.PMSettings;

import java.util.LinkedHashMap;

public class PMGame {
    public PMSettings SETTING_VALUES;
    public PMSave SAVE_VALUES;

    protected PMGame() {
        SETTING_VALUES = PMData.populateSettings();
        SAVE_VALUES = PMData.populateSave(1);
    }
}
