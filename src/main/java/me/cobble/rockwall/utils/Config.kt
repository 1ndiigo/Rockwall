package me.cobble.rockwall.utils

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

object Config {
    private val log = Bukkit.getLogger()
    private var file: File? = null
    private var config: YamlConfiguration? = null
    fun setup() { // NO_UCD (use default)
        file = File(
            Bukkit.getServer().pluginManager.getPlugin("Rockwall")!!.dataFolder,
            "config.yml"
        )
        if (!file!!.exists()) {
            log.info("No config found, Generating...")
            try {
                file!!.createNewFile()
            } catch (e: IOException) {
                // that's a lot of damage
                e.printStackTrace()
            }
        }
        config = YamlConfiguration.loadConfiguration(file!!)
    }

    fun get(): FileConfiguration? {
        return config
    }

    fun getString(path: String): String {
        return get()!!.getString(path)!!
    }

    fun getBool(path: String): Boolean {
        return get()!!.getBoolean(path)
    }

    fun getInt(path: String): Int {
        return get()!!.getInt(path)
    }

    fun save() { // NO_UCD (use default)
        try {
            config!!.save(file!!)
        } catch (e: IOException) {
            System.err.println("Couldn't save file")
        }
    }

    fun reload() { // NO_UCD (unused code)
        config = YamlConfiguration.loadConfiguration(file!!)
        log.info("Rockwall config reloaded")
    }
}