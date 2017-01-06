package me.ddevil.shiroi.craft.test;

import com.avaje.ebean.EbeanServer;
import me.ddevil.shiroi.craft.command.CommandManager;
import me.ddevil.shiroi.craft.config.ConfigManager;
import me.ddevil.shiroi.craft.log.PluginLogger;
import me.ddevil.shiroi.craft.message.MessageManager;
import me.ddevil.shiroi.craft.misc.PluginSettings;
import me.ddevil.shiroi.craft.misc.design.PluginColorDesign;
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by bruno on 06/01/2017.
 */
public class TestShiroiPlugin implements ShiroiPlugin<MessageManager, ConfigManager<?>> {

    @NotNull
    @Override
    public PluginSettings getSettings() {
        return null;
    }

    @NotNull
    @Override
    public MessageManager getMessageManager() {
        return null;
    }

    @NotNull
    @Override
    public ConfigManager<?> getConfigManager() {
        return null;
    }

    @NotNull
    @Override
    public CommandManager getCommandManager() {
        return null;
    }

    @NotNull
    @Override
    public PluginColorDesign getColorDesign() {
        return null;
    }

    @Override
    public void setColorDesign(@NotNull PluginColorDesign pluginColorDesign) {

    }

    @NotNull
    @Override
    public PluginLogger getPluginLogger() {
        return null;
    }

    @Override
    public File getDataFolder() {
        return null;
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return null;
    }

    @Override
    public FileConfiguration getConfig() {
        return null;
    }

    @Override
    public InputStream getResource(String s) {
        return null;
    }

    @Override
    public void saveConfig() {

    }

    @Override
    public void saveDefaultConfig() {

    }

    @Override
    public void saveResource(String s, boolean b) {

    }

    @Override
    public void reloadConfig() {

    }

    @Override
    public PluginLoader getPluginLoader() {
        return null;
    }

    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public boolean isNaggable() {
        return false;
    }

    @Override
    public void setNaggable(boolean b) {

    }

    @Override
    public EbeanServer getDatabase() {
        return null;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String s, String s1) {
        return null;
    }

    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
