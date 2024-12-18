(ns user
  (:require
    [clojure.java.io :as io]
    [clojure.tools.namespace.repl :as ns-repl]
    #_:clj-kondo/ignore
    [clojure.repl :refer [dir doc]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; file watcher

(def dirs (.list (io/file "src/easy_java_time")))

(apply ns-repl/set-refresh-dirs dirs)

(defn r [] (ns-repl/refresh))
(def reload r)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; format

(def LOGO
"
╔═╗┌─┐┌─┐┬ ┬   ╦┌─┐┬  ┬┌─┐  ╔╦╗┬┌┬┐┌─┐
║╣ ├─┤└─┐└┬┘   ║├─┤└┐┌┘├─┤   ║ ││││├┤
╚═╝┴ ┴└─┘ ┴   ╚╝┴ ┴ └┘ ┴ ┴   ╩ ┴┴ ┴└─┘
")


(def ORANGE "\033[0;33m")
(def PURPLE "\033[0;35m")
(def NC     "\033[0m")

(defn logo [] (println PURPLE LOGO NC))

(defn print-init-msg []
  (println (format "REPL started at %slocalhost%s:%s51020%s"
                   PURPLE NC ORANGE NC)))

(defn fresh []
  (logo)
  (print-init-msg))