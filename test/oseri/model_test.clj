(ns oseri.model-test
  (:require [clojure.test :refer :all]
            [oseri.core :refer :all]
            [oseri.view :refer :all]
            [oseri.model :refer :all]))


(defn from-char [row col c] (case c
                              (\B) (->Tile row col :black)
                              (\W) (->Tile row col :white)
                              (->Tile row col :empty)))

(defn from-str [row col s] (if (empty? s)
                             '()
                             (let [head (first s) body (rest s)]
                               (cons (from-char row col head) (from-str row (+ col 1) body)))))

(defn create-board-from-str [board-str-list]
  (map-indexed (fn [row line] (from-str row 0 line)) board-str-list))

(deftest test-successor
  (testing "should derive successor"
    (is (= (successor :N {:row 1 :col 1}) {:row 0 :col 1}))
    (is (= (successor :NE {:row 1 :col 1}) {:row 0 :col 2}))
    (is (= (successor :E {:row 1 :col 1}) {:row 1 :col 2}))
    (is (= (successor :SE {:row 1 :col 1}) {:row 2 :col 2}))
    (is (= (successor :S {:row 1 :col 1}) {:row 2 :col 1}))
    (is (= (successor :SW {:row 1 :col 1}) {:row 2 :col 0}))
    (is (= (successor :W {:row 1 :col 1}) {:row 1 :col 0}))
    (is (= (successor :NW {:row 1 :col 1}) {:row 0 :col 0}))))

(deftest test-wrapped?
  (testing "should be detection wrapped"
    (let [b (board 2)]
      (is (= true (wrapped? {:row 1 :col 1} b)))
      (is (= false (wrapped? {:row 2 :col 2} b)))
      (is (= false (wrapped? {:row -1 :col 0} b))))))

(deftest test-get-tile
  (testing "should get tile"
    (let [b (board 2)] (is (= (get-tile b {:row 1 :col 1}) #oseri.view.Tile {:row 1 :col 1 :color :empty})))))

(deftest test-correct-line
  (testing "should correct line"
    (let [b (create-board-from-str '(
        " BW"
        " BW"
        " BW"
      ))]
      (is (= (correct-line :N {:row 2 :col 1} b) [(->Tile 2 1 :black) (->Tile 1 1 :black) (->Tile 0 1 :black)]))
      (is (= (correct-line :SW {:row 0 :col 2} b) [(->Tile 0 2 :white) (->Tile 1 1 :black) (->Tile 2 0 :empty)]))
      (is (= (correct-line :E {:row 1 :col 0} b) [(->Tile 1 0 :empty) (->Tile 1 1 :black) (->Tile 1 2 :white)])))))

(deftest test-clasp?
  (testing "should detect claspable line"
    (let [line-1 [(->Tile 0 0 :white) (->Tile 0 1 :black)]
          line-2 [(->Tile 0 0 :black) (->Tile 0 1 :black)]
          line-3 [(->Tile 0 0 :black) (->Tile 0 1 :white)]
          line-4 [(->Tile 0 0 :empty) (->Tile 0 1 :black)]
          line-5 [(->Tile 0 0 :white) (->Tile 0 1 :black)]]
      (is (= true (clasp? :black line-1)))
      (is (= false (clasp? :black line-2)))
      (is (= false (clasp? :black line-3)))
      (is (= false (clasp? :black line-4)))
      (is (= false (clasp? :white line-5)))
      )))

(deftest test-movable?
  (testing "should detect movable point"
    (let [b1 (create-board-from-str '("   "
                                      " BW"
                                      "   "))
          b2 (create-board-from-str '("   "
                                      " WB"
                                      "   "))
          b3 (create-board-from-str '("W  "
                                      "B  "
                                      " WB"))
          b4 (create-board-from-str '("  W"
                                      " B "
                                      " WB"))]
      (is (= true (movable? b1 {:row 1 :col 0} :white)))
      (is (= false (movable? b2 {:row 1 :col 0} :white)))
      (is (= true (movable? b3 {:row 2 :col 0} :white)))
      (is (= true (movable? b4 {:row 1 :col 0} :white)))
      )))
