(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'lithria/clojure-runtime)
(def version "0.1.0-SNAPSHOT")
(def class-dir "target/classes")
(def uber-file (format "target/%s-%s.jar" (name lib) version))

;; Includes Clojure (from :deps) + Paper API (from :compile alias)
(def compile-basis (delay (b/create-basis {:project "deps.edn" :aliases [:compile]})))

;; Includes Clojure (from :deps) but NOT Paper API — correct for the fat JAR
(def uber-basis (delay (b/create-basis {:project "deps.edn"})))

(defn clean [_]
  (b/delete {:path "target"}))

(defn compile-plugin [_]
  (clean nil)
  (b/copy-dir {:src-dirs   ["src" "resources"]
               :target-dir class-dir})
  (b/compile-clj {:basis      @compile-basis
                  :src-dirs   ["src"]
                  :class-dir  class-dir
                  :ns-compile '[lithria.clojure-runtime.core]}))

(defn uber [_]
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis     @uber-basis}))

(defn build [_]
  (compile-plugin nil)
  (uber nil))
