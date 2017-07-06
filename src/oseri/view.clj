(ns oseri.view
  (:require [clojure.string :refer [join]]))

(defrecord Tile [row col color])

(defn board [size]
  (let [line (range size)]
    (vec (map (fn [x] (vec (map (fn [y] (->Tile x y :empty)) line))) line))))

; TODO: atomにしたい
(def player (ref nil))

(defn show-tile [x] (case (:color x)
                      :black "B"
                      :white "W"
                      " "))

(defn show-header-impl [size max] (if (= size 0) ""
                                      (str " " (- max size) (show-header-impl (dec size) max))))

(defn show-header [size] (str " " (show-header-impl size size)))

(defn show-line-impl [line] (if (= (count line) 0) ""
                                (str " " (show-tile (first line)) (show-line-impl (rest line)))))

(defn show-line [line] (join [(:row (first line)) (show-line-impl line)]))

(defn show-board-impl [board] (if (= (count board) 0) []
                                  (cons (show-line (first board)) (show-board-impl (rest board)))))

(defn show-board [board]
  (let [col-size (count (first board)) header (show-header col-size)]
    (join "\n" (cons header (show-board-impl board)))))
