(ns oseri.view)
; (require '[clojure.core.match :refer [match]])

(def board-size 8)

(defn board [size]
  (let [line (range size)]
    (map (fn [x] (map (fn [y] {:row x :column y :color :empty}) line)) line)))

(def direction #{:N :NE :E :SE :S :SW :W :NW})

(def player (ref nil))

(defn show-line [line] "  ")