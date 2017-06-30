(ns oseri.view-test
  (:use oseri.view)
  (:require [clojure.test :refer :all]
            [oseri.core :refer :all]))
(deftest board-test
  (testing "should be create board"
    (is (= (board 2) '(({:row 0 :column 0 :color :empty} {:row 0 :column 1 :color :empty}) ({:row 1, :column 0 :color :empty} {:row 1, :column 1 :color :empty}))))))

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
  (testing "should be show first line"
    (is (= (show-line '({:color :empty} {:color :black} {:color :white})) "   B W"))))
