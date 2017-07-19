(ns oseri.core
  (:require
   [oseri.view :refer [show-board board ->Tile]]
   [oseri.model :refer [initial-operations convert operate pointable?]]))


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
        plyr (ref :black)
        rvs #(case %2
               :black :white
               :white :black)]
    (fn [row col]
      ; 次のゲーム世界の状態のタプル
      ; FIXME: 置けなかった時も手番が変わる
      [(dosync (alter brd operate (->Tile row col plyr))),
       (dosync (alter plyr rvs plyr))])))

(defn on-command [game]
  (fn [cmd]
    (let [parse-cmd-of (fn [f] (fn [str] (Character/digit (f str) 10)))
          row ((parse-cmd-of first) cmd)
          col ((parse-cmd-of second) cmd)
          next (game row col)]
        ; (println row (type row))
        ; (println col (type col))

        ; (println "Result ->\n" (first next))
        ; (println "Next player color is" (second next))
        ; next
        )))

(defn read-cmd [f]
  (loop [pos (read-line)]
    (when (not (empty? pos))
      (f pos)
      (recur (read-cmd f)))))

(defn -main
  [& args]
  (let [game (create-game 8)]
    (read-cmd
      (on-command game))))
