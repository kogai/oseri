(ns oseri.view
  (:require [clojure.string :refer [join]]))

(def board-size 8)

(defn board [size]
  (let [line (range size)]
    (map (fn [x] (map (fn [y] {:row x :col y :color :empty}) line)) line)))

(def direction #{:N :NE :E :SE :S :SW :W :NW})

(def player (ref nil))

(defn show-tile [x] (case (get x :color)
                      (:black) "B"
                      (:white) "W"
                      " "))

(defn show-header-impl [size max] (if (= size 0) ""
                                      (join [" " (- max size) (show-header-impl (- size 1) max)])))

(defn show-header [size] (show-header-impl size size))

(defn show-line-impl [line] (if (= (count line) 0) ""
                                (join [" " (show-tile (first line)) (show-line-impl (rest line))])))

(defn show-line [line] (join [(get (first line) :row) (show-line-impl line)]))

(defn show-board-impl [board] (if (= (count board) 0) []
                                  (concat [(show-line (first board))] (show-board-impl (rest board)))))

(defn show-board [board]
  (let [col-size (count (first board)) header (show-header col-size)]
    (join "\n" (concat [header] (show-board-impl board)))))
