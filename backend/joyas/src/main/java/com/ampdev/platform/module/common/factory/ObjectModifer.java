package com.ampdev.platform.module.common.factory;

import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.common.dataobject.PersistedDataObject;

public class ObjectModifer {

    public static <T extends PersistedDataObject> T getModifiedObjectCreate(Module module, T t) {
        switch (module) {


            default:
                break;
        }
        return t;
    }

    public static <T extends PersistedDataObject> T getModifiedObjectUpdate(Module module, T t) {
        switch (module) {

            default:
                break;
        }
        return t;
    }
}
