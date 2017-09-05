package cn.mcsugar.pe.group;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.level.Level;
import cn.nukkit.level.generator.Flat;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//import money.MoneySLand;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import cn.mcsugar.pe.we.WeCmd;

/**
 * Created by endymx on 2017/7/22.
 */
public class Main extends PluginBase implements Listener {
	private static Main instance;
    private Config group;
    private LinkedHashMap<Player, Boolean> status = new LinkedHashMap<Player, Boolean>();
    
    public static Main getInstance(){
    	return Main.instance;
    }
    
    public void onEnable() {
        getLogger().info("开始测试");
        this.getServer().getPluginManager().registerEvents(this, this);
        if (new File(this.getDataFolder(), "/").mkdir()) {
            Server.getInstance().disablePlugins();
        }
        //getJson();
        group = new Config(new File(this.getDataFolder(), "groups.yml"), Config.YAML);
        
        Main.instance=this;
this.getServer().getCommandMap().register("we",new WeCmd(this));
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onLogin(PlayerPreLoginEvent event) {
        if (!isGroup(event.getPlayer())) {
            event.setCancelled();
            event.setKickMessage(TextFormat.GREEN + "新人快跑！这个服务器全是基佬！！\n" + TextFormat.YELLOW + "                           快跑啊！！！\n" + TextFormat.RED + "                               ...快...跑...");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        String levelName = "m" + String.valueOf(group.getSection(getGroup(event.getPlayer())).getInt("landId"));
        Level level = Server.getInstance().getLevelByName(levelName);
        if (level == null) {
            Server.getInstance().generateLevel(levelName, new Random().nextLong(), Flat.class);
//level=Server.getInstance().getLevelByName(levelName);
        }
//event.getPlayer().teleport(level.getSpawnLocation());

        status.put(event.getPlayer(),new Boolean(false));
        event.getPlayer().sendMessage(TextFormat.GREEN + "欢迎来到CMCSのPE决赛服务器！\n" + TextFormat.GOLD + "请直接输入在网站上注册时的密码：");
        event.setJoinMessage(TextFormat.YELLOW + "[" + getGroup(event.getPlayer()) + "]" + event.getPlayer().getName() + "气昂昂的进来了");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(PlayerChatEvent event) throws IOException {
        if ((new Boolean(false)).equals(status.get(event.getPlayer()))) {
            event.setCancelled(true);
            if (md5(event.getMessage(), group.getSection(getGroup(event.getPlayer())).getString(event.getPlayer().getName().toLowerCase())).trim().equals("1")) {
                status.put(event.getPlayer(),new Boolean(true));
                event.getPlayer().sendMessage(TextFormat.GREEN + "登录成功！");
                event.getPlayer().setGamemode(1);
                //event.getPlayer().addAttachment(this, "money.permission.sland.modify.place." + group.getSection(getGroup(event.getPlayer())).get("landId"), true);
                //event.getPlayer().addAttachment(this, "money.permission.sland.modify.break." + group.getSection(getGroup(event.getPlayer())).get("landId"), true);
                //event.getPlayer().addAttachment(this, "money.permission.sland.modify.interact." + group.getSection(getGroup(event.getPlayer())).get("landId"), true);
String levelName = "m" + String.valueOf(group.getSection(getGroup(event.getPlayer())).getInt("landId"));
        Level level = Server.getInstance().getLevelByName(levelName);
        if (level == null) {
            Server.getInstance().generateLevel(levelName, new Random().nextLong(), Flat.class);
            level = Server.getInstance().getLevelByName(levelName);
        }
event.getPlayer().teleport(level.getSpawnLocation());
return;
            }
            event.getPlayer().sendMessage(TextFormat.RED + "密码错误！");
        } else {
            event.setFormat(TextFormat.GOLD + "[PE决赛组]" + TextFormat.WHITE + "<" + TextFormat.GREEN + "[" + getGroup(event.getPlayer()) + "] <" + TextFormat.WHITE + event.getPlayer().getName() + "> " + event.getMessage());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockChange(BlockUpdateEvent event) {
        if (event.getBlock().getId() == Block.FIRE) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        if ((new Boolean(false)).equals(status.get(event.getPlayer()))) {
            event.getPlayer().setGamemode(0);
        }
        event.setQuitMessage(TextFormat.GREEN + "[" + getGroup(event.getPlayer()) + "]" + event.getPlayer().getName() + "灰溜溜的跑路了");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if ((new Boolean(false)).equals(status.get(event.getPlayer()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        if ((new Boolean(false)).equals(status.get(event.getPlayer()))) {
            event.setCancelled(true);
        } else {
            if (event.getBlock().y == 1) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        if ((new Boolean(false)).equals(status.get(event.getPlayer()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        if ((new Boolean(false)).equals(status.get(event.getPlayer()))) {
            event.setCancelled(true);
            event.getPlayer().sendPopup(TextFormat.GOLD + "请直接输入在网站上注册时的密码");
        } else {
            event.getPlayer().sendPopup(TextFormat.LIGHT_PURPLE + "x: " + event.getPlayer().getFloorX() + " y: " + event.getPlayer().getFloorY() + " z: " + event.getPlayer().getFloorZ());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChangeGameMode(PlayerGameModeChangeEvent event) {
        if ((new Boolean(false)).equals(status.get(event.getPlayer()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLevelChange(EntityLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (!isGroup((Player) event.getEntity())) {
            return;
        }

        String levelName = "m" + String.valueOf(group.getSection(getGroup((Player) event.getEntity())).getInt("landId"));
        Level level = Server.getInstance().getLevelByName(levelName);
        if (level == null) {
            return;
        }
        if (level != event.getTarget()) {
            event.setCancelled();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        if ((new Boolean(false)).equals(status.get(event.getPlayer()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent event) {
        if ((new Boolean(false)).equals(status.get(event.getPlayer()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent event) {
        if (!status.get(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCmd(PlayerCommandPreprocessEvent event) {
        if ((new Boolean(false)).equals(status.get(event.getPlayer()))) {
            event.setCancelled(true);
        }else{
			this.getServer().getLogger().info("["+event.getPlayer().getName()+"]"+event.getMessage());
		}
    }

    private void getJson() {
        File file = new File("/home/master34/pe.json");
        StringBuilder str = new StringBuilder();
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                str.append(lineTxt);
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonParser parse = new JsonParser();
        JsonArray js = (JsonArray) parse.parse(str.toString());
        LinkedHashMap<String, Object> groups = new LinkedHashMap<String, Object>();
        for (int i = 0; i < js.size(); i++) {
            JsonObject group = js.get(i).getAsJsonObject();
            JsonArray u = group.get("users").getAsJsonArray();
            LinkedHashMap<String, Object> users = new LinkedHashMap<String, Object>();
            for (int ii = 0; ii < u.size(); ii++) {
                JsonObject user = u.get(ii).getAsJsonObject();
                users.put(user.get("username").getAsString().toLowerCase(), user.get("password").getAsString());
                if (group.get("captainUid").getAsInt() == user.get("uid").getAsInt()) {
                    users.put("leader", user.get("username").getAsString());
                }
            }
            users.put("landId", i);
            groups.put(group.get("teamName").getAsString(), users);
            users = new LinkedHashMap<String, Object>();
        }
        Config c = new Config(new File(this.getDataFolder(), "groups.yml"), Config.YAML);
        c.setAll(groups);
        c.save();
    }

    private boolean isGroup(Player player) {
        LinkedHashMap<String, Object> groups = (LinkedHashMap<String, Object>) group.getAll();
        for (Object o : groups.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            for (Object p : ((LinkedHashMap<String, Object>) entry.getValue()).entrySet()) {
                Map.Entry e = (Map.Entry) p;
                String user = (String) e.getKey();
                if (player.getName().toLowerCase().equals(user.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getGroup(Player player) {
        LinkedHashMap<String, Object> groups = (LinkedHashMap<String, Object>) group.getAll();
        for (Object o : groups.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            for (Object p : ((LinkedHashMap<String, Object>) entry.getValue()).entrySet()) {
                Map.Entry e = (Map.Entry) p;
                String user = (String) e.getKey();
                if (player.getName().toLowerCase().equals(user.toLowerCase())) {
                    return (String) entry.getKey();
                }
            }
        }
        return null;
    }

    private String md5(String pw, String md5) throws IOException {
        URL url = new URL("http://47.90.124.34/md5.php?pw=" + pw + "&md5=" + md5);
        URLConnection urlConnection = url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8")); // 获取输入流
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ("group".equals(command.getName().toLowerCase())&&args.length>0) {
            switch (args[0]) {
            /*
                case "tp":
                    ((Player) sender).teleport(MoneySLand.getInstance().getLand((int) group.getSection(getGroup((Player) sender)).get("landId")).getShopBlock());
                    sender.sendMessage(TextFormat.YELLOW + "传送到团队建筑点");
                    break;
             */
                case "gm":
                    ((Player) sender).setGamemode(0);
                    ((Player) sender).setGamemode(1);
                    sender.sendMessage(TextFormat.YELLOW + "你的游戏模式已被更改");
                    break;
                case "reload":
                    if (!sender.isOp()) {
                        sender.sendMessage(TextFormat.RED + "权限不足");
                        return true;
                    }
                    group = new Config(new File(this.getDataFolder(), "groups.yml"), Config.YAML);
                    sender.sendMessage(TextFormat.GREEN + "成功重载团队文件");
                    break;
                default:
                    return false;
            }
        }
        return true;
    }
	
	public byte getWorkLevel(Player p){
		return (byte) (this.group.getSection(this.getGroup(p)).getInt("landId")&0x7f);
	}
	
}