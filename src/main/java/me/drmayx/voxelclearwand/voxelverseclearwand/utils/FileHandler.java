package me.drmayx.voxelclearwand.voxelverseclearwand.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FileHandler {

    private static final String PLUGIN_DATA_DIR_NAME = "plugins/VoxelVerseClearWand";
    private static final String DATABASE_FILE_NAME = "database.json";
    private static final String CONFIG_FILE_PATH = "config.yml";
    public static final String CONFIG_PATH = PLUGIN_DATA_DIR_NAME + "/" + CONFIG_FILE_PATH;
    private static File database = null;
    private static Gson gson;


    public static void Init() throws IOException{

        database = new File(PLUGIN_DATA_DIR_NAME + "/" + DATABASE_FILE_NAME);
        File dir = new File(PLUGIN_DATA_DIR_NAME);
        if(!dir.exists()){
            dir.mkdirs();
        }

        if(!database.exists()){

            database.createNewFile();
        }
        gson = new Gson();

        try{
            readAllFromDatabase(Bukkit.getConsoleSender());
        }catch(JsonSyntaxException e){
            Bukkit.getConsoleSender().sendMessage("Database badly formatted. There might have been an issue when " +
                    "transferring the file over different file systems. Please remove the database.json file and restart the plugin.\n" +
                    "Thanks, DrMayX");
        }

    }

    public static PlayerRecord readFromDatabase(Player player){
        PlayerRecord[] playersData;
        try {
            playersData = readAllFromDatabase(player);
        }catch (NullPointerException e){
            player.sendMessage("An error occurred processing your request. Contact admin and provide them with that error code : " +
                    (new Random().nextLong()) +
                    "PRNull");
            System.out.println("Some issue with reading from db. Contact DrMayX");
            return null;
        }

        if(playersData == null  || playersData.length < 1){
            return null;
        }

        for(PlayerRecord playerRecord : playersData){

            if(playerRecord == null){
                continue;
            }

            if(playerRecord.playerID.equals(player.getUniqueId())){
                return playerRecord;
            }
        }

        return null;
    }

    public static boolean saveToDatabase(PlayerRecord playerRecord, Player player){
        if(database == null){
            player.sendMessage("An error occurred processing your request. Contact admin and provide them with that error code : " +
                    (new Random().nextLong()) +
                    "DBNull");
            System.out.println("Some issue with saving. Contact DrMayX");
            return false;
        }
        String databaseData = "";
        ArrayList<PlayerRecord> playersData;
        try {
            playersData =  new ArrayList<>(Arrays.asList(readAllFromDatabase(player)));
        }catch (NullPointerException e){
            player.sendMessage("An error occurred processing your request. Contact admin and provide them with that error code : " +
                    (new Random().nextLong()) +
                    "SDNull");
            System.out.println(e.getMessage());
            System.out.println("Some issue with saving. Contact DrMayX");
            return false;
        }

        PlayerRecord plr = null;
        if(playersData == null){
            player.sendMessage("no records in db. recreating");
            playersData = new ArrayList<>();
        }else{
            for(PlayerRecord record : playersData){
                if(record == null){
                    continue;
                }
                else if(record.playerID.equals(player.getUniqueId())){
                    plr = record;
                    record.timestamp = playerRecord.timestamp;  // Update timestamp.
                    break;
                }
            }
        }

        if(plr == null){
            playersData.add(playerRecord);
        }

        databaseData = gson.toJson(playersData);

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(database));
        } catch (IOException e) {
            player.sendMessage("An error occurred processing your request. Contact admin and provide them with that error code : " +
                    (new Random().nextLong()) +
                    "BWErr");
            System.out.println(e.getMessage());
            System.out.println("Some issue with saving. Contact DrMayX");
            return false;
        }

        if(writer == null){
            player.sendMessage("An error occurred processing your request. Contact admin and provide them with that error code : " +
                    (new Random().nextLong()) +
                    "BWNull");
            System.out.println("Some issue with saving. Contact DrMayX");
            return false;
        }

        try {
            writer.write(databaseData);
        } catch (IOException e) {
            player.sendMessage("An error occurred processing your request. Contact admin and provide them with that error code : " +
                    (new Random().nextLong()) +
                    "WRNull");
            System.out.println("Some issue with saving. Contact DrMayX");
            return false;
        }

        try {
            writer.flush();
            writer.close();
        }catch (IOException e){
            player.sendMessage("An error occurred processing your request. Contact admin and provide them with that error code : " +
                    (new Random().nextLong()) +
                    "FCErr");
            System.out.println("Some issue with saving. Contact DrMayX");
            return false;
        }
        return true;
    }

    private static PlayerRecord[] readAllFromDatabase(CommandSender playerToError){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(database));

        } catch (FileNotFoundException e) {
            playerToError.sendMessage("An error occurred processing your request. Contact admin and provide them with that error code : " +
                    (new Random().nextLong()) +
                    "BRErr");
            System.out.println(e.getMessage());
            System.out.println("Some issue with reading from db. Contact DrMayX");
            return null;
        }

        if(reader == null){
            playerToError.sendMessage("An error occurred processing your request. Contact admin and provide them with that error code : " +
                    (new Random().nextLong()) +
                    "BRNull");
            System.out.println("Some issue with reading from db. Contact DrMayX");
            return null;
        }

        String databaseData = "";
        String line;
        try{
            while((line = reader.readLine() )!= null){
                databaseData += line;
            }
            reader.close();
        }catch(IOException e){
            playerToError.sendMessage("An error occurred processing your request. Contact admin and provide them with that error code : " +
                    (new Random().nextLong()) +
                    "LRErr");
            System.out.println(e.getMessage());
            System.out.println("Some issue with reading from db. Contact DrMayX");
        }
        PlayerRecord[] d = gson.fromJson(databaseData, PlayerRecord[].class);

        if(d==null){
            d = new PlayerRecord[0];
        }
        return d;
    }
}
