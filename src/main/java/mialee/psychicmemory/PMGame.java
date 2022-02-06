package mialee.psychicmemory;

import mialee.psychicmemory.data.PMData;
import mialee.psychicmemory.data.PMSave;
import mialee.psychicmemory.data.PMSettings;

public class PMGame {
    public PMSettings SETTING_VALUES;
    public PMSave SAVE_VALUES;

    protected PMGame() {
        SETTING_VALUES = PMData.populateSettings();
        SAVE_VALUES = PMData.populateSave(1);
    }
}
