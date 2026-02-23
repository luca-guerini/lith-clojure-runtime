(ns lithria.clojure-runtime.core
  (:gen-class
   :name    lithria.clojure_runtime.Plugin
   :extends org.bukkit.plugin.java.JavaPlugin))

(defn -onEnable [this]
  (.info (.getLogger this) "LithClojureRuntime enabled."))

(defn -onDisable [this]
  (.info (.getLogger this) "LithClojureRuntime disabled."))
