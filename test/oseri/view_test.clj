(ns oseri.view-test
  (:use oseri.view)
  (:require [clojure.test :refer :all]
            [oseri.core :refer :all]
            [oseri.model-test :refer [create-board-from-str]]
            [clojure.string :refer [split]]))

(deftest test-board
  (testing "should be create board"
    (is (= (board 2) '((#oseri.view.Tile {:row 0 :col 0 :color :empty} #oseri.view.Tile {:row 0 :col 1 :color :empty}) (#oseri.view.Tile {:row 1, :col 0 :color :empty} #oseri.view.Tile {:row 1, :col 1 :color :empty}))))))

(deftest test-map-board
  (testing "should map board"
    (let [actual (map-board #(assoc %1 :color :black)
                            (create-board-from-str '("  "
                                                     "  ")))
          expect (create-board-from-str '("BB"
                                          "BB"))]
      (is (= expect actual)))))

(deftest test-show-tile
  (testing "should be show black tile"
    (is (= (show-tile {:color :black}) "B")))
  (testing "should be show white tile"
    (is (= (show-tile {:color :white}) "W")))
  (testing "should be show empty tile"
    (is (= (show-tile {:color :empty}) " "))))

(deftest test-show-header
  (testing "should be show header"
    (is (= (show-header 3) "  0 1 2"))))

(deftest test-show-line
  (testing "should be show line"
    (is (= (show-line '({:color :empty :row 0} {:color :black :row 0} {:color :white :row 0})) "0   B W"))))

; -  0 1 2
; -0 W B W
; -1 B   B
; -2 W B W
(deftest test-show-board
  (testing "should be show board"
    (let [actual (show-board '(({:color :white :row 0} {:color :black} {:color :white})
                               ({:color :black :row 1} {:color :empty} {:color :black})
                               ({:color :white :row 2} {:color :black} {:color :white})))
          expect "  0 1 2\n0 W B W\n1 B   B\n2 W B W"]
      (is (= expect actual)))))
