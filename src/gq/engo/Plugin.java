package gq.engo;

import gq.engo.commands.*;
import gq.engo.event.onTPSUpdate;
import gq.engo.listeners.DeathMessages;
import gq.engo.listeners.Listeners;
import gq.engo.listeners.RandomSpawn;
import gq.engo.listeners.prevention.*;
import gq.engo.utils.DiscordWebhook;
import gq.engo.utils.TPS;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import gq.engo.utils.Metrics;

public class Plugin extends JavaPlugin {
    public static Plugin Instance;
    public DiscordWebhook discordWebhook = new DiscordWebhook(this, getConfig().getString("AlertSystem.WebhookURL"));

    @Override
    public void onEnable() {
        Instance = this;

        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        Metrics metrics = new Metrics(this, 14672); // setup bstats

        final double[] cached = {TPS.getRoundedTPS()};
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                try {
                    Thread.sleep(1000);
                    if (TPS.getRoundedTPS() != cached[0]) {
                        onTPSUpdate e = new onTPSUpdate(TPS.getRoundedTPS(), cached[0]);
                        Bukkit.getServer().getPluginManager().callEvent(e);
                    }
                    cached[0] = TPS.getRoundedTPS();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }, 0, 12000);


        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getPluginManager().registerEvents(new Chat(), this);
        Bukkit.getPluginManager().registerEvents(new Antillegal(), this);
        Bukkit.getPluginManager().registerEvents(new Redstone(), this);
        Bukkit.getPluginManager().registerEvents(new SandLag(), this);
        Bukkit.getPluginManager().registerEvents(new BookBan(), this);
        Bukkit.getPluginManager().registerEvents(new Elytra(), this);
        Bukkit.getPluginManager().registerEvents(new Projectiles(), this);
        Bukkit.getPluginManager().registerEvents(new WaterLag(), this);
        Bukkit.getPluginManager().registerEvents(new Withers(), this);
        Bukkit.getPluginManager().registerEvents(new NetherFloorRoof(), this);
        Bukkit.getPluginManager().registerEvents(new ArmorStandLag(), this);
        Bukkit.getPluginManager().registerEvents(new AntiOp(), this);
        Bukkit.getPluginManager().registerEvents(new MobAI(), this);
        Bukkit.getPluginManager().registerEvents(new RandomSpawn(), this);
        Bukkit.getPluginManager().registerEvents(new DeathMessages(), this);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TPS(), 100L, 1L);

        this.getCommand("autumn").setExecutor((CommandExecutor) new AutumnCommand());
        this.getCommand("kill").setExecutor((CommandExecutor) new KillCommand());
        this.getCommand("discord").setExecutor((CommandExecutor) new DiscordCommand());
        this.getCommand("vanish").setExecutor((CommandExecutor) new VanishCommand());
        this.getCommand("getTPS").setExecutor((CommandExecutor) new TPSCommand());
        this.getCommand("help").setExecutor((CommandExecutor) new HelpCommand());
        this.getCommand("uniquejoins").setExecutor((CommandExecutor) new UniqueCommand());
        this.getCommand("say").setExecutor((CommandExecutor) new SayCommand());
        this.getCommand("dupe").setExecutor((CommandExecutor) new DupeCommand());
        //this.getCommand("crash").setExecutor((CommandExecutor) new CrashCommand());
    }

    @Override
    public void onDisable() {
    }
}
