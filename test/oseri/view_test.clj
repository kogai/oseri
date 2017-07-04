(ns oseri.view-test
  (:use oseri.view)
  (:require [clojure.test :refer :all]
            [oseri.core :refer :all]))

(deftest test-board
  (testing "should be create board"
    (is (= (board 2) '((#oseri.view.Tile {:row 0 :col 0 :color :empty} #oseri.view.Tile {:row 0 :col 1 :color :empty}) (#oseri.view.Tile {:row 1, :col 0 :color :empty} #oseri.view.Tile {:row 1, :col 1 :color :empty}))))))

(deftest test-show-tile
  (testing "should be show black tile"
    (is (= (show-tile {:color :black}) "B")))
  (testing "should be show white tile"
    (is (= (show-tile {:color :white}) "W")))
  (testing "should be show empty tile"
    (is (= (show-tile {:color :empty}) " "))))

(deftest test-show-header
  (testing "should be show header"
    (is (= (show-header 3) " 0 1 2"))))

(deftest test-show-line
  (testing "should be show line"
    (is (= (show-line '({:color :empty :row 0} {:color :black :row 0} {:color :white :row 0})) "0   B W"))))

(deftest test-show-board
  (testing "should be show board"
    (is (= (show-board '(({:color :empty :row 0} {:color :black} {:color :white})
                         ({:color :empty :row 1} {:color :black} {:color :white}))) " 0 1 2\n0   B W\n1   B W"))))
