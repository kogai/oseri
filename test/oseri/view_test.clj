(ns oseri.view-test
  (:use oseri.view)
  (:require [clojure.test :refer :all]
            [oseri.core :refer :all]))
(deftest board-test
  (testing "should be create board"
    (is (= (board 2) '(({:row 0 :column 0 :color :empty} {:row 0 :column 1 :color :empty}) ({:row 1, :column 0 :color :empty} {:row 1, :column 1 :color :empty}))))))

(deftest test-show-line
  (testing "should be show first line"
    (is (= (show-line (first (board 1))) "  "))))
