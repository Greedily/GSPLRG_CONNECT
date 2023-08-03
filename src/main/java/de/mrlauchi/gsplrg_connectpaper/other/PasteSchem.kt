package de.mrlauchi.gsplrg_connectpaper.other

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.WorldEditException
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operation
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
import com.sk89q.worldedit.world.World
import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Location
import java.io.File
import java.io.FileInputStream
import java.io.IOException


object PasteSchem {

    fun paste(location: Location, schematicName: String?) {
        val file = File(Main.instance!!.getDataFolder().toString() + File.separator + "schematics" + File.separator + schematicName + ".schem")
        val clipboardFormat = ClipboardFormats.findByFile(file)
        var clipboard: Clipboard
        val blockVector3: BlockVector3 =
            BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ())
        if (clipboardFormat != null) {
            try {
                clipboardFormat.getReader(FileInputStream(file)).use { clipboardReader ->
                    if (location.getWorld() == null) throw NullPointerException("Failed to paste schematic due to world being null")
                    val world: World = BukkitAdapter.adapt(location.getWorld())
                    val editSession = WorldEdit.getInstance().newEditSessionBuilder().world(world).build()
                    clipboard = clipboardReader.read()
                    val operation: Operation = ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(blockVector3)
                        .ignoreAirBlocks(true)
                        .build()
                    try {
                        Operations.complete(operation)
                        editSession.close()
                    } catch (e: WorldEditException) {
                        e.printStackTrace()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}