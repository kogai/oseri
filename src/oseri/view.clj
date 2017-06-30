(ns oseri.view
  (:require [clojure.string :refer [join]]))

(def board-size 8)

(defn board [size]
  (let [line (range size)]
    (map (fn [x] (map (fn [y] {:row x :column y :color :empty}) line)) line)))

(def direction #{:N :NE :E :SE :S :SW :W :NW})

(def player (ref nil))
(defn show-tile [x] (case (get x :color)
                      (:black) "B"
                      (:white) "W"
                      " "))

(defn show-header-impl [size max]
  (if (= size 0)
    ""
    (join [" " (- max size) (show-header-impl (- size 1) max)])))

(defn show-header [size] (show-header-impl size size))

(defn show-line [line] "  ")