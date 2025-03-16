# Garder la classe principale de Forge
-keep class net.minecraftforge.fml.common.Mod {
    *;
}

# Garder toutes les annotations de Forge (évite les crashs)
-keepattributes *Annotation*

# Garder les classes essentielles de ton mod (adapte le chemin)
-keep class fr.draconiummc.draconiummod.** { *; }

# Renommer les classes, méthodes et variables
-obfuscate
-repackageclasses ''
-optimizationpasses 5
-overloadaggressively
-flattenpackagehierarchy

# Supprimer les messages de debug
-dontwarn
-dontnote
-ignorewarnings

# Rendre plus difficile la rétro-ingénierie
-adaptresourcefilecontents **.properties,**.xml,**.json
