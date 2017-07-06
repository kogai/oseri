(ns oseri.core
  (:require
    [oseri.view :refer [show-board board]]
    [oseri.model :refer [initial-operations convert]]
    ))

; (defn on-command
;   [cmdline]
;   (let [
;         cmd (first cmdline)
;         pos (second cmdline)
;       ]
;     (cond
;       (= cmd :move) (play-move pos)
;       (= cmd :exit) (System/exit 0)
;       :else nil)
;   ))

; (defn -main
;   [& args]
;   (init-view on-command)
;   (init-game on-state-changed)
;   (start-ui))
; (ns oser.core)

; TODO: atomにしたい
(def player (ref nil))

(defn initial-board [n]
  (let [brd (board n) oprts (initial-operations n)]
      (reduce convert brd oprts)))

(defn -main
  [& args]
  (println (show-board (board 8))))
