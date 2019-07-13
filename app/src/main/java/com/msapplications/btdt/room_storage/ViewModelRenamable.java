package com.msapplications.btdt.room_storage;

import com.msapplications.btdt.interfaces.Renamable;

public interface ViewModelRenamable {

    public void rename(Renamable renamable);
    public int nameExists(String name);
}
