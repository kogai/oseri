(ns oseri.core
  (:require
   [oseri.view :refer [show-board board]]
   [oseri.model :refer [initial-operations convert operate]]))

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

(defn initial-board [n]
  (let [brd (board n) oprts (initial-operations n)]
    (reduce convert brd oprts)))

(defn create-game [n]
  (let [brd (ref (initial-board n))
        plyr (ref :white)
        rvs #(case %
               :black :white
               :white :black)]
    (fn [tile]
    ; 次のゲーム世界の状態のタプル
      [(dosync (alter brd operate tile)),
       (dosync (alter plyr rvs))])))

(defn -main
  [& args]
  (println (show-board (board 8))))
