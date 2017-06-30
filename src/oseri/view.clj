(ns oseri.view)

(def size 8)
(def line (range size))

(def board
  (map (fn [x] (map (fn [y] {:row x :column y}) line)) line))

(def direction #{:N :NE :E :SE :S :SW :W :NW })

(def player (ref nil))