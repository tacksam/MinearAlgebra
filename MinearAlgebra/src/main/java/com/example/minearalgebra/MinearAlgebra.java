package com.example.minearalgebra;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.World;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;



public class MinearAlgebra extends JavaPlugin {

    private static final Logger LOGGER = Logger.getLogger("minearalgebra");


    private World world;
    private Material vectorMaterial;
    private Material gridMaterial;
    private Material pointMaterial;
    private HashMap<String, Room> roomRegistry = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("MinearAlgebra plugin has been enabled!");
        vectorMaterial = Material.BLUE_WOOL;
        gridMaterial = Material.BLACK_WOOL;
        pointMaterial = Material.DIAMOND_BLOCK;
        world = getServer().getWorlds().get(0); 

    }

    @Override
    public void onDisable() {
        // Code to run when the plugin is disabled
        getLogger().info("MinearAlgebra plugin has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(command.getName().equalsIgnoreCase("createroom")){
            if (sender instanceof Player){
                Player player = (Player) sender;
                
                if(args.length != 4){
                    player.sendMessage("Usage: /createroom <room name> <width> <height> <depth>");
                    return true;
                }
                try{
                    String roomName = args[0];
                    double x = Double.parseDouble(args[1]);
                    double y = Double.parseDouble(args[2]);
                    double z = Double.parseDouble(args[3]);

                    createRoom(roomName,x,y,z);
                    player.sendMessage("Created a room with name: " + roomName);

                } catch(NumberFormatException invalidRoomEntry){
                    player.sendMessage("Invalid entry");
                } 
        }

        if(command.getName().equalsIgnoreCase("createvector")){
            if(sender instanceof Player){
                Player player = (Player) sender;

                if(args.length != 5){
                    player.sendMessage("Usage: /createvector <room name> <vector name> <x> <y> <z>");
                    return true;
                }
                try{
                    String roomName = args[0];
                    String vectorName = args[1];
                    double x = Double.parseDouble(args[2]);
                    double y = Double.parseDouble(args[3]);
                    double z = Double.parseDouble(args[4]);
                    addVectorToRoom(roomName, vectorName, 0, 0, 0);


                } catch (NumberFormatException invalidVectorEntry){
                    player.sendMessage("Invalid coordinates");
                }
                return true;
            } else{
                sender.sendMessage("This command can only be ran by player");
                return true;
            }
        }

        return false;
    }

    public boolean valid(){
        if(world == null) {
            LOGGER.info("World is not initialized!"); 
            return false;
        }
        if(vectorMaterial == null || gridMaterial == null || pointMaterial == null) {
            LOGGER.info("Materials are not intitialized!");
            return false;
        }
        LOGGER.info("World and materials are valid");
        return true;
    }
    

    public void drawRoom(Room room){
        if(valid()){

            drawVector(room.getWidth(), gridMaterial);
            drawVector(room.getHeight(), gridMaterial);
            drawVector(room.getDepth(), gridMaterial);

            
            for (Map.Entry<String, Vector> entry : room.getVectorMap().entrySet()) {
                String key = entry.getKey();
                drawVector(room.getVectorMap().get(key));
            }
        }

    }

    public void drawVector(Vector vector, Material material) {
        if (!valid()) {
            return;
        }
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();
    
        int steps = (int) Math.ceil(Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.abs(z)));
        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            int blockX = (int) Math.round(t * x);
            int blockY = (int) Math.round(t * y);
            int blockZ = (int) Math.round(t * z);
            placeBlock(blockX, blockY, blockZ, material);
        }
    }
    
    public void drawVector(Vector vector) {
        drawVector(vector, vectorMaterial);
    }
    

    public void drawPoint(Vector vector) {
            placeBlock(vector, vectorMaterial);
    }
    
    public void placeBlock(Vector vector, Material material){
        Location location = new Location(world, vector.getX(), vector.getY(), vector.getZ());
        world.getBlockAt(location).setType(material);     
    }
    
    public void placeBlock(int x, int y, int z, Material material){
        Location location = new Location(world, x, y, z);
        location.getBlock().setType(material);
    }

    public void clearVectors(){
    
    }

    public void createRoom(String roomName, double width, double height, double depth){
        Vector x = new Vector(width, 0, 0);
        Vector y = new Vector(0, height, 0);
        Vector z = new Vector(0, 0, depth);
        if(roomRegistry.containsKey(roomName)){
            LOGGER.info("A room with this already exists");
            return;
        }
        Room room = new Room(x, y, z);
        roomRegistry.put(roomName, room);
        LOGGER.info("Room created with name: " + roomName);
    }

    public void addVectorToRoom(String roomName, String vectorName, int x, int y, int z){
        Room room = roomRegistry.get(roomName);

        if(room == null){
            LOGGER.info("No room with this name");
            return;
        }
        if(room.getVectorMap().containsKey(vectorName)){
            LOGGER.info("A vector with this name already exists");
            return;
        }
        Vector vector = new Vector(x, y, z);
        room.addVector(vector, vectorName);
        LOGGER.info("Added vector :" + vector.toString());
    }



    
}
