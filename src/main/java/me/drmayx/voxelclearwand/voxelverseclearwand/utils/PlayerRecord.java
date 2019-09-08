package me.drmayx.voxelclearwand.voxelverseclearwand.utils;

import java.util.Date;
import java.util.UUID;

public class PlayerRecord {

    public UUID playerID;
    public Date timestamp;

    public PlayerRecord(UUID id, Date date){
        this.playerID = id;
        this.timestamp = date;
    }
}
