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
                             []
                             (let [head (first s) body (rest s)]
                               (cons (from-char row col head) (from-str row (+ col 1) body)))))

(defn create-board-from-str "文字列のリストからある状態の盤面を作るヘルパー関数" [board-str-list]
  (vec (map-indexed (fn [row line] (vec (from-str row 0 line))) board-str-list)))

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
    (let [b (create-board-from-str '(" BW"
                                     " BW"
                                     " BW"))]
      (is (= (correct-line :N {:row 2 :col 1} b) [(->Tile 2 1 :black) (->Tile 1 1 :black) (->Tile 0 1 :black)]))
      (is (= (correct-line :SW {:row 0 :col 2} b) [(->Tile 0 2 :white) (->Tile 1 1 :black) (->Tile 2 0 :empty)]))
      (is (= (correct-line :E {:row 1 :col 0} b) [(->Tile 1 0 :empty) (->Tile 1 1 :black) (->Tile 1 2 :white)])))))

(deftest test-clasp?
  (testing "should detect claspable line"
    (let [line-1 [(->Tile 0 0 :white) (->Tile 0 1 :black)]
          line-2 [(->Tile 0 0 :black) (->Tile 0 1 :black)]
          line-3 [(->Tile 0 0 :black) (->Tile 0 1 :white)]
          line-4 [(->Tile 0 0 :empty) (->Tile 0 1 :black)]
          line-5 [(->Tile 0 0 :white) (->Tile 0 1 :black)]
          line-6 [(->Tile 0 0 :black) (->Tile 0 1 :empty)]]
      (is (= true (clasp? :black line-1)))
      (is (= false (clasp? :black line-2)))
      (is (= false (clasp? :black line-3)))
      (is (= false (clasp? :black line-4)))
      (is (= false (clasp? :white line-5)))
      (is (= false (clasp? :black line-6))))))

(deftest test-pointable?
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
                                      " WB"))
          b5 (create-board-from-str '("   "
                                      " B "
                                      "   "))]
      (is (= true (pointable? b1 {:row 1 :col 0} :white)))
      (is (= false (pointable? b2 {:row 1 :col 0} :white)))
      (is (= true (pointable? b3 {:row 2 :col 0} :white)))
      (is (= false (pointable? b4 {:row 1 :col 0} :white)))
      (is (= false (pointable? b5 {:row 1 :col 0} :black))))))

; "        "
; "        "
; "        "
; "   BW   "
; "   WB   "
; "        "
; "        "
; "        "
(deftest test-initial-operations
  (testing "should create initial operations"
    (is (= [(->Tile 3 3 :black) (->Tile 3 4 :white) (->Tile 4 3 :white) (->Tile 4 4 :black)] (initial-operations 8)))))

(deftest test-operate
  (testing "should return new state of board"
    (let [base (create-board-from-str '("   "
                                        " WB"
                                        "   "))
          actual (operate base (->Tile 1 0 :black))
          expect (create-board-from-str '("   "
                                          "BBB"
                                          "   "))]
      (is (= expect actual))))
  (testing "should return new state of board with oblique"
    (let [base (create-board-from-str '("  W"
                                        " B "
                                        "   "))
          actual (operate base (->Tile 2 0 :white))
          expect (create-board-from-str '("  W"
                                          " W "
                                          "W  "))]
      (is (= expect actual))))
  (testing "should return new state of board that line includes empty tile"
    (let [base (create-board-from-str '("    "
                                        " W  "
                                        " B  "
                                        "    "))
          actual (operate base (->Tile 3 1 :white))
          expect (create-board-from-str '("    "
                                          " W  "
                                          " W  "
                                          " W  "))]
      (is (= expect actual))))
  (testing "should return new state of board that include multiple convertable lines"
    (let [base (create-board-from-str '("     "
                                        "   W "
                                        "   B "
                                        " WB  "
                                        "     "))
          actual (operate base (->Tile 3 3 :white))
          expect (create-board-from-str '("     "
                                          "   W "
                                          "   W "
                                          " WWW "
                                          "     "))]
      (is (= expect actual))))
  (testing "should return error when not pointable"
    (let [actual (create-board-from-str '("   "
                                          " BW"
                                          "   "))
          expect "Can't point here"]
      (is (= expect (operate actual (->Tile 1 0 :black)))))))

(deftest test-score
  (testing "should count score"
    (let [b (create-board-from-str '(" B "
                                     "WWW"
                                     " B "))
          actual1 (score b :black)
          actual2 (score b :white)]
      (is (= 2 actual1))
      (is (= 3 actual2)))))

(deftest test-playable?
  (testing "should be able to determine that the board is playeable"
    (let [actual (playable? (create-board-from-str '("   "
                                                     " B "
                                                     "   ")) :black)
          expect false]
      (is (= expect actual)))
    (let [actual (playable? (create-board-from-str '("BWB"
                                                     "WBW"
                                                     "BWB")) :black)
          expect false]
      (is (= expect actual)))
    (let [actual (playable? (create-board-from-str '(" WB"
                                                     "WBW"
                                                     "BWB")) :black)
          expect true]
      (is (= expect actual)))))
